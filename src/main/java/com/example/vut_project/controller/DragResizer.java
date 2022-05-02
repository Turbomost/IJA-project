package com.example.vut_project.controller;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class DragResizer {

    /**
     * The margin around the control that a user can click in to start resizing
     * the region.
     */

    private double mouseAnchorX;
    private double mouseAnchorY;

    private static final int RESIZE_MARGIN = 5;

    private Region region;

    private Line line;

    private double y;

    private boolean initMinHeight;

    private boolean dragging;

    DraggableMarker draggableMarker;

    /*public DragResizer(Region aRegion) {
        region = aRegion;
    }*/

    public void makeResizable(Region region, Line line) {
        this.line = line;
        this.region = region;
        //DraggableMarker d = new DraggableMarker();
        //d.makeDraggable(this.region, line);
        //final DragResizer resizer = new DragResizer(region);

        region.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Line resize start " + line);
                mousePressed(event);
            }});
        region.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseDragged(event);
            }});
        region.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseOver(event);
            }});
        region.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseReleased(event);
            }});
    }

    protected void mouseReleased(MouseEvent event) {
        dragging = false;
        region.setCursor(Cursor.DEFAULT);
    }

    protected void mouseOver(MouseEvent event) {
        if(isInDraggableZone(event) || dragging) {
            region.setCursor(Cursor.S_RESIZE);
        }
        else {
            region.setCursor(Cursor.DEFAULT);
        }
    }

    protected boolean isInDraggableZone(MouseEvent event) {
        return event.getY() > (region.getHeight() - RESIZE_MARGIN);
    }

    protected void mouseDragged(MouseEvent event) {
        if(!dragging) {
            if (!isInDraggableZone(event)) {
                System.out.println("Before Move " + region.getChildrenUnmodifiable().get(0));
                double length = line.getEndY() - line.getStartY();
                System.out.println(length);
                region.setLayoutX(event.getSceneX() - mouseAnchorX);
                region.setLayoutY(event.getSceneY() - mouseAnchorY - 60);
                System.out.println("Set to: " + region.getLayoutX());
                System.out.println("Set to: " + region.getLayoutY());
                System.out.println("After Move " + region.getChildrenUnmodifiable().get(0));
            }
            return;
        }

        double mousey = event.getY();

        double newHeight = region.getMinHeight() + (mousey - y);

        region.setMinHeight(newHeight);

        y = mousey;

        System.out.println("RESIZE TO > " + y);
        line.setEndY(y);
        line.setFill(Color.BLACK);
        System.out.println("Line resize end " + line);
    }

    protected void mousePressed(MouseEvent event) {
        dragging = isInDraggableZone(event);
    }
}