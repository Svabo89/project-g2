package edu.rit.edgeconverter.Listeners;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.logging.Logger;

/**
 * The SceneListener class provides utility methods for handling window events,
 * such as when the window is closing or showing. It is particularly useful for
 * performing specific actions or logging during these events.
 */
public class SceneListener {

    private static final Logger LOGGER = Logger.getLogger(SceneListener.class.getName());

    // Private constructor to prevent instantiation
    private SceneListener() {
        // Prevent instantiation
    }

    /**
     * Handles the close request event of the primary stage.
     * This method is invoked when the user attempts to close the window,
     * allowing for specific actions or logging to be performed at this time.
     *
     * @param primaryStage The primary stage of the application.
     */
    public static void onCloseRequest(Stage primaryStage) {
        primaryStage.setOnCloseRequest(event -> LOGGER.info("Window CLOSED"));
    }

    /**
     * Handles the showing event of the primary stage.
     * This method is invoked when the window is about to be shown,
     * allowing for specific actions or logging to be performed at this time.
     *
     * @param primaryStage The primary stage of the application.
     */
    public static void onShowing(Stage primaryStage) {
        primaryStage.setOnShowing(event -> LOGGER.info("Window OPENING"));
    }
}
