package com.example.vut_project.controller;

public class AttributeOperationController {

    String name;
    String dataType;

    public AttributeOperationController(String name, String dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return this.name;
    }


    public String getDataType() {
        return this.dataType;
    }

    public String returnString() {
        return this.name + " : " + this.dataType;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDataType(String dataType){
        this.dataType = dataType;
    }

    public void setParams(String name, String dataType){
        this.setDataType(dataType);
        this.setName(name);
    }
}
