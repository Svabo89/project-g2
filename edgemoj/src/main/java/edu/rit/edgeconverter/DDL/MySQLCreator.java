package edu.rit.edgeconverter.DDL;

import edu.rit.edgeconverter.model.Field;
import edu.rit.edgeconverter.model.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MySQLCreator extends DDLCreator{
    private static final Logger log = LogManager.getLogger("edu.rit");
    protected String databaseName;
    protected StringBuilder sb=new StringBuilder();
    public MySQLCreator(Table[]tables,Field[]fields){
        super(tables,fields);
        log.info("Initializing MySQLCreator");
        initalize();
        sb=new StringBuilder();
    }

    @Override
    public void createDDL() {
        log.info("Creating DDL");
        initializeDatabase();
        processTables();
        log.info("DDL created");
    }

    private void initializeDatabase() {
        databaseName = getDatabaseName();
        sb.append(String.format("CREATE DATABASE %s;\r%n", databaseName));
        sb.append(String.format("USE %s;\r%n", databaseName));
    }

    private void processTables() {
        for (int boundCount = 0; boundCount <= maxBound; boundCount++) {
            for (int tableCount = 0; tableCount < numBoundTables.length; tableCount++) {
                if (numBoundTables[tableCount] == boundCount) {
                    processTable(tableCount);
                }
            }
        }
    }

    private void processTable(int tableCount) {
        Table table = tables[tableCount];
        sb.append(String.format("CREATE TABLE %s (\r%n", table.getTableName()));
        int[] nativeFields = table.getNativeFields();
        int[] relatedFields = table.getRelatedFields();
        List<Boolean> primaryKey = processFields(nativeFields);
        processPrimaryKeys(primaryKey, tableCount, nativeFields);
        processForeignKeys(relatedFields, nativeFields, tableCount);
        sb.append(");\r\n\r\n");
    }

    private List<Boolean> processFields(int[] nativeFields) {
        List<Boolean> primaryKey = new ArrayList<>();
        for (int nativeField : nativeFields) {
            Field field = getField(nativeField);
            primaryKey.add(processField(field));
        }
        return primaryKey;
    }

    private boolean processField(Field field) {
        sb.append(String.format("\t%s %s", field.getName(), field.getDataType()));
        appendVarcharLengthIfNeeded(field);
        appendNotNullIfNeeded(field);
        appendDefaultValueIfNeeded(field);
        sb.append(",\r\n");
        return field.isPrimaryKey();
    }

    private void appendVarcharLengthIfNeeded(Field field) {
        if (field.getDataType().getIndex() == 0) {
            sb.append(String.format("(%d)", field.getVarcharValue()));
        }
    }

    private void appendNotNullIfNeeded(Field field) {
        if (field.getDisallowNull()) {
            sb.append(" NOT NULL");
        }
    }

    private void appendDefaultValueIfNeeded(Field field) {
        String defaultValue = field.getDefaultValue();
        if (!defaultValue.isEmpty()) {
            if (field.getDataType().getIndex() == 1) {
                sb.append(String.format(" DEFAULT %d", convertStrBooleanToInt(defaultValue)));
            } else {
                sb.append(String.format(" DEFAULT %s", defaultValue));
            }
        }
    }

    private void processPrimaryKeys(List<Boolean> primaryKey, int tableCount, int[] nativeFields) {
        long numPrimaryKey = primaryKey.stream().filter(Boolean::booleanValue).count();
        if (numPrimaryKey > 0) {
            appendPrimaryKeys(primaryKey, tableCount, nativeFields);
        } else {
            log.warn("No primary keys in table");
        }
    }

    private void appendPrimaryKeys(List<Boolean> primaryKey, int tableCount, int[] nativeFields) {
        sb.append(String.format("CONSTRAINT %s_PK PRIMARY KEY (", tables[tableCount].getTableName()));
        String primaryKeys = IntStream.range(0, primaryKey.size())
                .filter(primaryKey::get)
                .mapToObj(i -> getField(nativeFields[i]).getName())
                .collect(Collectors.joining(", "));
        sb.append(primaryKeys).append(")\r\n");
    }

    private void processForeignKeys(int[] relatedFields, int[] nativeFields, int tableCount) {
        long numForeignKey = Arrays.stream(relatedFields).filter(r -> r != 0).count();
        if (numForeignKey > 0) {
            appendForeignKeys(relatedFields, nativeFields, tableCount);
        }
    }

    private void appendForeignKeys(int[] relatedFields, int[] nativeFields, int tableCount) {
        Table table = tables[tableCount];
        for (int i = 0; i < relatedFields.length; i++) {
            if (relatedFields[i] != 0) {
                Field nativeField = getField(nativeFields[i]);
                Field relatedField = getField(relatedFields[i]);
                Table boundTable = getTable(nativeField.getTableBound());
                sb.append(String.format("CONSTRAINT %s_FK%d FOREIGN KEY(%s) REFERENCES %s(%s),\r%n",
                        table.getTableName(), i + 1, nativeField.getName(), boundTable.getTableName(), relatedField.getName()));
            }
        }
        sb.append("\r\n");
    }

    protected int convertStrBooleanToInt(String input) { //MySQL uses '1' and '0' for boolean types
        if (input.equals("true")) {
            return 1;
        } else {
            return 0;
        }
    }
    public String generateDatabaseName() { //prompts user for database name
        log.info("Getting DDL name");
        databaseName="";
        return databaseName;
    }
    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public String getSQLString() {
        createDDL();
        return sb.toString();
    }

    @Override
    public void initalize() {
        numBoundTables = new int[tables.length];
        maxBound = 0;
        sb = new StringBuilder();

        for (int i = 0; i < tables.length; i++) { //step through list of tables
            int numBound = 0; //initialize counter for number of bound tables
            int[] relatedFields = tables[i].getRelatedFields();
            for (int j = 0; j < relatedFields.length; j++) { //step through related fields list
                if (relatedFields[j] != 0) {
                    numBound++; //count the number of non-zero related fields
                }
            }
            numBoundTables[i] = numBound;
            if (numBound > maxBound) {
                maxBound = numBound;
            }
        }
    }

    @Override
    public String getProductName() {
        return "MySQL";
    }
}
