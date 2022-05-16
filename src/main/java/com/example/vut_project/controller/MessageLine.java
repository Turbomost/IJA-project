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

    private static final double arrowLength = 20;
    private static final double arrowWidth = 7;
    public Line arrow1;
    public Line arrow2;
    DoubleProperty fromX;
    DoubleProperty fromY;
    DoubleProperty toX;
    DoubleProperty toY;

    public String messageType = ""; // request / reply

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
        label_string = "";
        this.arrow1 = new Line();
        this.arrow1.setStrokeWidth(2.0);
        this.arrow2 = new Line();
        this.arrow2.setStrokeWidth(2.0);
        checkForOperationAvailability();
        update();
    }

    public void checkForOperationAvailability() {
        ClassController r = null;
        if (this.messageType.equals("request")){
            r = toEntity.getSequenceControllerReference().getHelloControllerReference().classDiagramController.findClass(toEntity.getSequenceNameTextField());
        }
        if (this.messageType.equals("reply")){
            r = fromEntity.getSequenceControllerReference().getHelloControllerReference().classDiagramController.findClass(fromEntity.getSequenceNameTextField());
        }
        if (r == null){
            this.changeMessageLineColor(Color.RED);
            return;
        }
        AttributeController a = r.findAttributeByName(label_string);
        if (a == null){
            System.out.println("operation does not exist!");
            this.changeMessageLineColor(Color.RED);
        }else{
            System.out.println("NASTAVUJEM NA BLACK");
            if (a.getType().equals("function")){
                this.changeMessageLineColor(Color.BLACK);
            }
            if (a.getType().equals("attribute")){
                this.changeMessageLineColor(Color.ORANGE);
            }
        }
    }

    public void changeMessageLineColor(Color color){
        arrow1.setStroke(color);
        arrow2.setStroke(color);
        setStroke(color);
    }

    public void update() {
        System.out.println("updating message positions");
        double ex = this.getEndX();
        double ey = this.getEndY();
        double sx = this.getStartX();
        double sy = this.getStartY();

        arrow1.setEndX(ex);
        arrow1.setEndY(ey);
        arrow2.setEndX(ex);
        arrow2.setEndY(ey);

        if (ex == sx && ey == sy) {
            // arrow parts of length 0
            arrow1.setStartX(ex);
            arrow1.setStartY(ey);
            arrow2.setStartX(ex);
            arrow2.setStartY(ey);

        } else {
            double hypot = Math.hypot(sx - ex, sy - ey);
            double factor = arrowLength / hypot;
            double factorO = arrowWidth / hypot;

            // part in direction of main line
            double dx = (sx - ex) * factor;
            double dy = (sy - ey) * factor;

            // part ortogonal to main line
            double ox = (sx - ex) * factorO;
            double oy = (sy - ey) * factorO;

            arrow1.setStartX(ex + dx - oy);
            arrow1.setStartY(ey + dy + ox);
            arrow2.setStartX(ex + dx + oy);
            arrow2.setStartY(ey + dy - ox);
        }
    }

    private void onMouseClicked(MouseEvent event) {
        System.out.println("Clicked");
        ContextMenu menu = new ContextMenu();
        MenuItem item = new MenuItem("Delete Message");
        MenuItem item2 = new MenuItem("Edit");
        menu.getItems().addAll(item2, item);
        item.setOnAction(e -> onDeleteMessageClick(event));
        item2.setOnAction(e -> onEditMessageClick(event));
        menu.show((Node) event.getSource(), event.getScreenX(), event.getScreenY());
    }

    private void onEditMessageClick(MouseEvent event) {
        System.out.println("on edite message click");
        EditMessagePopUp.EditMessagePopUpDisplay(this, "Edit", "Edit Message", "Edit");
        this.checkForOperationAvailability();
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
        update();
    }

    public void makeDashed(){
        setStrokeWidth(2);
        setStroke(Color.BLACK.deriveColor(0, 1, 1, 0.5));
        setStrokeLineCap(StrokeLineCap.BUTT);
        getStrokeDashArray().setAll(10.0, 5.0);
        this.checkForOperationAvailability();
    }
}
