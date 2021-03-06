/**
 * @Brief: Entity for easier class identification at scene
 * @File: EntityController
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project.controller;

import com.example.vut_project.SequenceDiagramController;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class DragResizer {

    private static final int RESIZE_MARGIN = 5;
    DraggableMarker draggableMarker;
    /**
     * The margin around the control that a user can click in to start resizing
     * the region.
     */

    private double mouseAnchorX;
    private double mouseAnchorY;
    private double y;
    private boolean initMinHeight;
    private boolean dragging;

    /*public DragResizer(Region aRegion) {
        region = aRegion;
    }*/

    public void makeResizable(Region region, LifeLine line, SequenceDiagramController reference) {

        //DraggableMarker d = new DraggableMarker();
        //d.makeDraggable(this.region, line);
        //final DragResizer resizer = new DragResizer(region);

        region.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Line resize start " + line);
                if (event.getButton() == MouseButton.SECONDARY) {
                    System.out.println("Secondary mouse button on Life Line");
                    ContextMenu menu = new ContextMenu();
                    MenuItem item = new MenuItem("Delete Life Line");
                    MenuItem item2 = new MenuItem("Create Message");
                    MenuItem item3 = new MenuItem("Paste Message");
                    MenuItem item4 = new MenuItem("Constructor Message");
                    menu.getItems().addAll(item2, item3, item);
                    item.setOnAction(e -> reference.onDeleteLifeLineClick(event, line));
                    item2.setOnAction(e -> reference.onCreateMessageLifeLineClick(event, line));
                    item3.setOnAction(e -> reference.onPasteMessageLifeLineClick(event, line));
                    item4.setOnAction(e -> reference.onCreateConstructorMessageLifeLineClick(event));
                    menu.show((Node) event.getSource(), event.getScreenX(), event.getScreenY());

                } else {
                    mousePressed(event, region);
                }
            }
        });
        region.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseDragged(event, region, line, reference);
            }
        });
        region.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseOver(event, region);
            }
        });
        region.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseReleased(event, region);
            }
        });
    }

    protected void mouseReleased(MouseEvent event, Region region) {
        dragging = false;
        region.setCursor(Cursor.DEFAULT);
    }

    protected void mouseOver(MouseEvent event, Region region) {
        if (isInDraggableZone(event, region) || dragging) {
            region.setCursor(Cursor.S_RESIZE);
        } else {
            region.setCursor(Cursor.MOVE);
        }
    }

    protected boolean isInDraggableZone(MouseEvent event, Region region) {
        return event.getY() > (region.getHeight() - RESIZE_MARGIN);
    }

    protected void mouseDragged(MouseEvent event, Region region, LifeLine line, SequenceDiagramController reference) {
        if (!dragging) {
            if (!isInDraggableZone(event, region)) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (event.getSceneY() - 125 < 0) return;
                        System.out.println("Before Move " + region.getChildrenUnmodifiable().get(0));
                        double length = line.getEndY() - line.getStartY();
                        System.out.println(length);
                        line.getAnchorPane().setLayoutY(event.getSceneY() - 60);
                        //region.setLayoutY(event.getSceneY() - mouseAnchorY - 140);
                        System.out.println("Set to: " + region.getLayoutX());
                        System.out.println("Set to: " + region.getLayoutY());
                        System.out.println("After Move " + region.getChildrenUnmodifiable().get(0));
                }
            }
            return;
        }

        double mousey = event.getY();

        double newHeight = region.getMinHeight() + (mousey - y);

        if (newHeight > 10)
        region.setMinHeight(newHeight);

        y = mousey;
        if (y > 10) {
            System.out.println("RESIZE TO > " + y);
            line.getLifeLine().setEndY(y);
            line.getLifeLine().setFill(Color.BLACK);
            System.out.println("Line resize end " + line);
        }
    }

    protected void mousePressed(MouseEvent event, Region region) {
        dragging = isInDraggableZone(event, region);
    }
}