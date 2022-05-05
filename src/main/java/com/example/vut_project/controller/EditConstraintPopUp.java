package com.example.vut_project.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditConstraintPopUp {
    public static void display(BoundLine reference) {
        final String[] constraintName = {null};
        VBox layout = new VBox();
        TextField constraintNameTextField = new TextField();
        constraintNameTextField.setText(reference.getLabel());
        Scene addAttributeScene = new Scene(layout);
        Stage editConstraintPopUpWindow = new Stage();
        Button editButton = new Button();
        Label label = new Label();
        label.setText("Constraint Editor");
        editButton.setText("Edit Constraint");
        constraintNameTextField.setPromptText("Constraint name");
        constraintNameTextField.setMaxHeight(50);
        constraintNameTextField.setMaxWidth(200);
        constraintNameTextField.setPrefHeight(Region.USE_COMPUTED_SIZE);
        constraintNameTextField.setPrefWidth(Region.USE_COMPUTED_SIZE);


        HBox hboxDescription = new HBox();
        Text description1 = new Text("Cardinality");
        Text description2 = new Text("Type");
        Text description3 = new Text("Cardinality");
        hboxDescription.setMargin(description1, new Insets(10, 10, 0, 10));
        hboxDescription.setMargin(description2, new Insets(10, 50, 0, 50));
        hboxDescription.setMargin(description3, new Insets(10, 10, 0, 10));
        hboxDescription.getChildren().addAll(description1, description2, description3);
        hboxDescription.setAlignment(Pos.CENTER);

        HBox hboxChoice = new HBox();

        final String[] choiceBoxLeftResult = {null};
        choiceBoxLeftResult[0] = reference.left_cardinality;
        String constraintLeftRoll[] = {"1..n", "0..n", "0..1", "1..1"};
        ChoiceBox constraintChoiceBoxLeft = new ChoiceBox(FXCollections.observableArrayList(constraintLeftRoll));
        constraintChoiceBoxLeft.setValue(choiceBoxLeftResult[0]);
        constraintChoiceBoxLeft.setAccessibleText("Constraint Left");
        // add a listener
        constraintChoiceBoxLeft.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value) {
                choiceBoxLeftResult[0] = constraintLeftRoll[new_value.intValue()];
                System.out.println("LEFT CARDINALITY: " + choiceBoxLeftResult[0]);
            }
        });


        final String[] choiceBoxDataTypeResult = {null};
        choiceBoxDataTypeResult[0] = reference.getLineType();
        String constraintDataType[] = {BoundLine.BoundLineAssociation(), BoundLine.BoundLineAggregation(), BoundLine.BoundLineComposition(), BoundLine.BoundLineGeneralization()}; // TODO ake to boli xd si nepamatam sad noises ... tak zatial datove typy
        ChoiceBox constraintChoiceBoxMiddle = new ChoiceBox(FXCollections.observableArrayList(constraintDataType));
        constraintChoiceBoxMiddle.setValue(choiceBoxDataTypeResult[0]);
        constraintChoiceBoxLeft.setAccessibleText("Constraint Data Type");
        // add a listener
        constraintChoiceBoxLeft.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value) {
                choiceBoxDataTypeResult[0] = constraintDataType[new_value.intValue()];
                System.out.println("LEFT CARDINALITY: " + choiceBoxDataTypeResult[0]);
            }
        });


        final String[] choiceBoxRightResult = {null};
        choiceBoxRightResult[0] = reference.right_cardinality;
        String constraintRightRoll[] = {"1..n", "0..n", "0..1", "1..1"};
        ChoiceBox constraintChoiceBoxRight = new ChoiceBox(FXCollections.observableArrayList(constraintRightRoll));
        constraintChoiceBoxRight.setValue(choiceBoxRightResult[0]);
        constraintChoiceBoxRight.setAccessibleText("Constraint Right");
        // add a listener
        constraintChoiceBoxRight.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value) {
                choiceBoxRightResult[0] = constraintRightRoll[new_value.intValue()];
                System.out.println("LEFT CARDINALITY: " + choiceBoxRightResult[0]);
            }
        });

        hboxDescription.setMargin(constraintChoiceBoxLeft, new Insets(10, 10, 10, 10));
        hboxDescription.setMargin(constraintChoiceBoxMiddle, new Insets(10, 10, 10, 10));
        hboxDescription.setMargin(constraintChoiceBoxRight, new Insets(10, 10, 10, 10));
        hboxChoice.setAlignment(Pos.CENTER);
        hboxChoice.getChildren().addAll(constraintChoiceBoxLeft, constraintChoiceBoxMiddle, constraintChoiceBoxRight);

        editButton.setOnAction(e -> {
            constraintName[0] = constraintNameTextField.getText();
            reference.setLeftCardinality(choiceBoxLeftResult[0]);
            reference.setLineType(choiceBoxDataTypeResult[0]);
            reference.setRightCardinality(choiceBoxRightResult[0]);
            reference.setLabel(constraintName[0]);
            reference.plabel.setText(reference.getLineString());
            editConstraintPopUpWindow.close();
        });

        HBox hboxLabel = new HBox();
        hboxLabel.setMargin(label, new Insets(10, 10, 20, 10));
        label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        hboxLabel.getChildren().add(label);
        hboxLabel.setAlignment(Pos.CENTER);


        layout.getChildren().addAll(hboxLabel, constraintNameTextField, hboxDescription, hboxChoice, editButton);
        layout.setAlignment(Pos.CENTER);

        editConstraintPopUpWindow.initModality(Modality.APPLICATION_MODAL);
        editConstraintPopUpWindow.setTitle("title");
        editConstraintPopUpWindow.setMinWidth(400);
        editConstraintPopUpWindow.setMinHeight(250);
        editConstraintPopUpWindow.setScene(addAttributeScene);

        editConstraintPopUpWindow.showAndWait();
    }
}
