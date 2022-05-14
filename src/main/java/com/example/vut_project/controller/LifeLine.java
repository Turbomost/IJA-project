/**
 * @Brief: Entity for easier class identification at scene
 * @File: EntityController
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project.controller;

import com.example.vut_project.EntityController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

import java.util.ArrayList;


public class LifeLine extends Line {
    Line life_line;
    EntityController stick_to_entity;
    public AnchorPane anchorPane;
    private final ArrayList<MessageLine> MesssageLineList;

    public LifeLine(EntityController stick_to_entity, AnchorPane anchorPane) {
        this.stick_to_entity = stick_to_entity;
        this.anchorPane = anchorPane;
        this.life_line = new Line();
        this.MesssageLineList = new ArrayList<>();
        this.life_line.setStrokeWidth(10.0);

        this.anchorPane.setLayoutX(this.stick_to_entity.getLayoutX() + this.stick_to_entity.sequenceVBox.getPrefWidth() / 2);
        this.anchorPane.setLayoutY(this.stick_to_entity.getLayoutY() + this.stick_to_entity.sequenceVBox.getPrefHeight() + this.life_line.getStrokeWidth() / 2);

        this.life_line.setFill(Color.GRAY);
        this.life_line.setStroke(Color.BLACK);
        this.life_line.setStartX(0);
        this.life_line.setEndX(0);
        this.life_line.setStartY(0);
        this.life_line.setEndY(80);


        System.out.println("LIFE LINE INSIDE " + this.life_line);
        System.out.println("Layout x: " + this.anchorPane.getLayoutX() + ", Layout y: " + this.anchorPane.getLayoutY());
        System.out.println("Scale x: " + this.anchorPane.getScaleX() + ", Scale y: " + this.anchorPane.getScaleY());
    }

    public Line getLifeLine() {
        return this.life_line;
    }

    public AnchorPane getAnchorPane() {
        return this.anchorPane;
    }

    public EntityController getStick_to_entity(){
        return this.stick_to_entity;
    }

    public void addMessageLineToList(MessageLine messageLine){
        this.MesssageLineList.add(messageLine);
    }

    public void removeMessageFromLineList(MessageLine messageLine){
        this.MesssageLineList.remove(messageLine);
    }

    public ArrayList<MessageLine> getMessageLineList(){
        return MesssageLineList;
    }

    public void setAnchorPaneYLaout(Double layoutY){
        this.anchorPane.setLayoutY(layoutY);
    }

    public void checkForClassAvailability() {
        if (stick_to_entity.getSequenceControllerReference().getHelloControllerReference().classDiagramController.findClass(stick_to_entity.getSequenceNameTextField()) == null){
            life_line.setStroke(Color.RED);
        }else{
            life_line.setStroke(Color.BLACK);
        }
    }
}
