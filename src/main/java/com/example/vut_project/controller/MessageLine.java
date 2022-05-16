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
    public String messageType = ""; // request / reply
    public Label plabel;
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

    public AttributeController FindFunction(ClassController Class, String function){
        AttributeController attr = Class.findAttributeByName(function);
        if (attr != null){
            return attr;
        }
        for (BoundLine boundLine : Class.getConstraintList()){
            if (boundLine.from.equals(Class) && boundLine.getLineType().equals(BoundLine.BoundLineGeneralization())){
                if ((attr = this.FindFunction(boundLine.to, function)) != null){
                    return attr;
                }
            }
        }
        return null;
    }

    public void checkForOperationAvailability() {
        ClassController r = null;
        if (this.messageType.equals("request")) {
            r = toEntity.getSequenceControllerReference().getHelloControllerReference().classDiagramController.findClass(toEntity.getSequenceNameTextField());
            for (BoundLine boundLine : r.getConstraintList()){
                if (boundLine.to.equals(r) && boundLine.getLineType().equals(BoundLine.BoundLineGeneralization())){
                    toEntity = sequenceDiagramControllerReference.findEntity(boundLine.from.getName());
                    this.checkForOperationAvailability();
                }
            }
        }
        if (this.messageType.equals("reply")) {
            this.changeMessageLineColor(Color.BLACK);
            return;
        }
        if (r == null) {
            this.changeMessageLineColor(Color.RED);
            return;
        }

        String function = label_string;
        String args = "";
        if (label_string.contains("(")) {
            function = label_string.substring(0, label_string.lastIndexOf("("));
            args = label_string.substring(label_string.lastIndexOf("(") + 1, label_string.lastIndexOf(")"));
            System.out.println("ARGS: <" + args + ">");
        }


        AttributeController a = this.FindFunction(r, function);
        if (a == null) {
            System.out.println("operation does not exist!");
            this.changeMessageLineColor(Color.RED);
        } else {
            System.out.println("NASTAVUJEM NA BLACK");
            if (a.getType().equals("function")) {

                // Function without brackets
                if (!label_string.contains("(")) {
                    this.changeMessageLineColor(Color.ORANGE);
                } else {

                    // Function with no parameters
                    if (a.getOperationControllerList().size() == 0) {
                        if (args.equals("")) {
                            System.out.println("Empty args Black");
                            this.changeMessageLineColor(Color.BLACK);
                        } else {
                            System.out.println("No empty args");
                            this.changeMessageLineColor(Color.ORANGE);
                        }
                    }

                    // Regex doesn't match
                    else if (!args.matches("^\\w+(, ?\\w+)*$")) {
                        System.out.println("Regex error");
                        this.changeMessageLineColor(Color.ORANGE);
                        return;
                    } else {
                        // Regex do match
                        int lastIndex = 0;
                        int count = 0;
                        while (lastIndex != -1) {
                            lastIndex = args.indexOf(",", lastIndex);
                            if (lastIndex != -1) {
                                count++;
                                lastIndex += 1;
                            }
                        }
                        if (a.getOperationControllerList().size() == count + 1) {
                            System.out.println("good");
                            this.changeMessageLineColor(Color.BLACK);
                        } else {
                            System.out.println("argcount mismatch a.size: " + a.getOperationControllerList().size() + "commas count -1: " + (count - 1));
                            this.changeMessageLineColor(Color.ORANGE);
                        }
                    }
                }
            }
            if (a.getType().equals("attribute")) {
                this.changeMessageLineColor(Color.ORANGE);
            }
        }
    }

    public void changeMessageLineColor(Color color) {
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

    public void create_line() {
        setStartX(this.fromX.getValue());
        setStartY(this.fromY.getValue());
        setEndX(this.toX.getValue());
        setEndY(this.toY.getValue());
    }

    public void setLabel_string(String message) {
        this.label_string = message;
        create_label();
    }

    public String getLineString() {
        return this.label_string;
    }

    public void create_label() {
        this.plabel.setText(this.getLineString());
        this.plabel.setAlignment(Pos.CENTER);
        this.plabel.layoutXProperty().bind(this.endXProperty().subtract(this.endXProperty().subtract(this.startXProperty()).divide(2)).subtract(this.getLineString().length() * 2.5));
        this.plabel.layoutYProperty().bind(this.endYProperty().subtract(this.endYProperty().subtract(this.startYProperty()).divide(2)).subtract(20));
    }

    public void update_position(Double toY) {
        this.setStartY(toY);
        this.setEndY(toY);
        update();
    }

    public void update_position_x_left(Double toLeftX){
        this.setStartX(toLeftX);
        update();
    }

    public void update_position_x_right(Double toRightY){
        this.setEndX(toRightY);
        update();
    }

    public Double get_position_y(){
        return this.getStartY();
    }

    public Double get_position_x(){
        return this.getStartX();
    }

    public void makeDashed() {
        setStrokeWidth(2);
        setStroke(Color.BLACK.deriveColor(0, 1, 1, 0.5));
        setStrokeLineCap(StrokeLineCap.BUTT);
        getStrokeDashArray().setAll(10.0, 5.0);
        this.checkForOperationAvailability();
    }

    public EntityController getFromEntity() {
        return this.fromEntity;
    }

    public EntityController getToEntity() {
        return this.toEntity;
    }

    public LifeLine getFromLifeLine() {
        return this.fromLifeLine;
    }

    public LifeLine getToLifeLine() {
        return this.toLifeLine;
    }
}