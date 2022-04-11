package com.example.vut_project.controller;

import java.util.ArrayList;

// Class is one object in class diagram
// It stores their AttributeList
public class ClassController extends ElementController {

    private ArrayList<AttributeController> AttributeList;
    private boolean isAbstract = false;


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
     * @param isAbstract boolean value
     */
    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    /**
     * Adds attribute to this classes list
     *
     * @param attr one attribute
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
     * Removes attribute from this classes list
     *
     * @param attr one attribute
     * @return false if this attribute is not in class, otherwise true
     */
    public boolean removeAttribute(AttributeController attr) {
        if (this.AttributeList.contains(attr)) {
            this.AttributeList.remove(attr);
            return true;
        }
        return false;
    }

    /**
     * Returs attribute position in list of attributes
     *
     * @param attr attribute
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
     * @param index index of attribute
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
