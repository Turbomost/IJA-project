package com.example.vut_project;

import com.example.vut_project.controller.DragResizer;
import com.example.vut_project.controller.DraggableMarker;
import com.example.vut_project.controller.SequenceChoiceBox;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.util.ArrayList;

public class SequenceDiagramController {

    public Pane sequenceSpace;
    private HelloController helloControllerReference;
    private DraggableMarker draggableMaker;
    private DragResizer resizableMaker;
    public ArrayList<EntityController> EntityList;
    EntityController new_entity;
    private int i = 0;

    public void parseHelloControllerAsReference(HelloController reference) {
        this.helloControllerReference = reference;
        draggableMaker = new DraggableMarker();
        resizableMaker = new DragResizer();
        this.EntityList = new ArrayList<>();
    }

    public void onNewSequenceDiagramButtonClick(ActionEvent event) throws IOException {
        System.out.println("New sequence diagram click");
        String st[] = helloControllerReference.classDiagramController.getUniqueClassList(returnList()).toArray(new String[0]);
        String chosen = SequenceChoiceBox.display("Choose", "Choose Class Diagram To display", "OK", st);
        System.out.println("RETUNED: " + chosen);
        if (chosen == null){
            return;
        }
        new_entity = new EntityController(this);
        new_entity.setLayoutX(20+100*i++);
        new_entity.setLayoutY(20);
        draggableMaker.makeDraggableOnXAxis(new_entity);
        sequenceSpace.getChildren().add(new_entity);
        new_entity.setSequenceNameTextField(chosen);
        onNewLifeLineButtonClick(event);
        EntityList.add(new_entity);

    }

    public void onNewLifeLineButtonClick(ActionEvent event) {
        System.out.println("New life line click");
        Line life_line = new Line();
        life_line.setStrokeWidth(10.0);
        life_line.setStartY(new_entity.getLayoutY() + new_entity.sequenceVBox.getPrefHeight() + life_line.getStrokeWidth()/2);
        life_line.setStartX(new_entity.getLayoutX() + new_entity.sequenceVBox.getPrefWidth()/2);
        life_line.setEndX(new_entity.getLayoutX() + new_entity.sequenceVBox.getPrefWidth()/2);
        life_line.setEndY(new_entity.getLayoutY() + 50 + new_entity.sequenceVBox.getPrefHeight());
        life_line.setFill(Color.GRAY);
        life_line.setStroke(Color.BLACK);
        life_line.resize(80, 200);
        System.out.println("New Line created " + life_line);

        AnchorPane p = new AnchorPane();
        p.getChildren().add(life_line);

        resizableMaker.makeResizable(p, life_line, this);
        //draggableMaker.makeDraggable(p, p, life_line);

        sequenceSpace.getChildren().add(p);
    }

    public void onFileOpenButtonClick(ActionEvent event) {
        System.out.println("Open sequence from file click");
        System.out.println(helloControllerReference.classDiagramController.getClassList().toString());
    }
    public void onDeleteLifeLineClick(Event event){
        System.out.println("There should be life line deleted");
        sequenceSpace.getChildren().remove(event.getSource());
    }

    public EntityController findEntity(String name){
        for (EntityController entity : EntityList){
            if (entity.getSequenceNameTextField().equals(name)){
                return entity;
            }
        }
        return null;
    }

    public ArrayList<String> returnList(){
        ArrayList<String> nameList = new ArrayList<>();
        for (EntityController entity : EntityList){
            nameList.add(entity.getSequenceNameTextField());
        }
        return nameList;
    }
}
