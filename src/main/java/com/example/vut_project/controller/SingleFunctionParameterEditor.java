package com.example.vut_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SingleFunctionParameterEditor {
    public TextField functionParameterEditTextField;
    public Button functionAddParameterButton;
    public TextField functionDataTypeEditTextField;
    private AddFunctionController reference;

    public void onAddFunctionParameterButtonClick(ActionEvent event) {
        reference.handleAddParameterButtonClick();
    }

    public void parseAddFunctionControllerReference(AddFunctionController reference){
        this.reference = reference;
    }

    public String getEnteredParameter(){
        // TODO tu sa bude musiet ten parameter vytvarat a retrnovať
        String dataType = this.functionDataTypeEditTextField.getText();
        return this.functionParameterEditTextField.getText() + ":" + this.functionDataTypeEditTextField.getText();
    }
}
