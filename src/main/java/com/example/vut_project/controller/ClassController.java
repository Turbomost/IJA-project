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
    private boolean isAbstract = false;

    private int layout_x = 10;
    private int layout_y = 10;

    /**
     * Default constructor for Class
     *
     * @param name name of new class
     */
    public ClassController(String name) {
        super(name);
        AttributeList = new ArrayList<>();
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
        if (this.AttributeList.contains(attr)) {
            return false;
        }
        this.AttributeList.add(attr);
        return true;
    }

    /**
     * Returns x-position of class
     *
     * @return x-position
     */
    public int getPosition_x() {
        return this.layout_x;
    }

    /**
     * Set x-position of class
     *
     * @param layout_x new x-position
     */
    public void setPosition_x(int layout_x) {
        this.layout_x = layout_x;
    }

    /**
     * Returns y-position of class
     *
     * @return y-position
     */
    public int getPosition_y() {
        return this.layout_y;
    }

    /**
     * Set y-position of class
     *
     * @param layout_y new x-position
     */
    public void setPosition_y(int layout_y) {
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

}
