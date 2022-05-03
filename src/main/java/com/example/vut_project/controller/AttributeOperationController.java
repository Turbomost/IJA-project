package com.example.vut_project.controller;

public class AttributeOperationController {

    String name;
    String dataType;

    public AttributeOperationController(String name, String dataType){
        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return this.name;
    }
}
