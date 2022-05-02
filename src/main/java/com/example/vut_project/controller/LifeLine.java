package com.example.vut_project.controller;

import com.example.vut_project.EntityController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class LifeLine extends Line {
    Line life_line;
    EntityController stick_to_entity;
    AnchorPane anchorPane;

    public LifeLine(EntityController stick_to_entity, AnchorPane anchorPane) {
        this.stick_to_entity = stick_to_entity;
        this.anchorPane = anchorPane;
        this.life_line = new Line();
        this.life_line.setStrokeWidth(10.0);
        this.life_line.setStartY(this.stick_to_entity.getLayoutY() + this.stick_to_entity.sequenceVBox.getPrefHeight() + this.life_line.getStrokeWidth() / 2);
        this.life_line.setStartX(this.stick_to_entity.getLayoutX() + this.stick_to_entity.sequenceVBox.getPrefWidth() / 2);
        this.life_line.setEndX(this.stick_to_entity.getLayoutX() + this.stick_to_entity.sequenceVBox.getPrefWidth() / 2);
        this.life_line.setEndY(this.stick_to_entity.getLayoutY() + 50 + this.stick_to_entity.sequenceVBox.getPrefHeight());
        this.life_line.setFill(Color.GRAY);
        this.life_line.setStroke(Color.BLACK);
        this.life_line.resize(80, 200);
        System.out.println("LIFE LINE INSIDE " + this.life_line);
    }

    public Line getLifeLine() {
        return this.life_line;
    }
    public AnchorPane getAnchorPane(){
        return this.anchorPane;
    }
}
