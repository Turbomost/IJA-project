package com.example.vut_project.controller;

// Attribute is everything in class
public class AttributeController extends ElementController {

    private ClassifierController type;

    public AttributeController(String name, ClassifierController type) {
        super(name);
        this.type = type;
    }

    public ClassifierController getType() {
        return this.type;
    }

}
