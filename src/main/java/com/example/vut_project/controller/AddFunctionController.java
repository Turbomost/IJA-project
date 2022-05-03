package com.example.vut_project.controller;

import com.example.vut_project.EntityController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class AddFunctionController {
    private final ObservableList<String> observableListOfFunctions = FXCollections.observableArrayList();
    @FXML
    public Button saveFunctionButton;
    public MenuItem editFunctionContextMenuClick;
    @FXML
    ListView functionListView;
    @FXML
    TextField methodNameTextField;
    EntityController entityControllerReference;
    AttributeController attributeReference;

    public void parseEntityControllerAsReference(EntityController reference, AttributeController attributeReference){
        this.entityControllerReference = reference;
        this.attributeReference = attributeReference;
        this.methodNameTextField.setText(attributeReference.getName());
    }

    public void onSaveFunctionButtonClick(ActionEvent event) {

    }

    public void onAddFunctionContextMenuClick(ActionEvent event) {

        this.functionListView.getItems().add("fero");
    }

    public void onEditFunctionContextMenuClick(ActionEvent event) {
    }

    public void onDeleteFunctionContextMenuClick(ActionEvent event) {

    }
}
