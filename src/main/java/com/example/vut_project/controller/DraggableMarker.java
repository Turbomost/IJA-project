/**
 * @Brief: Makes objects draggable
 * @File: DraggableMarker
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project.controller;

import com.example.vut_project.EntityController;
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
    public void makeDraggable(Node node, ClassController draggable_class) {
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY - 60);
            draggable_class.setPosition_x(node.getLayoutX());
            draggable_class.setPosition_y(node.getLayoutY() - 60);
            draggable_class.update_constraint();
        });
    }

    public void makeDraggable(Node node) {
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });
    }

    public void makeDraggableOnXAxis(Node node, EntityController entity) {
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            for (int i = 0; i < entity.getLifeLineList().size(); i++) {
                entity.getLifeLineList().get(i).getAnchorPane().setLayoutX(entity.getLayoutX() + entity.sequenceVBox.getPrefWidth() / 2);
            }
        });
    }

    public void makeDraggableOnYAxis(Node node, MessageLine messageLine) {
        final Double[] pressedPosition = new Double[1];
        node.setOnMousePressed(mouseEvent -> {
            pressedPosition[0] = messageLine.getLayoutY();
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            messageLine.update_position(mouseEvent.getSceneY() - pressedPosition[0] - 50);
            //node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });
    }
}
