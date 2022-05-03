package com.example.vut_project.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SequenceChoiceBox {
    public static String display(String title, String message, String button_message, String[] st) {
        // create a choiceBox
        final String[] chosen = {null};
        ChoiceBox c = new ChoiceBox(FXCollections.observableArrayList(st));

        // add a listener
        c.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value) {
                chosen[0] = st[new_value.intValue()];
                System.out.println("CHOSEN CD: " + chosen[0]);
            }
        });
        // add ChoiceBox
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);
        window.setMinHeight(200);
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button(button_message);
        closeButton.setOnAction(e -> window.close());
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                chosen[0] = null;
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton, c);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        if (chosen[0] != null) {
            return chosen[0];
        }
        return null;
    }
}
