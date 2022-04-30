package com.example.vut_project;

import com.example.vut_project.controller.ClassController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.TextFieldListCell;

import java.io.IOException;

public class SequenceDiagramController {

    private HelloController reference;

    public void parseHelloControllerAsReference(HelloController reference){
        this.reference = reference;
    }

    public void onNewSequenceDiagramButtonClick(ActionEvent event) {
        System.out.println("New sequence diagram click");
        System.out.println(reference.classDiagramController.getClassList().toString());
    }

    public void onNewLifeLineButtonClick(ActionEvent event) {
        System.out.println("New life line click");
    }

    public void onFileOpenButtonClick(ActionEvent event) {
        System.out.println("Open sequence from file click");
    }
}
