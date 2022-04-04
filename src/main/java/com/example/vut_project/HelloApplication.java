package com.example.vut_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private Button FileOpenButton;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        // new
        FileOpenButton = new Button();
        FileOpenButton.setText("Open XML");
    }

    public static void main(String[] args) {
        ParseXML XMLTree = new ParseXML();
        XMLTree.start_parse();
        launch();
    }
}