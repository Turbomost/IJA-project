package com.example.vut_project.controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class BoundLine extends Line {
    DoubleProperty fromX;
    DoubleProperty fromY;
    DoubleProperty toX;
    DoubleProperty toY;
    ClassController from;
    ClassController to;

    public BoundLine(Double startX, Double startY, Double endX, Double endY, ClassController from, ClassController to) {
        this.fromX = new SimpleDoubleProperty(startX);
        this.fromY = new SimpleDoubleProperty(startY);
        this.toX = new SimpleDoubleProperty(endX);
        this.toY = new SimpleDoubleProperty(endY);
        this.from = from;
        this.to = to;

        from.addConstraint(this);
        to.addConstraint(this);

        setStrokeWidth(2);
        setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
        setStrokeLineCap(StrokeLineCap.BUTT);
        getStrokeDashArray().setAll(10.0, 5.0);
        setMouseTransparent(true);
    }

    public void create_line() {
        setStartX(this.fromX.getValue());
        setStartY(this.fromY.getValue());
        setEndX(this.toX.getValue());
        setEndY(this.toY.getValue());
    }

    public void update_position(double fromX, double fromY, double toX, double toY) {
        setStartX(fromX);
        setStartY(fromY);
        setEndX(toX);
        setEndY(toY);
    }

    public void update_position_from(double fromX, double fromY) {
        setStartX(fromX);
        setStartY(fromY);
    }


    public void update_position_to(double toX, double toY) {
        setEndX(toX);
        setEndY(toY);
    }
}
