package com.example.vut_project.controller;

public class ClassifierController extends ElementController{

    private boolean isUserDefined = true;

    ClassifierController(java.lang.String name) {
        super(name);
    }

    ClassifierController(java.lang.String name, boolean isUserDefined) {
        super(name);
        this.isUserDefined = isUserDefined;
    }

    public boolean isUserDefined() {
        return this.isUserDefined;
    }
}
