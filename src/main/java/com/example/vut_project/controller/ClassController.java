/**
 * @Brief: Class representation
 * @File: ClassController
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project.controller;

import java.util.ArrayList;

/**
 * Class is one object in ClassDiagram. Each class has list with attributes and boolean value whether the class is abstract.
 * Position is represented by layout_x and layout_y
 */
public class ClassController extends ElementController {

    private final ArrayList<AttributeController> AttributeList;
    private ArrayList<BoundLine> ConstraintList;
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
                System.out.println("Attribute in class is duplicate");
                AlertBox.display("Note", "Attribute in class is duplicate", "Understood");
                return false;
            }
        }
        this.AttributeList.add(attr);
        System.out.println("successfully added " + attr.getName());
        System.out.println(this.AttributeList.toString());
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
        String substr = name.substring(0,name.lastIndexOf("("));
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
        System.out.println(ConstraintList.toString());
    }

    public void removeConstraint(BoundLine line) {
        ConstraintList.remove(line);
    }

    public ArrayList getConstraintList() {
        ArrayList<String> nameList = new ArrayList<>();
        for (BoundLine con : this.ConstraintList) {
            nameList.add(con.toString());
        }
        return nameList;
    }

    public void update_constraint() {
        for (BoundLine line : ConstraintList) {
            if (line.from.equals(this)) {
                line.update_position_from(this.getPosition_x(), this.getPosition_y());
            } else {
                line.update_position_to(this.getPosition_x(), this.getPosition_y());
            }
        }
    }

    public void delete_constraints() {
        while (ConstraintList.size() != 0) {
            ConstraintList.get(0).onDeleteConstraintClick(null);
        }
    }
}
