/**
 * @Brief: Entity for easier class identification at scene
 * @File: EntityController
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project.controller;

import javafx.event.ActionEvent;
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

    public void setFieldsWhileEdit(AttributeOperationController attr) {
        this.functionParameterEditTextField.setText(attr.getName());
        this.functionDataTypeEditTextField.setText(attr.getDataType());
    }

    public AttributeOperationController getEnteredParameter(int selected_index, String old_param_name) {
        AttributeOperationController new_attr;
        if (this.functionParameterEditTextField.getText().isBlank() || this.functionDataTypeEditTextField.getText().isBlank()) {
            AlertBox.display("note", "Name and data type cannot be blank", "Cancel");
            return null;//reference.attributeReference.findOperationTypeByName(old_param_name.substring(0, old_param_name.lastIndexOf(" :")));
        }

        if (selected_index == -1) {
            new_attr = reference.attributeReference.addOperationType(this.functionParameterEditTextField.getText(), this.functionDataTypeEditTextField.getText());
        } else {
            System.out.println(reference.attributeReference.operationTypesNames());
            System.out.println(reference.attributeReference.getOperationControllerList().toString());

            new_attr = reference.attributeReference.findOperationTypeByName(old_param_name.substring(0, old_param_name.lastIndexOf(" :")));
            if (reference.attributeReference.findOperationTypeByName(this.functionParameterEditTextField.getText()) != null) {
                new_attr.setName(this.functionParameterEditTextField.getText());
                if (new_attr.getName().equals(old_param_name.substring(0, old_param_name.lastIndexOf(" :")))) {
                    new_attr.setDataType(this.functionDataTypeEditTextField.getText());
                    return new_attr;
                } else {
                    AlertBox.display("Warning", "Argument name is taken", "Understood");
                    new_attr.setName(old_param_name.substring(0, old_param_name.lastIndexOf(" :")));
                }
            } else {
                new_attr.setParams(this.functionParameterEditTextField.getText(), this.functionDataTypeEditTextField.getText());
            }
        }
        return new_attr;
    }
}
