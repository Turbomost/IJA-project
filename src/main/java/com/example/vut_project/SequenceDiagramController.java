package com.example.vut_project;

import com.example.vut_project.controller.ClassController;
import com.example.vut_project.controller.DraggableMarker;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class SequenceDiagramController {

    public Pane sequenceSpace;
    private HelloController classDiagramReference;
    private DraggableMarker draggableMaker;

    public void parseHelloControllerAsReference(HelloController reference){
        this.classDiagramReference = reference;
        draggableMaker = new DraggableMarker();
    }

    public void onNewSequenceDiagramButtonClick(ActionEvent event) throws IOException {
        System.out.println("New sequence diagram click");
        EntityController new_entity = new EntityController(this);
        new_entity.setLayoutX(20);
        new_entity.setLayoutY(20);
        draggableMaker.makeDraggable(new_entity);
        sequenceSpace.getChildren().add(new_entity);

    }

    public void onNewLifeLineButtonClick(ActionEvent event) {
        System.out.println("New life line click");
    }

    public void onFileOpenButtonClick(ActionEvent event) {
        System.out.println("Open sequence from file click");
    }
}
