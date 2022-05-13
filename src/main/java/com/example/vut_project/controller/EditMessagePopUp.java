package com.example.vut_project.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditMessagePopUp {
    public static void EditMessagePopUpDisplay(MessageLine messageLineReference, String title, String message, String button_message){
        Stage editMessagePopUpWindow = new Stage();
        editMessagePopUpWindow.initModality(Modality.APPLICATION_MODAL);
        editMessagePopUpWindow.setTitle(title);
        editMessagePopUpWindow.setMinWidth(300);
        editMessagePopUpWindow.setMinHeight(200);
        Label label = new Label();

        TextField textField = new TextField();
        textField.setPromptText("Message");
        textField.setText(messageLineReference.getLineString());
        textField.setAlignment(Pos.CENTER);
        textField.maxWidth(editMessagePopUpWindow.getMinWidth()/2);
        textField.maxHeight(20);
        HBox hboxTextField = new HBox();
        hboxTextField.getChildren().add(textField);
        hboxTextField.setAlignment(Pos.CENTER);
        hboxTextField.setMargin(hboxTextField, new Insets(25, 10, 25, 10));
        hboxTextField.setMaxWidth(editMessagePopUpWindow.getMinWidth()/2+20);
        hboxTextField.setMaxHeight(20);

        label.setText(message);
        Button closeButton = new Button(button_message);
        closeButton.setOnAction(e -> {
            messageLineReference.setLabel_string(textField.getText());
            editMessagePopUpWindow.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, hboxTextField, closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene addAttributeScene = new Scene(layout);
        editMessagePopUpWindow.setScene(addAttributeScene);
        editMessagePopUpWindow.showAndWait();
    }
}
