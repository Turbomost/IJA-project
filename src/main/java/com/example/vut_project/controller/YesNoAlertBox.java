package com.example.vut_project.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class YesNoAlertBox {
    public static int display(String title, String message, String button1_message, String button2_message) {
        final int[] choice = {-1};
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);
        window.setMinHeight(200);
        Label label = new Label();
        label.setText(message);
        Button yesButton = new Button(button1_message);
        yesButton.setOnAction(event -> {
            choice[0] = 1;
            window.close();
        });
        Button noButton = new Button(button2_message);
        noButton.setOnAction(event -> {
            choice[0] = 0;
            window.close();
        });

        VBox layout = new VBox(10);
        HBox hboxButtonBox = new HBox();
        hboxButtonBox.getChildren().addAll(yesButton, noButton);
        hboxButtonBox.setAlignment(Pos.CENTER);
        hboxButtonBox.setMargin(yesButton, new Insets(10, 10, 10, 10));
        hboxButtonBox.setMargin(noButton, new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(label, hboxButtonBox);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return (int) choice[0];
    }
}
