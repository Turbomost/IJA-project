package com.example.vut_project.controller;

// Element is everything in the class diagram area
public class ElementController {

    private String name;

    public ElementController(String name) {
        this.name = name;
    }

    /**
     * Returns current name of the element
     * @return name of the element
     */
    public String getName() {
        return this.name;
    }

    /**
     * Renames element
     * @param new name of the element
     */
    public void rename(String newName) {
        this.name = newName;
    }
}
