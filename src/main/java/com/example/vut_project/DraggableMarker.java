package com.example.vut_project;

import javafx.scene.Node;

public class DraggableMarker {

    private double mouseAnchorX;
    private double mouseAnchorY;

    public void makeDraggable(Node node){
        node.setOnMousePressed(mouseEvent -> {
            System.out.println("event");
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            System.out.println("drag");
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });
    }
}
