/**
 * @Brief: Attribute representation
 * @File: AttributeController
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project.controller;

/**
 * Class represents attribute with name and type. Type is represented with String
 */
public class AttributeController extends ElementController {

    private String type;
    private String datatype;
    private String accessType;

    /**
     * Default Attribute constructor
     *
     * @param name name of attribute
     * @param type type of attribute
     */
    public AttributeController(String name, String type, String datatype, String accessType) {
        super(name);
        this.type = type;
        this.accessType = accessType;
        this.datatype = datatype;
    }

    public AttributeController(String name, String type) {
        super(name);
        this.type = type;
        this.accessType = "+";
        this.datatype = "int";
    }

    /**
     * Returns Attributes type
     *
     * @return type of attribute
     */
    public String getType() {
        return this.type;
    }

    public String getWholeAttributeString(){
        return this.accessType + this.getName() + ": " + this.datatype;
    }

}
