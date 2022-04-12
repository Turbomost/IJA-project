/**
 * @Brief: Main program
 * @File: HelloApplication
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Application starts here
 */
public class HelloApplication extends Application {

    /**
     * Main program launch
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Constructor for application start
     *
     * @param stage stage for application
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("diagram_editor.fxml")));
        stage.setTitle("UML");
        stage.setScene(new Scene(root, 750, 500));
        stage.show();
    }
}