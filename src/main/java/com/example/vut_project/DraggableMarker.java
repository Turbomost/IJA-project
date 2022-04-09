package com.example.vut_project;

import javafx.scene.Node;

public class DraggableMarker {

    private double mouseAnchorX;
    private double mouseAnchorY;

    public void makeDraggable(Node node){
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX - 50);
            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY - 150);
        });
    }
}
