package com.example.vut_project.controller;

import com.example.vut_project.EntityController;
import com.example.vut_project.SequenceDiagramController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class MessageLine extends Line {

    DoubleProperty fromX;
    DoubleProperty fromY;
    DoubleProperty toX;
    DoubleProperty toY;

    SequenceDiagramController sequenceDiagramControllerReference;
    LifeLine fromLifeLine;
    LifeLine toLifeLine;
    EntityController fromEntity;
    EntityController toEntity;

    String label_string;
    public Label plabel;

    public MessageLine(SequenceDiagramController sequenceDiagramControllerReference, EntityController fromEntity, EntityController toEntity, LifeLine lifeLineFrom, LifeLine lifeLineTo, Label label) {
        this.sequenceDiagramControllerReference = sequenceDiagramControllerReference;
        this.fromEntity = fromEntity;
        this.toEntity = toEntity;
        this.fromLifeLine = lifeLineFrom;
        this.toLifeLine = lifeLineTo;
        this.fromX = new SimpleDoubleProperty(lifeLineFrom.anchorPane.getLayoutX());
        this.fromY = new SimpleDoubleProperty(lifeLineTo.anchorPane.getLayoutY());
        this.toX = new SimpleDoubleProperty(lifeLineTo.anchorPane.getLayoutX());
        this.toY = new SimpleDoubleProperty(lifeLineTo.anchorPane.getLayoutY());
        setOnMouseClicked(event -> onMouseClicked(event));
        plabel = label;
        label_string = "Empty Message";
    }

    private void onMouseClicked(MouseEvent event) {
        System.out.println("Clicked");
        ContextMenu menu = new ContextMenu();
        MenuItem item = new MenuItem("Delete Message");
        MenuItem item2 = new MenuItem("Edit");
        menu.getItems().addAll(item, item2);
        item.setOnAction(e -> onDeleteMessageClick(event));
        item2.setOnAction(e -> onEditMessageClick(event));
        menu.show((Node) event.getSource(), event.getScreenX(), event.getScreenY());
    }

    private void onEditMessageClick(MouseEvent event) {
        System.out.println("on edite message click");
        EditMessagePopUp.EditMessagePopUpDisplay(this, "Edit", "Edit Message", "Edit");
    }

    private void onDeleteMessageClick(MouseEvent event) {
        System.out.println("on delete message click");
        fromLifeLine.removeMessageFromLineList(this);
        toLifeLine.removeMessageFromLineList(this);
        sequenceDiagramControllerReference.deleteMessageFromSpace(this);
    }

    public void create_line(){
        setStartX(this.fromX.getValue());
        setStartY(this.fromY.getValue());
        setEndX(this.toX.getValue());
        setEndY(this.toY.getValue());
    }

    public void setLabel_string(String message){
        this.label_string = message;
        create_label();
    }

    public String getLineString(){
        return this.label_string;
    }

    public void create_label(){
        this.plabel.setText(this.getLineString());
        this.plabel.setAlignment(Pos.CENTER);
        this.plabel.layoutXProperty().bind(this.endXProperty().subtract(this.endXProperty().subtract(this.startXProperty()).divide(2)).subtract(this.getLineString().length() * 2.5));
        this.plabel.layoutYProperty().bind(this.endYProperty().subtract(this.endYProperty().subtract(this.startYProperty()).divide(2)).subtract(20));
    }

    public void update_position(Double toY){
        this.setStartY(toY);
        this.setEndY(toY);
    }

    public void makeDashed(){
        setStrokeWidth(2);
        setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
        setStrokeLineCap(StrokeLineCap.BUTT);
        getStrokeDashArray().setAll(10.0, 5.0);
    }
}
