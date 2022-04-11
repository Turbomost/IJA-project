package com.example.vut_project.controller;

import java.util.ArrayList;

// Operation is kind of attribute in class
public class OperationController extends AttributeController {

    private ArrayList<AttributeController> OperationList;

    public OperationController(String name, String type) {
        super(name, type);
        OperationList = new ArrayList<>();
    }

    /**
     * Creates a new operation in class
     *
     * @param name name of operation
     * @param args arguments of operation
     * @return created object of new operation
     */
    public static OperationController create(String name, String type, AttributeController... args) {
        OperationController Operation = new OperationController(name, type);
        for (AttributeController Attribute : args) {
            Operation.addArgument(Attribute);
        }
        return Operation;
    }

    /**
     * Adds new argument to operation list
     *
     * @param arg new argument
     * @return false if the argument already exists, otherwise true
     */
    public boolean addArgument(AttributeController arg) {
        if (this.OperationList.contains(arg)) {
            return false;
        }
        this.OperationList.add(arg);
        return true;
    }

    /**
     * Returns list of operation arguments
     * @return list of arguments
     */
    public java.util.List<AttributeController> getArguments() {
        return (OperationList);
    }
}

