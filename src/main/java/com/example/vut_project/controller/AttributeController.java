/**
 * @Brief: Attribute representation
 * @File: AttributeController
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project.controller;

import java.util.ArrayList;

/**
 * Class represents attribute with name and type. Type is represented with String
 */
public class AttributeController extends ElementController {

    private String type;
    private String datatype;
    private String accessType;
    private ArrayList<AttributeOperationController> attributeOperationsList;

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
        attributeOperationsList = new ArrayList<>();
    }

    public AttributeController(String name, String type) {
        super(name);
        this.type = type;
        this.accessType = "+";
        this.datatype = "int";
        attributeOperationsList = new ArrayList<>();
    }

    /**
     * Returns Attributes type
     *
     * @return type of attribute
     */
    public String getType() {
        return this.type;
    }

    public String getDatatype() {
        return this.datatype;
    }

    public String getAccessType() {
        return this.accessType;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setDatatype(String datatype){
        this.datatype = datatype;
    }

    public void setAccessType(String accessType){
        this.accessType = accessType;
    }
    

    public String getWholeAttributeString() {
        if (this.type.equals("function")) {
            return this.accessType + this.getName() + "(" + operationTypesReturnList() + ") : " + this.datatype;
        } else {
            return this.accessType + this.getName() + " : " + this.datatype;
        }
    }

    private String operationTypesReturnList() {
        StringBuilder operationTypesReturnList = new StringBuilder();
        for (AttributeOperationController attrOp : attributeOperationsList) {
            operationTypesReturnList.append(attrOp.returnString());
            operationTypesReturnList.append(", ");
        }
        if (operationTypesReturnList.toString().isBlank()) {
            return "";
        }
        ;
        return operationTypesReturnList.toString().substring(0, operationTypesReturnList.toString().lastIndexOf(","));
    }

    public ArrayList<String> operationTypesNames() {
        ArrayList<String> return_list = new ArrayList<String>();
        for (AttributeOperationController attributeOperation : attributeOperationsList) {
            return_list.add(attributeOperation.getName());
        }
        return return_list;
    }

    public void addOperationType(String name, String type) {
        if (operationTypesNames().contains(name)) {
            AlertBox.display("Note", "Argument name is taken", "Understood");
        }
        AttributeOperationController new_attr = new AttributeOperationController(name, type);
        attributeOperationsList.add(new_attr);
    }

    public AttributeOperationController findOperationTypeByName(String name) {
        for (AttributeOperationController attr : attributeOperationsList) {
            if (attr.getName().equals(name)) {
                return attr;
            }
        }
        return null;
    }


    public void removeOperationTypeByName(String name) {
        AttributeOperationController attr = findOperationTypeByName(name);
        if (attr != null) {
            attributeOperationsList.remove(attr);
        }
    }

    public void removeOperationType(AttributeOperationController attr) {
        attributeOperationsList.remove(attr);
    }
}
