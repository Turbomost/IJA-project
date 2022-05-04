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

    public void getEnteredParameter(int selected_index, String old_param_name){
        if (selected_index == -1){
            reference.attributeReference.addOperationType(this.functionParameterEditTextField.getText(), this.functionDataTypeEditTextField.getText());
        }else{
            reference.attributeReference.findOperationTypeByName(old_param_name.substring(0, old_param_name.lastIndexOf(" :"))).setParams(this.functionParameterEditTextField.getText(), this.functionDataTypeEditTextField.getText());
        }
        // TODO tu sa bude musiet ten parameter vytvarat a retrnovať
    }
}
