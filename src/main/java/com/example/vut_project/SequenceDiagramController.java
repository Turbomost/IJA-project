package com.example.vut_project;

import com.example.vut_project.controller.DragResizer;
import com.example.vut_project.controller.DraggableMarker;
import com.example.vut_project.controller.LifeLine;
import com.example.vut_project.controller.SequenceChoiceBox;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class SequenceDiagramController {

    public Pane sequenceSpace;
    public ArrayList<EntityController> EntityList;
    public EntityController new_entity;
    private HelloController helloControllerReference;
    private DraggableMarker draggableMaker;
    private DragResizer resizableMaker;
    private int i = 0;

    public void parseHelloControllerAsReference(HelloController reference) {
        this.helloControllerReference = reference;
        draggableMaker = new DraggableMarker();
        resizableMaker = new DragResizer();
        this.EntityList = new ArrayList<>();
    }

    public void onNewSequenceDiagramButtonClick(ActionEvent event) throws IOException {
        System.out.println("New sequence diagram click");
        String[] st = helloControllerReference.classDiagramController.getUniqueClassList(returnList()).toArray(new String[0]);
        String chosen = SequenceChoiceBox.display("Choose", "Choose Class Diagram To display", "OK", st);
        System.out.println("RETURNED: " + chosen);
        if (chosen == null) {
            return;
        }
        new_entity = new EntityController(this, i);
        new_entity.setLayoutX(20 + 100 * i++);
        new_entity.setLayoutY(20);
        createLifeLineBindToEntity(event);
        draggableMaker.makeDraggableOnXAxis(new_entity, new_entity);
        sequenceSpace.getChildren().add(new_entity);
        new_entity.setSequenceNameTextField(chosen);
        EntityList.add(new_entity);

    }

    public void onNewLifeLineButtonClick(ActionEvent event) {
        System.out.println("On New Life Line Button Click");
    }

    public void createLifeLineBindToEntity(ActionEvent event) {
        System.out.println("New life line click");
        AnchorPane p = new AnchorPane();
        LifeLine life_line_class = new LifeLine(new_entity, p);
        Line life_line = life_line_class.getLifeLine();
        System.out.println("New Line created " + life_line);
        new_entity.addLifeLine(life_line_class);

        p.getChildren().add(life_line);

        resizableMaker.makeResizable(p, life_line, this);
        //draggableMaker.makeDraggable(p, p, life_line);

        sequenceSpace.getChildren().add(p);
    }

    public void onFileOpenButtonClick(ActionEvent event) {
        System.out.println("Open sequence from file click");
        System.out.println(helloControllerReference.classDiagramController.getClassList().toString());
    }

    public void onDeleteLifeLineClick(Event event) {
        System.out.println("There should be life line deleted");
        sequenceSpace.getChildren().remove(event.getSource());
    }

    public EntityController findEntity(String name) {
        for (EntityController entity : EntityList) {
            if (entity.getSequenceNameTextField().equals(name)) {
                return entity;
            }
        }
        return null;
    }

    public ArrayList<String> returnList() {
        ArrayList<String> nameList = new ArrayList<>();
        for (EntityController entity : EntityList) {
            nameList.add(entity.getSequenceNameTextField());
        }
        return nameList;
    }

    public void deleteAllLifeLines(ArrayList<LifeLine> lifelines) {
        for (LifeLine lifeLine : lifelines) {
            System.out.println(lifeLine.getClass());
            sequenceSpace.getChildren().remove(lifeLine.getAnchorPane());
            sequenceSpace.getChildren().remove(lifeLine);
        }
    }
}
