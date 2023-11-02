package edu.rit.edgeconverter;

import javafx.application.Application;
import javafx.stage.Stage;
import edu.rit.edgeconverter.view.ConverterGUI;

public class RunFileConverter extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ConverterGUI gui = new ConverterGUI(primaryStage);
        gui.initializeUI();
    }
}
