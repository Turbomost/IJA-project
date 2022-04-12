package com.example.vut_project.controller;

/**
 * Element is every object in ClassDiagram
 */
public class ElementController {

    private String name;

    /**
     * Default constructor for Element
     *
     * @param name name of new Element
     */
    public ElementController(String name) {
        this.name = name;
    }

    /**
     * Returns name of element
     *
     * @return name of element
     */
    public String getName() {
        return this.name;
    }

    /**
     * Renames element to input string
     *
     * @param newName new name of the element
     */
    public void rename(String newName) {
        this.name = newName;
    }
}
