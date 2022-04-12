package com.example.vut_project.controller;

// Element is everything in the class diagram area
public class ElementController {

    private String name;
    // @Param tvojtatko
    public ElementController(String name) {
        this.name = name;
    }


    public String getName() {
        return this.name;
    }


    public void rename(String newName) {
        this.name = newName;
    }
}
