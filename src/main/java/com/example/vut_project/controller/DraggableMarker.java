package com.example.vut_project.controller;

import javafx.scene.Node;

/**
 * Class that makes every object draggable
 */
public class DraggableMarker {

    private double mouseAnchorX;
    private double mouseAnchorY;

    /**
     * Dedfault constructor for DraggableMaker
     *
     * @param node node to make draggable
     */
    public void makeDraggable(Node node) {
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY - 60);
        });
    }
}
