package edu.rit.edgeconverter.Listeners;

import edu.rit.edgeconverter.DDL.DDLCreator;
import edu.rit.edgeconverter.DDL.MySQLCreator;
import edu.rit.edgeconverter.view.ConverterGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.PrintWriter;

public class DDLButtonListener implements EventHandler<ActionEvent> {

    private File outputDir;
    private String sqlString;
    private DDLCreator ddlCreator;
    private ConverterGUI converterGUI;

    private static final Logger log = LogManager.getLogger("edu.rit");

    public static final String CANCELLED = "CANCELLED";


    public DDLButtonListener(ConverterGUI converterGUI){
        this.converterGUI = converterGUI;
    }

    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }


    public void OutputClasses() {
    }

    public String getSQLStatements() {
        this.ddlCreator = new MySQLCreator(this.converterGUI.getTables(),this.converterGUI.getFields());
        this.sqlString = this.ddlCreator.getSQLString();
        return this.sqlString;
    }

    public void writeSQL(String sqlString) {
        this.sqlString = sqlString;
        try (PrintWriter out = new PrintWriter(new File(outputDir, "output.sql"))) {
            out.println(sqlString);
        } catch (Exception e) {
            log.fatal(e.getMessage());
        }
    }

    @Override
    public void handle(ActionEvent event) {
            if(this.outputDir == null){
                System.out.println("Please, select output directory first.");
            }
            if (this.converterGUI.getTables()==null || this.converterGUI.getFields()==null){
                System.out.println("Please, load .edg file first.");
            }
            if(this.outputDir != null && this.converterGUI.getTables()!=null && this.converterGUI.getFields()!=null) {
                OutputClasses();
                sqlString = getSQLStatements();
                if (sqlString.equals(CANCELLED)) {
                    return;
                }
                writeSQL(sqlString);
            }

        }
    }

