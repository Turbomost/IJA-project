package com.example.vut_project.controller;

import com.example.vut_project.EntityController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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
    SingleFunctionParameterEditor singleFunctionParameterEditorReference;

    Pane editMethodPupUpSpace;
    Scene editMethodPopUpScene;
    Stage editMethodPopUpStage;
    private int selected_fucntion_index;
    private String old_param_name;

    public void parseEntityControllerAsReference(EntityController reference, AttributeController attributeReference){
        this.entityControllerReference = reference;
        this.attributeReference = attributeReference;
        this.methodNameTextField.setText(attributeReference.getName());
        this.selected_fucntion_index = entityControllerReference.ClickedAttributeIndex;
    }

    public void onSaveFunctionButtonClick(ActionEvent event) {
        System.out.println("SAVE PARAMETERS ");

    }

    public void onAddFunctionContextMenuClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vut_project/single_method_edit.fxml")); //new object (aba class diagram) is created as pane
        this.editMethodPupUpSpace = loader.load();
        this.editMethodPopUpScene = new Scene(editMethodPupUpSpace);
        this.editMethodPopUpStage = new Stage();
        this.editMethodPopUpStage.setTitle("Parameter");
        this.editMethodPopUpStage.setScene(editMethodPopUpScene);
        this.singleFunctionParameterEditorReference = loader.getController();
        this.singleFunctionParameterEditorReference.parseAddFunctionControllerReference(this);
        this.editMethodPopUpStage.show();
    }

    public void onEditFunctionContextMenuClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vut_project/single_method_edit.fxml")); //new object (aba class diagram) is created as pane
        this.editMethodPupUpSpace = loader.load();
        this.editMethodPopUpScene = new Scene(editMethodPupUpSpace);
        this.editMethodPopUpStage = new Stage();
        this.editMethodPopUpStage.setTitle("Parameter");
        this.editMethodPopUpStage.setScene(editMethodPopUpScene);
        this.singleFunctionParameterEditorReference = loader.getController();
        this.singleFunctionParameterEditorReference.parseAddFunctionControllerReference(this);
        this.editMethodPopUpStage.show();
    }

    public void handleAddParameterButtonClick(){
        //System.out.println(this.singleFunctionParameterEditorReference.getEnteredParameter());
        int selectedIndex = functionListView.getSelectionModel().getSelectedIndex();
        if(selectedIndex != -1) {
            old_param_name = functionListView.getItems().get(selectedIndex).toString();
            functionListView.getItems().remove(selectedIndex);
            System.out.println("CLICKED TEXT :" + old_param_name);
        }
        System.out.println("SELECTED INDEX " + selectedIndex);
        //this.functionListView.getItems().remove();
        this.singleFunctionParameterEditorReference.getEnteredParameter(selectedIndex, old_param_name);
        this.editMethodPopUpStage.close();
        if(this.selected_fucntion_index != -1){
            entityControllerReference.entityAttributeView.getItems().set(this.selected_fucntion_index, attributeReference.getWholeAttributeString());
        }
        if(selectedIndex != -1){
            this.functionListView.getItems().add(selectedIndex, attributeReference.getLastAddedOperationAttributeString());
        }else{
            this.functionListView.getItems().add(attributeReference.getLastAddedOperationAttributeString());
            System.out.println("FOO INDEX TO UPDATE " + this.selected_fucntion_index);

        }

        //this.functionListView.getItems().add(this.singleFunctionParameterEditorReference.getEnteredParameter());
    }

    public void onDeleteFunctionContextMenuClick(ActionEvent event) {

    }

}
