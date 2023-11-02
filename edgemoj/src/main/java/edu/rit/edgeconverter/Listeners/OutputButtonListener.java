package edu.rit.edgeconverter.Listeners;


import edu.rit.edgeconverter.view.ConverterGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
// import edu.rit.edgeconverter.DDL.DDLCreator;
// import edu.rit.edgeconverter.DDL.MySQLCreator;

import java.io.File;

public class OutputButtonListener implements EventHandler<ActionEvent> {

    private File outputDir;
    private ConverterGUI converterGUI;


    public OutputButtonListener(ConverterGUI converterGUI){
        this.converterGUI = converterGUI;
    }

    public void setOutputDir() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(this.converterGUI.getStage());
        if (selectedDirectory != null) {
            outputDir = selectedDirectory;
            System.out.println("Output directory set to: " + outputDir.getAbsolutePath());
        }
        this.converterGUI.getDDLButtonListener().setOutputDir(selectedDirectory);
    }

    public File getOutputDir(){
        return this.outputDir;
    }

    @Override
    public void handle(ActionEvent event) {
        outputDir = null;
        if (event.getSource() instanceof DirectoryChooser) {
            setOutputDir();
        } else {
            while (outputDir == null) {
                setOutputDir();
            }
        }
    }
}

