// Package declaration indicating the package in which the class belongs
package com.example.carivers;

// Importing necessary JavaFX classes for scene management
import javafx.scene.Scene;
import javafx.stage.Stage;

// Utility class for handling scene transitions
public class SceneTransitionUtil {

    // Static method for switching the scene of a given stage
    public static void switchScene(Stage stage, Scene scene) {
        // Setting the provided scene as the current scene for the stage
        stage.setScene(scene);
    }
}
