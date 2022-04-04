package com.example.vut_project;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ProjectLayout {
    public void CreateProjectLayout(){
        Stage window = new Stage();
        Scene scene;
        Button button = new Button("Some button in the middle for now");
        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        scene = new Scene(layout, 600, 300);
        window.setScene(scene);
        window.setTitle("New Project Layout");
        window.show();

    }
}
