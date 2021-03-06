/**
 * @Brief: Class representation
 * @File: ClassController
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project.controller;

import com.example.vut_project.EntityController;
import com.example.vut_project.HelloController;

import java.util.ArrayList;

/**
 * Class is one object in ClassDiagram. Each class has list with attributes and boolean value whether the class is abstract.
 * Position is represented by layout_x and layout_y
 */
public class ClassController extends ElementController {

    private final ArrayList<AttributeController> AttributeList;
    private final ArrayList<BoundLine> ConstraintList;
    private final ArrayList<ClassController> GeneralizationList;
    private boolean isAbstract = false;

    private double layout_x = 10;
    private double layout_y = 10;

    /**
     * Default constructor for Class
     *
     * @param name name of new class
     */
    public ClassController(String name) {
        super(name);
        AttributeList = new ArrayList<>();
        ConstraintList = new ArrayList<>();
        GeneralizationList = new ArrayList<>();
    }

    /**
     * Check whether this class is abstract
     *
     * @return boolean
     */
    public boolean isAbstract() {
        return this.isAbstract;
    }

    /**
     * Set class abstract value
     *
     * @param isAbstract boolean value whether the class is abstract
     */
    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    /**
     * Adds attribute to this classes list
     *
     * @param attr attribute to be added
     * @return false if this attribute is already in class, otherwise true
     */
    public boolean addAttribute(AttributeController attr) {
        for (AttributeController attribute : AttributeList) {
            System.out.println("attr: " + attr.getName() + ", attrlist: " + attribute.getName());
            if (attribute.getName().equals(attr.getName())) {
                System.out.println("Attribute or operation with the same name alerady exists");
                AlertBox.display("Note", "Attribute or operation with name '" + attr.getName() + "' already exists!", "Understood");
                return false;
            }
        }
        this.AttributeList.add(attr);
        System.out.println("successfully added " + attr.getName());
        System.out.println(this.AttributeList);
        return true;
    }

    /**
     * Returns x-position of class
     *
     * @return x-position
     */
    public double getPosition_x() {
        return this.layout_x;
    }

    /**
     * Set x-position of class
     *
     * @param layout_x new x-position
     */
    public void setPosition_x(double layout_x) {
        this.layout_x = layout_x;
    }

    /**
     * Returns y-position of class
     *
     * @return y-position
     */
    public double getPosition_y() {
        return this.layout_y;
    }

    /**
     * Set y-position of class
     *
     * @param layout_y new x-position
     */
    public void setPosition_y(double layout_y) {
        this.layout_y = layout_y;
    }

    /**
     * Removes attribute from this classes list
     *
     * @param attr attribute to be deleted
     * @return false if this attribute is not in class, otherwise true
     */
    public boolean removeAttribute(AttributeController attr) {
        if (this.AttributeList.contains(attr)) {
            this.AttributeList.remove(attr);
            return true;
        }
        return false;
    }

    public AttributeController findAttributeByName(String name) {
        for (AttributeController attr : this.AttributeList) {
            if (attr.getName().equals(name)) {
                return attr;
            }
        }
        if (!name.contains("(")) {
            return null;
        }
        String substr = name.substring(0, name.lastIndexOf("("));
        System.out.println("<" + substr + ">");
        for (AttributeController attr : this.AttributeList) {
            if (attr.getName().equals(substr)) {
                return attr;
            }
        }
        return null;
    }


    /**
     * Returs attribute position in list of attributes
     *
     * @param attr attribute to find
     * @return position or list, otherwise -1
     */
    public int getAttrPosition(AttributeController attr) {
        if (this.AttributeList.contains(attr)) {
            return this.AttributeList.indexOf(attr);
        }
        return -1;
    }

    /**
     * Removes attribute at position in list of attributes
     *
     * @param index index of attribute to be found
     * @return true if successful, otherwise false
     */
    public boolean removeAttrPosition(int index) {
        try {
            this.AttributeList.remove(index);
        } catch (Exception E) {
            return false;
        }
        return true;
    }

