package com.example.vut_project.controller;

// Attribute is everything in class
public class AttributeController extends ElementController {

    private String type;

    public AttributeController(String name, String type) {
        super(name);
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

}
