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

    public void parseAddFunctionControllerReference(AddFunctionController reference) {
        this.reference = reference;
    }

    public AttributeOperationController getEnteredParameter(int selected_index, String old_param_name) {

        AttributeOperationController new_attr;
        if (this.functionParameterEditTextField.getText().isBlank() || this.functionDataTypeEditTextField.getText().isBlank()) {
            AlertBox.display("note", "Name and data type cannot be blank", "Cancel");
            return null;
        }

        if (selected_index == -1) {
            new_attr = reference.attributeReference.addOperationType(this.functionParameterEditTextField.getText(), this.functionDataTypeEditTextField.getText());
        } else {
            System.out.println(reference.attributeReference.getOperationControllerList().toString()); //TODO why its empty?????
            new_attr = reference.attributeReference.findOperationTypeByName(old_param_name.substring(0, old_param_name.lastIndexOf(" :"))).setParams(this.functionParameterEditTextField.getText(), this.functionDataTypeEditTextField.getText());
        }
        return new_attr;
        // TODO tu sa bude musiet ten parameter vytvarat a retrnovať
    }
}
