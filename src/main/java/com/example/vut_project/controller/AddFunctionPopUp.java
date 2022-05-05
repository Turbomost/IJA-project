package com.example.vut_project.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddFunctionPopUp {
    public static String[] AddFunctionPopUpDisplay(String title, String message, String button_message) {
        String[] accessRule = {"private", "public", "protected", "package"};
        // create a choiceBox
        final String[] attributeName = {null};

        final String[] chosenAccessRule = {null};
        ChoiceBox choiceBox1 = new ChoiceBox(FXCollections.observableArrayList(accessRule));
        choiceBox1.setAccessibleText("Access type");
        // add a listener
        choiceBox1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value) {
                chosenAccessRule[0] = (String) accessRule[new_value.intValue()];
                System.out.println("CHOSEN CD: " + chosenAccessRule[0]);
            }
        });

        final String[] chosenDataType = {null};
        String[] dataTypePopUpChooser = {"int", "bool", "string", "void"};
        ChoiceBox choiceBox2 = new ChoiceBox(FXCollections.observableArrayList(dataTypePopUpChooser));
        choiceBox2.setAccessibleText("Data Type");
        // add a listener
        choiceBox2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value) {
                chosenDataType[0] = (String) dataTypePopUpChooser[new_value.intValue()];
                System.out.println("CHOSEN CD: " + chosenDataType[0]);
            }
        });

        final String[] type = {null}; // function or attribute
        String[] chosenType = {"attribute", "function"};
        ChoiceBox choiceBox3 = new ChoiceBox(FXCollections.observableArrayList(chosenType));
        choiceBox3.setAccessibleText("Data Type");
        // add a listener
        choiceBox3.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value) {
                chosenType[0] = (String) chosenType[new_value.intValue()];
                System.out.println("CHOSEN CD: " + chosenType[0]);
            }
        });


        TextField textField = new TextField();
        textField.setEditable(true);
        textField.prefWidthProperty();
        textField.prefHeightProperty();
        textField.setPromptText("Function Name");

        // add ChoiceBox
        Stage attributePopUpWindow = new Stage();
        attributePopUpWindow.initModality(Modality.APPLICATION_MODAL);
        attributePopUpWindow.setTitle(title);
        attributePopUpWindow.setMinWidth(400);
        attributePopUpWindow.setMinHeight(250);
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button(button_message);
        closeButton.setOnAction(e -> {
            attributeName[0] = textField.getText();
            attributePopUpWindow.close();
        });

        VBox layout = new VBox(10);
        HBox hbox = new HBox();
        HBox hboxDescription = new HBox();

        Text description1 = new Text("Access type");
        Text description2 = new Text("Data type");
        //Text description3 = new Text("Type");
        hboxDescription.getChildren().addAll(description1, description2);
        hboxDescription.setMargin(description1, new Insets(10, 32, 0, 10));
        hboxDescription.setMargin(description2, new Insets(10, 8, 0, 10));
        //hboxDescription.setMargin(description3, new Insets(10, 30, 0, 10));
        hboxDescription.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(choiceBox1, choiceBox2);
        hbox.setAlignment(Pos.CENTER);
        hbox.setMargin(choiceBox1, new Insets(0, 10, 10, 10));
        hbox.setMargin(choiceBox2, new Insets(0, 10, 10, 10));
        //hbox.setMargin(choiceBox3, new Insets(0, 10, 10, 10));

        HBox hboxButton = new HBox();
        hboxButton.getChildren().add(closeButton);
        hboxButton.setMargin(closeButton, new Insets(10, 10, 10, 10));
        hboxButton.setAlignment(Pos.CENTER);

        HBox hboxLabel = new HBox();
        hboxLabel.getChildren().add(label);
        hboxLabel.setAlignment(Pos.CENTER);
        hboxLabel.setMargin(hboxLabel, new Insets(15, 10, 10, 10));
        HBox hboxTextField = new HBox();
        hboxTextField.setAlignment(Pos.CENTER);
        hboxTextField.getChildren().add(textField);
        layout.getChildren().addAll(hboxLabel, hboxDescription, hbox, hboxTextField, hboxButton);
        layout.setAlignment(Pos.CENTER);

        Scene addAttributeScene = new Scene(layout);

        addAttributeScene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    attributeName[0] = textField.getText();
                    attributePopUpWindow.close();
                }
            }
        });

        attributePopUpWindow.setScene(addAttributeScene);
        attributePopUpWindow.showAndWait();
        if ((attributeName[0] == null || chosenAccessRule[0] == null || chosenDataType[0] == null)) {
            return null;
        }
        if (chosenAccessRule[0].equals("private")) chosenAccessRule[0] = "- ";
        if (chosenAccessRule[0].equals("public")) chosenAccessRule[0] = "+ ";
        if (chosenAccessRule[0].equals("protected")) chosenAccessRule[0] = "# ";
        if (chosenAccessRule[0].equals("package")) chosenAccessRule[0] = "~ ";
        String[] result = {null, null, null, null};
        result[0] = chosenAccessRule[0];
        result[1] = attributeName[0];
        result[2] = chosenDataType[0];
        result[3] = "function";
        return result;
    }

    public static AttributeController EditFunctionPopUpDisplay(String title, String message, String button_message, AttributeController old_function) {
        String[] accessRule = {"private", "public", "protected", "package"};
        // create a choiceBox
        final String[] attributeName = {null};

        final String[] chosenAccessRule = {null};
        ChoiceBox choiceBox1 = new ChoiceBox(FXCollections.observableArrayList(accessRule));
        choiceBox1.setAccessibleText("Access type");
        String transform = old_function.getAccessType();
        if (transform.equals("- ")) chosenAccessRule[0] = "private";
        if (transform.equals("+ ")) chosenAccessRule[0] = "public";
        if (transform.equals("# ")) chosenAccessRule[0] = "protected";
        if (transform.equals("~ ")) chosenAccessRule[0] = "package";
        choiceBox1.setValue(chosenAccessRule[0]);
        // add a listener
        choiceBox1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value) {
                chosenAccessRule[0] = (String) accessRule[new_value.intValue()];
                System.out.println("CHOSEN CD: " + chosenAccessRule[0]);
            }
        });

        final String[] chosenDataType = {null};
        String[] dataTypePopUpChooser = {"int", "bool", "string", "void"};
        ChoiceBox choiceBox2 = new ChoiceBox(FXCollections.observableArrayList(dataTypePopUpChooser));
        choiceBox2.setAccessibleText("Data Type");
        chosenDataType[0] = old_function.getDatatype();
        choiceBox2.setValue(chosenDataType[0]);
        // add a listener
        choiceBox2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value) {
                chosenDataType[0] = (String) dataTypePopUpChooser[new_value.intValue()];
                System.out.println("CHOSEN CD: " + chosenDataType[0]);
            }
        });

        final String[] type = {null}; // function or attribute
        String[] chosenType = {"attribute", "function"};
        ChoiceBox choiceBox3 = new ChoiceBox(FXCollections.observableArrayList(chosenType));
        choiceBox3.setAccessibleText("Data Type");

        // add a listener
        choiceBox3.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value) {
                chosenType[0] = (String) chosenType[new_value.intValue()];
                System.out.println("CHOSEN CD: " + chosenType[0]);
            }
        });


        TextField textField = new TextField();
        textField.setEditable(true);
        textField.prefWidthProperty();
        textField.prefHeightProperty();
        textField.setPromptText("Function Name");
        attributeName[0] = old_function.getName();
        textField.setText(attributeName[0]);

        // add ChoiceBox
        Stage attributePopUpWindow = new Stage();
        attributePopUpWindow.initModality(Modality.APPLICATION_MODAL);
        attributePopUpWindow.setTitle(title);
        attributePopUpWindow.setMinWidth(400);
        attributePopUpWindow.setMinHeight(250);
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button(button_message);
        closeButton.setOnAction(e -> {
            attributeName[0] = textField.getText();
            attributePopUpWindow.close();
        });

        VBox layout = new VBox(10);
        HBox hbox = new HBox();
        HBox hboxDescription = new HBox();

        Text description1 = new Text("Access type");
        Text description2 = new Text("Data type");
        //Text description3 = new Text("Type");
        hboxDescription.getChildren().addAll(description1, description2);
        hboxDescription.setMargin(description1, new Insets(10, 32, 0, 10));
        hboxDescription.setMargin(description2, new Insets(10, 8, 0, 10));
        //hboxDescription.setMargin(description3, new Insets(10, 30, 0, 10));
        hboxDescription.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(choiceBox1, choiceBox2);
        hbox.setAlignment(Pos.CENTER);
        hbox.setMargin(choiceBox1, new Insets(0, 10, 10, 10));
        hbox.setMargin(choiceBox2, new Insets(0, 10, 10, 10));
        //hbox.setMargin(choiceBox3, new Insets(0, 10, 10, 10));

        HBox hboxButton = new HBox();
        hboxButton.getChildren().add(closeButton);
        hboxButton.setMargin(closeButton, new Insets(10, 10, 10, 10));
        hboxButton.setAlignment(Pos.CENTER);

        HBox hboxLabel = new HBox();
        hboxLabel.getChildren().add(label);
        hboxLabel.setAlignment(Pos.CENTER);
        hboxLabel.setMargin(hboxLabel, new Insets(15, 10, 10, 10));
        HBox hboxTextField = new HBox();
        hboxTextField.setAlignment(Pos.CENTER);
        hboxTextField.getChildren().add(textField);
        layout.getChildren().addAll(hboxLabel, hboxDescription, hbox, hboxTextField, hboxButton);
        layout.setAlignment(Pos.CENTER);

        Scene addAttributeScene = new Scene(layout);

        addAttributeScene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    attributeName[0] = textField.getText();
                    attributePopUpWindow.close();
                }
            }
        });

        attributePopUpWindow.setScene(addAttributeScene);
        attributePopUpWindow.showAndWait();
        if (attributeName[0] == null) return null;
        if (chosenAccessRule[0] == null) return null;
        if (chosenAccessRule[0].equals("private")) chosenAccessRule[0] = "- ";
        if (chosenAccessRule[0].equals("public")) chosenAccessRule[0] = "+ ";
        if (chosenAccessRule[0].equals("protected")) chosenAccessRule[0] = "# ";
        if (chosenAccessRule[0].equals("package")) chosenAccessRule[0] = "~ ";
        String[] result = {null, null, null, null};
        //old_function.setParams(attributeName[0], chosenAccessRule[0], chosenDataType[0], "function");
        AttributeController new_attr = new AttributeController(attributeName[0], "function", chosenDataType[0], chosenAccessRule[0], old_function.row); //TODO SUS - správne poradie?
        return new_attr;
    }
}
