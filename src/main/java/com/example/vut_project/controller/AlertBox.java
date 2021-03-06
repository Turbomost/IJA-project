/**
 * @Brief: Show box with error message
 * @File: AlertBox
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project.controller;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Show box with error message
 */
public class AlertBox {

    /**
     * Default constructor for AlertBox
     *
     * @param title   title of AlertBox
     * @param message message in AlertBox
     */
    public static void display(String title, String message, String button_message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);
        window.setMinHeight(200);
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button(button_message);
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
