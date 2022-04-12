package com.example.vut_project.controller;

/**
 * Class represents attribute with name and type. Type is represented with String
 */
public class AttributeController extends ElementController {

    private final String type;

    /**
     * Default Attribute constructor
     *
     * @param name name of attribute
     * @param type type of attribute
     */
    public AttributeController(String name, String type) {
        super(name);
        this.type = type;
    }

    /**
     * Returns Attributes type
     *
     * @return type of attribute
     */
    public String getType() {
        return this.type;
    }

}