    /**
     * Moves attribute in dreamed position
     *
     * @param attr attribute
     * @param pos  position
     * @return 0 if successful, otherwise -1
     */
    public int moveAttrAtPosition(AttributeController attr, int pos) {
        if (this.AttributeList.contains(attr)) {
            AttributeList.remove(attr);
            AttributeList.add(pos, attr);
            return 0;
        }
        return -1;
    }

    /**
     * Returns list of attributes
     *
     * @return list of attributes
     */
    public java.util.List<AttributeController> getAttributes() {
        return AttributeList;
    }

    public java.util.List<String> getAttributesList() {
        ArrayList<String> nameList = new ArrayList<>();
        for (AttributeController attr : this.AttributeList) {
            nameList.add(attr.getName());
        }
        return nameList;
    }

    public void addConstraint(BoundLine line) {
        ConstraintList.add(line);
        System.out.println(ConstraintList);
    }

    public void removeConstraint(BoundLine line) {
        ConstraintList.remove(line);
    }

    public ArrayList<String> getConstraintListString() {
        ArrayList<String> nameList = new ArrayList<>();
        for (BoundLine con : this.ConstraintList) {
            nameList.add(con.toString());
        }
        return nameList;
    }

    public void update_constraint() {
        for (BoundLine line : ConstraintList) {
            if (line.from.equals(this)) {
                line.setFromX(this.getPosition_x());
                line.setFromY(this.getPosition_y());
                line.update_position_from(this.getPosition_x(), this.getPosition_y());
            } else {
                line.setToX(this.getPosition_x());
                line.setToY(this.getPosition_y());
                line.update_position_to(this.getPosition_x(), this.getPosition_y());
            }
        }
    }

    public void delete_constraints() {
        while (ConstraintList.size() != 0) {
            ConstraintList.get(0).onDeleteConstraintClick(null);
        }
    }

    public void setGeneralizations() {
        this.GeneralizationList.removeAll(this.GeneralizationList);
        for (BoundLine Line : this.ConstraintList) {
            if (Line.LineType.equals(BoundLine.BoundLineGeneralization()) && Line.from == this) {
                System.out.println("Generalization[" + this.getName() + "] = " + Line.to.getName());
                this.GeneralizationList.add(Line.to);
            }
        }
    }

    public void updateOverrides(HelloController reference) {
        //System.out.println(" --> START UPDATE");
        for (AttributeController attr : this.AttributeList) {
            //System.out.println(" --> attr: " + attr.getName());
            if (attr.getType().equals("function")) {
                //System.out.println(" --> attr je funkce");
                attr.setOverride(false);
                for (ClassController Class : GeneralizationList) {
                    //System.out.println(" --> comparing to class: " + Class.getName());
                    for (AttributeController currAttr : Class.AttributeList) {
                        //System.out.println(" --> attr in class: " + currAttr.getName());
                        if (currAttr.getType().equals("function")) {
                            //System.out.println(" --> attr in class is function");
                            if (attr.getName().equals(currAttr.getName())) {
                                //System.out.println(" --> FOUND!! <-- ");
                                attr.setOverride(true);
                            }
                        }
                    }
                }
                EntityController entity = reference.findEntityByName(this.getName());
                if (entity != null) {
                    //System.out.println(">> Found entity: " + entity.getNameTextField());
                    int index = entity.getAttributeIndex(attr.getName());
                    //System.out.println(">> index : " + index);
                    if (index != -1) {
                        entity.entityAttributeView.getItems().set(index, attr.getWholeAttributeString());
                        //System.out.println("NAME changed:: {" + attr.getWholeAttributeString() + "}");
                    }
                }
            }
        }
    }

    public ArrayList<BoundLine> getConstraintList() {
        return this.ConstraintList;
    }
}
