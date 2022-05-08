/**
 * @Brief: Entity for easier class identification at scene
 * @File: EntityController
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project;

import com.example.vut_project.controller.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
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
   // private ArrayList <EntityController> entityControllerList;

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
        EntityList.add(new_entity);
        createLifeLineBindToEntity(event, new_entity);
        draggableMaker.makeDraggableOnXAxis(new_entity, new_entity);
        sequenceSpace.getChildren().add(new_entity);
        new_entity.setSequenceNameTextField(chosen);

    }

    public void onNewLifeLineButtonClick(ActionEvent event) {
        System.out.println("On New Life Line Button Click");
    }

    public void createLifeLineBindToEntity(ActionEvent event, EntityController entity) {
        System.out.println("New life line click");
        AnchorPane p = new AnchorPane();
        LifeLine life_line_class = new LifeLine(entity, p);
        p.setLayoutX(life_line_class.getAnchorPane().getLayoutX());
        p.setLayoutY(life_line_class.getAnchorPane().getLayoutY());
        System.out.println("p Layout x: " + p.getLayoutX() + ", p Layout y: " + p.getLayoutY());
        System.out.println("p Scale x: " + p.getScaleX() + ", p Scale y: " + p.getScaleY());
        Line life_line = life_line_class.getLifeLine();
        System.out.println("New Line created " + life_line);
        entity.addLifeLine(life_line_class);

        p.getChildren().add(life_line);

        resizableMaker.makeResizable(p, life_line_class, this);
        //draggableMaker.makeDraggable(p, p, life_line);

        sequenceSpace.getChildren().add(p);
    }

    public void onFileOpenButtonClick(ActionEvent event) throws IOException, ParserConfigurationException, SAXException {
        this.EntityList = new ArrayList<>();
        System.out.println("Open sequence from file click");
        System.out.println(helloControllerReference.classDiagramController.getClassList().toString());
        ParseXML parse = new ParseXML();
        FileChooser fileChooser = new FileChooser();
        File result = fileChooser.showOpenDialog(null);  //select file to open
        if (result != null) {
            File filePath = new File(result.getAbsolutePath());
            String path = filePath.toString();
            String lowercaseName = path.toLowerCase();
            if (lowercaseName.endsWith(".xml")) {
                parse.input_file_from_button(path);
                //classDiagramController = parse.start_parse(this);
                this.EntityList = parse.load_sequence(this);
                this.displayLoadedSequenceDiagram(parse);
                //parse.load_operations(this);
            } else {
                AlertBox.display("Error", "You need to choose XML file format", "I will choose an XML file next time");
                System.out.println("Need to be a xml file");
            }
        }
    }

    public void displayLoadedSequenceDiagram(ParseXML parse) throws IOException, ParserConfigurationException, SAXException {
        for (EntityController seq : EntityList) {
            seq.setLayoutY(20);
            //createLifeLineBindToEntity(null, seq);
            draggableMaker.makeDraggableOnXAxis(seq, seq);;
            sequenceSpace.getChildren().add(seq);
        }
        parse.load_life_lines(this);
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

    public EntityController findSequenceEntity(String name) {
        System.out.println("Trying to find entity in: " + EntityList);
        for (EntityController entity : EntityList) {
            System.out.println("FINDING ENTITY: " + entity.getSequenceNameTextField());
            System.out.println("COMPARED TO: " + name);
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
