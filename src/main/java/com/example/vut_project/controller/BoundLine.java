/**
 * @Brief: Entity for easier class identification at scene
 * @File: EntityController
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project.controller;

import com.example.vut_project.HelloController;
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

public class BoundLine extends Line {
    private static final double arrowLength = 20;
    private static final double arrowWidth = 7;
    public Line arrow1;
    public Line arrow2;
    DoubleProperty fromX;
    DoubleProperty fromY;
    DoubleProperty toX;
    DoubleProperty toY;
    ClassController from;
    ClassController to;
    HelloController reference;
    BoundLine self;
    String LineType;
    String label_string;
    String left_cardinality;
    String right_cardinality;
    Label plabel;

    public BoundLine(Double startX, Double startY, Double endX, Double endY, ClassController from, ClassController to, HelloController reference, String LineType, Label plabel, String label, String left, String right) {
        this.fromX = new SimpleDoubleProperty(startX);
        this.fromY = new SimpleDoubleProperty(startY);
        this.toX = new SimpleDoubleProperty(endX);
        this.toY = new SimpleDoubleProperty(endY);
        this.from = from;
        this.to = to;
        this.LineType = LineType;
        this.label_string = label;
        this.plabel = plabel;
        this.left_cardinality = left;
        this.right_cardinality = right;
        from.addConstraint(this);
        to.addConstraint(this);
        setStrokeWidth(2);
        setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
        setStrokeLineCap(StrokeLineCap.BUTT);
        getStrokeDashArray().setAll(10.0, 5.0);
        setOnMouseClicked(event -> onMouseClicked(event));
        this.reference = reference;
        this.arrow1 = new Line();
        this.arrow2 = new Line();
        update();
    }

    public static String BoundLineAssociation() {
        return "Association";
    }

    public static String BoundLineAggregation() {
        return "Aggregation";
    }

    public static String BoundLineComposition() {
        return "Composition";
    }

    public static String BoundLineGeneralization() {
        return "Generalization";
    }

    public static String LabelToString(String label) {
        if (label.equals("Zero or More")) {
            return "0 .. N";
        }
        if (label.equals("One or More")) {
            return "1 .. N";
        }
        if (label.equals("Zero or One")) {
            return "0 .. 1";
        }
        if (label.equals("Exactly One")) {
            return "1";
        }
        return "";
    }

    public static String StringToLabel(String label) {
        if (label.equals("0 .. N")) {
            return "Zero or More";
        }
        if (label.equals("1 .. N")) {
            return "One or More";
        }
        if (label.equals("0 .. 1")) {
            return "Zero or One";
        }
        if (label.equals("1")) {
            return "Exactly One";
        }
        return "";
    }

    public void update() {
        System.out.println("eyoou");
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

    public double getXdiff(double x1, double x2) {
        if (Math.abs(x2 - x1) < 100) {
            return 0;
        }
        if (x2 > x1)
            return 120;
        return -120;
    }

    public double getYdiff(double y1, double y2) {
        if (Math.abs(y2 - y1) < 60) {
            return 0;
        }
        if (y2 > y1)
            return 60;
        return -80;
    }

    public void create_line() {
        setStartX(this.fromX.getValue() + 125);
        setStartY(this.fromY.getValue() + 150);
        setEndX(this.toX.getValue() + 125 + getXdiff(this.toX.getValue() + 125, this.getStartX()));
        setEndY(this.toY.getValue() + 150 + getYdiff(this.toY.getValue() + 150, this.getStartY()));
        update();
    }

    public void create_label() {
        this.plabel.setText(this.getLineString());
        this.plabel.setAlignment(Pos.CENTER);
        this.plabel.layoutXProperty().bind(this.endXProperty().subtract(this.endXProperty().subtract(this.startXProperty()).divide(2)).subtract(this.getLineString().length() * 2.5));
        this.plabel.layoutYProperty().bind(this.endYProperty().subtract(this.endYProperty().subtract(this.startYProperty()).divide(2)).subtract(20));
    }

    public void update_position(double fromX, double fromY, double toX, double toY) {
        setStartX(fromX);
        setStartY(fromY);
        setEndX(toX);
        setEndY(toY);
        update();
    }

    public void update_position_from(double fromX, double fromY) {
        setStartX(fromX + 125);
        setStartY(fromY + 150);
        update();
    }

    public void update_position_to(double toX, double toY) {
        setEndX(toX + 125 + getXdiff(toX + 125, this.getStartX()));
        setEndY(toY + 150 + getYdiff(toY + 150, this.getStartY()));
        update();
    }

    public void onMouseClicked(MouseEvent event) {
        System.out.println("Clicked");
        ContextMenu menu = new ContextMenu();
        MenuItem item = new MenuItem("Delete Constraint");
        MenuItem item2 = new MenuItem("Edit");
        menu.getItems().addAll(item, item2);
        item.setOnAction(e -> onDeleteConstraintClick(event));
        item2.setOnAction(e -> onEditConstraintClick(event));
        menu.show((Node) event.getSource(), event.getScreenX(), event.getScreenY());
    }

    private void onEditConstraintClick(MouseEvent event) {
        System.out.println("On edit constraint click");
        EditConstraintPopUp.display(this);
    }

    public void onDeleteConstraintClick(MouseEvent event) {
        System.out.println("BEFORE CONSTRAINT DELETING FROM" + this.from.getConstraintList());
        System.out.println("BEFORE CONSTRAINT DELETING TO" + this.to.getConstraintList());
        this.from.removeConstraint(self);
        this.to.removeConstraint(self);
        System.out.println("deleting constraint");
        System.out.println("AFTER CONSTRAINT DELETING FROM " + this.from.getName() + this.from.getConstraintList());
        System.out.println("AFTER CONSTRAINT DELETING TO " + this.to.getName() + this.to.getConstraintList());
        this.reference.DeleteConstraint(this.self, this.from, this.to);
    }

    public void setSelfReference(BoundLine self) {
        this.self = self;
    }

    public String getLineType() {
        return this.LineType;
    }

    public void setLineType(String lineType) {
        System.out.println("TYPE SET TO " + lineType);
        this.LineType = lineType;
    }

    public void setLeftCardinality(String left_card) {
        System.out.println("LEFT CARDINALITY SET TO " + left_card);
        this.left_cardinality = left_card;
    }

    public void setRightCardinality(String right_card) {
        System.out.println("RIGHT CARDINALITY SET TO " + right_card);
        this.right_cardinality = right_card;
    }

    public String getLabel() {
        return this.label_string;
    }

    public void setLabel(String Label) {
        System.out.println("ACTUALIZING LABEL TO " + Label);
        this.label_string = Label;
    }

    public Label getPLabel() {
        return this.plabel;
    }

    public String getLineString() {
        if (this.LineType.isBlank()) {
            return this.left_cardinality + "     " + this.label_string + "     " + this.right_cardinality;
        }
        return this.left_cardinality + "     " + this.label_string + LineTypeToString(this.LineType) + "     " + this.right_cardinality;
    }

    public String LineTypeToString(String type) {
        if (this.LineType.equals(BoundLineComposition())) {
            return " { " + type + " }";
        }
        if (this.LineType.equals(BoundLineAssociation())) {
            return " < " + type + " >";
        }
        if (this.LineType.equals(BoundLineAggregation())) {
            return " > " + type + " <";
        }
        if (this.LineType.equals(BoundLineGeneralization())) {
            return " --> " + type + " <-- ";
        }
        return "";
    }
}
