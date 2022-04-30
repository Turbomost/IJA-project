package com.example.vut_project;

import com.example.vut_project.controller.ClassController;
import com.example.vut_project.controller.DragResizer;
import com.example.vut_project.controller.DraggableMarker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class SequenceDiagramController {

    public Pane sequenceSpace;
    private HelloController classDiagramReference;
    private DraggableMarker draggableMaker;
    private DragResizer resizableMaker;

    public void parseHelloControllerAsReference(HelloController reference){
        this.classDiagramReference = reference;
        draggableMaker = new DraggableMarker();
        resizableMaker = new DragResizer();
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
        Line life_line = new Line();
        life_line.setStrokeWidth(10.0);
        life_line.setStartY(10);
        life_line.setStartX(10);
        life_line.setEndX(10);
        life_line.setEndY(50);
        life_line.setFill(Color.GRAY);
        life_line.setStroke(Color.BLACK);
        life_line.resize(80, 200);

        AnchorPane p = new AnchorPane();
        p.getChildren().add(life_line);

        resizableMaker.makeResizable(p, life_line);
        //draggableMaker.makeDraggable(p, p, life_line);

        sequenceSpace.getChildren().add(p);
    }

    public void onFileOpenButtonClick(ActionEvent event) {
        System.out.println("Open sequence from file click");
    }
}
