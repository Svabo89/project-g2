package edu.rit.edgeconverter.DDL;

import edu.rit.edgeconverter.model.Table;
import edu.rit.edgeconverter.model.Field;

public abstract class DDLCreator {
    protected int[] numBoundTables;
    protected Table[] tables;
    protected Field[] fields;
    protected String products;
    protected int maxBound;

    public DDLCreator(Table[]tables,Field[]fields){
        this.tables=tables;
        this.fields=fields;
    }
    protected Table getTable(int numFigure) {
        for (int tIndex = 0; tIndex < tables.length; tIndex++) {
            if (numFigure == tables[tIndex].getNumFigure()) {
                return tables[tIndex];
            }
        }
        return null;
    }

    protected Field getField(int numFigure) {
        for (int fIndex = 0; fIndex < fields.length; fIndex++) {
            if (numFigure == fields[fIndex].getNumFigure()) {
                return fields[fIndex];
            }
        }
        return null;
    }
    public abstract String getDatabaseName();

    public abstract String getSQLString();

    public abstract void initalize();

    public abstract String getProductName();

    public abstract void createDDL();
}
