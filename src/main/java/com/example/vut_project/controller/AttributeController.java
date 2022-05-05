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

    private final ArrayList<AttributeOperationController> attributeOperationsList;
    public boolean primary;
    public int row;
    private String type;
    private String datatype;
    private String accessType;

    /**
     * Default Attribute constructor
     *
     * @param name name of attribute
     * @param type type of attribute
     */
    public AttributeController(String name, String type, String datatype, String accessType, int row) {
        super(name);
        this.type = type;
        this.accessType = accessType;
        this.datatype = datatype;
        this.row = row;
        this.attributeOperationsList = new ArrayList<>();
        this.primary = false;
    }

    public AttributeController(String name, String type, String datatype, String accessType, int row, boolean primary) {
        super(name);
        this.type = type;
        this.accessType = accessType;
        this.datatype = datatype;
        this.row = row;
        this.attributeOperationsList = new ArrayList<>();
        this.primary = primary;
    }


    public AttributeController(String name, String type) {
        super(name);
        this.type = type;
        this.accessType = "+";
        this.datatype = "int";
        this.attributeOperationsList = new ArrayList<>();
    }

    /**
     * Returns Attributes type
     *
     * @return type of attribute
     */
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatatype() {
        return this.datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getAccessType() {
        return this.accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }


    public String getWholeAttributeString() {
        if (this.type.equals("function")) {
            return this.accessType + this.getName() + "(" + operationTypesReturnList() + ") : " + this.datatype;
        } else {
            if (primary) {
                return this.accessType + " <<PK>> " + this.getName() + " : " + this.datatype;
            }
            return this.accessType + this.getName() + " : " + this.datatype;
        }
    }

    public String operationTypesReturnList() {
        StringBuilder operationTypesReturnList = new StringBuilder();
        for (AttributeOperationController attrOp : this.attributeOperationsList) {
            operationTypesReturnList.append(attrOp.returnString());
            operationTypesReturnList.append(", ");
        }
        if (operationTypesReturnList.toString().isBlank()) {
            return "";
        }
        return operationTypesReturnList.substring(0, operationTypesReturnList.toString().lastIndexOf(","));
    }

    public AttributeOperationController getLastAddedOperationAttribute() {
        return this.attributeOperationsList.get(this.attributeOperationsList.size() - 1);
    }

    public String getLastAddedOperationAttributeString() {
        AttributeOperationController attr = this.getLastAddedOperationAttribute();
        return attr.returnString();
    }

    public ArrayList<String> operationTypesNames() {
        ArrayList<String> return_list = new ArrayList<>();
        for (AttributeOperationController attributeOperation : this.attributeOperationsList) {
            return_list.add(attributeOperation.getName());
        }
        return return_list;
    }

    public AttributeOperationController addOperationType(String name, String type) {
        if (operationTypesNames().contains(name)) {
            AlertBox.display("Note", "Argument name is taken", "Understood");
            return null;
        }
        AttributeOperationController new_attr = new AttributeOperationController(name, type);
        this.attributeOperationsList.add(new_attr);
        return new_attr;
    }

    public AttributeOperationController findOperationTypeByName(String name) {
        for (AttributeOperationController attr : this.attributeOperationsList) {
            if (attr.getName().equals(name)) {
                return attr;
            }
        }
        return null;
    }


    public void removeOperationTypeByName(String name) {
        AttributeOperationController attr = findOperationTypeByName(name);
        if (attr != null) {
            this.removeOperationType(attr);
        } else {
            AlertBox.display("Error", "Error while deleting '" + name + "'", "OK");
        }
    }

    public void removeOperationType(AttributeOperationController attr) {
        this.attributeOperationsList.remove(attr);
    }

    public void setParams(String name, String accessType, String datatype, String type) {
        this.rename(name);
        this.setAccessType(accessType);
        this.setDatatype(datatype);
        this.setType(type);
    }

    public ArrayList<AttributeOperationController> getOperationControllerList() {
        return this.attributeOperationsList;
    }

    public void copyParams(AttributeController new_attr) {
        this.setParams(new_attr.getName(), new_attr.getAccessType(), new_attr.getDatatype(), new_attr.getType());
    }

    public boolean isPrimary() {
        return this.primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }
}
