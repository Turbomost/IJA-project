package com.example.vut_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("diagram_editor.fxml"));
        stage.setTitle("UML");
        stage.setScene(new Scene(root, 750, 500));
        stage.show();
        // new
    }

    public static void main(String[] args) {
        launch();
    }
}