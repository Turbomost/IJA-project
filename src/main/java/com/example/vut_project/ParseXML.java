/**
 * @Brief: Parsing XMl file and creating class diagram
 * @File: ParseXML
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project;

import com.example.vut_project.controller.AttributeController;
import com.example.vut_project.controller.ClassController;
import com.example.vut_project.controller.ClassDiagramController;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Parsing XML from file
 */
public class ParseXML extends HelloController {
    private static String FILENAME = "";
    ClassDiagramController classDiagramController = new ClassDiagramController("AllClasses");
    HelloController reference;
    SequenceDiagramController sequenceDiagramController;

    public void input_file_from_button(String file_path) {
        FILENAME = file_path;
    }

    /**
     * Main parsing function
     *
     * @return filled ClassDiagram
     */
    public ClassDiagramController start_parse(HelloController reference) {
        this.reference = reference;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();
            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName()); //class diagram or sequence diagram
            System.out.println(doc.getDocumentElement().getNodeName().equals("class"));
            System.out.println("------");

            NodeList nodeList = doc.getElementsByTagName("element"); //creating list nodes, nodeList is list of <element> tags
            for (int i = 0; i < nodeList.getLength(); i++) { //for every element
                Node nNode = nodeList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) { //for each element
                    Element eElement = (Element) nNode; //each element is basically element, but it is called as node xD
                    NodeList fieldNodes = eElement.getElementsByTagName("arg"); //creating list of arguments inside every element (class, constraint)
                    NodeList accessNodes = eElement.getElementsByTagName("attr_access");
                    NodeList typeNodes = eElement.getElementsByTagName("attr_type");
                    System.out.println("------");
                    System.out.println("NEW ELEMENT");
                    //parsing element information
                    String elementType = eElement.getAttribute("type");  //element type such as : class || constraint || generalization
                    String elementName = eElement.getAttribute("name");  //class name for example : vaškovo_fáro || kolesá
                    ClassController new_class = null;
                    if (elementType.equals("class")) { //creating class
                        new_class = classDiagramController.createClass(elementName);
                        for (int j = 0; j < fieldNodes.getLength(); j++) { //for each argument
                            Node fieldNode = fieldNodes.item(j);
                            NamedNodeMap attributes = fieldNode.getAttributes(); //converting nodes (arguments) into iterable from added dependency
                            Node arg = attributes.getNamedItem("type"); //getting type attributes from each argument
                            Node mandatory = attributes.getNamedItem("mandatory");
                            Node genclass = attributes.getNamedItem("class");
                            if (arg != null && new_class != null) {
                                //TODO -> FILL CLASS WITH ITS ATTRIBUTES
                                if (arg.getTextContent().equals("primarykey")) {
                                    System.out.println("PK> " + fieldNode.getTextContent());
                                    System.out.println("Access node " + accessNodes.getLength());
                                    System.out.println("Type node " + typeNodes.getLength() + " (" + typeNodes.item(j).getTextContent() + ")");
                                    AttributeController new_attribute = new AttributeController(fieldNode.getTextContent(), "attribute", typeNodes.item(j).getTextContent(), accessNodes.item(j).getTextContent() + " ", j);

                                    new_attribute.setPrimary(true);
                                    new_class.addAttribute(new_attribute);
                                }
                                if (arg.getTextContent().equals("attribute")) {
                                    System.out.println("ATTR> " + fieldNode.getTextContent());
                                    AttributeController new_attribute = new AttributeController(fieldNode.getTextContent(), "attribute", typeNodes.item(j).getTextContent(), accessNodes.item(j).getTextContent() + " ", j);
                                    new_class.addAttribute(new_attribute);
                                }
                                if (arg.getTextContent().equals("function")) {
                                    System.out.println("FUNC> " + fieldNode.getTextContent());
                                    AttributeController new_attribute = new AttributeController(fieldNode.getTextContent(), "function", typeNodes.item(j).getTextContent(), accessNodes.item(j).getTextContent() + " ", j);
                                    new_class.addAttribute(new_attribute);
                                }
                                if (arg.getTextContent().equals("position_x")) {
                                    new_class.setPosition_x(Integer.parseInt(fieldNode.getTextContent()));
                                }
                                if (arg.getTextContent().equals("position_y")) {
                                    new_class.setPosition_y(Integer.parseInt(fieldNode.getTextContent()));
                                }
                            }
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classDiagramController;
    }

    public void load_constraints(HelloController reference) {
        this.reference = reference;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();
            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName()); //class diagram or sequence diagram
            System.out.println(doc.getDocumentElement().getNodeName().equals("class"));
            System.out.println("------");

            NodeList nodeList = doc.getElementsByTagName("element"); //creating list nodes, nodeList is list of <element> tags
            for (int i = 0; i < nodeList.getLength(); i++) { //for every element
                Node nNode = nodeList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) { //for each element
                    Element eElement = (Element) nNode; //each element is basically element, but it is called as node xD
                    NodeList fieldNodes = eElement.getElementsByTagName("arg"); //creating list of arguments inside every element (class, constraint)
                    System.out.println("------");
                    System.out.println("NEW ELEMENT");
                    //parsing element information
                    String elementType = eElement.getAttribute("type");  //element type such as : class || constraint || generalization
                    String elementName = eElement.getAttribute("name");  //class name for example : vaškovo_fáro || kolesá
                    if (elementType.equals("constraint")) {
                        String constraintLeft = eElement.getAttribute("left");
                        String constraintRight = eElement.getAttribute("right");
                        String constraintType = eElement.getAttribute("consType");
                        System.out.println("constraint");
                        ClassController constraint_from = null;
                        ClassController constraint_to = null;
                        for (int j = 0; j < fieldNodes.getLength(); j++) { //for each argument
                            Node fieldNode = fieldNodes.item(j);
                            NamedNodeMap attributes = fieldNode.getAttributes(); //converting nodes (arguments) into iterable from added dependency
                            Node arg = attributes.getNamedItem("type"); //getting type attributes from each argument
                            if (arg.getTextContent().equals("constraint_from")) {
                                System.out.println("CONSTRAINT FROM: " + fieldNode.getTextContent());
                                if (fieldNode.getTextContent() != null)
                                    reference.SetConstraintFrom(fieldNode.getTextContent());
                            }
                            if (arg.getTextContent().equals("constraint_to")) {
                                System.out.println("CONSTRAINT TO: " + fieldNode.getTextContent());
                                if (fieldNode.getTextContent() != null) {
                                    reference.SetConstraintTo(fieldNode.getTextContent(), constraintType, elementName, constraintLeft, constraintRight);
                                }
                            }
                        }
                        /*if(constraint_from != null && constraint_to != null){
                            SetConstraintFrom(constraint_from.getName());
                            SetConstraintTo(constraint_to.getName());
                        }*/
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load_operations(HelloController reference) {
        this.reference = reference;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();
            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName()); //class diagram or sequence diagram
            System.out.println(doc.getDocumentElement().getNodeName().equals("constraint"));
            System.out.println("------");

            NodeList nodeList = doc.getElementsByTagName("element"); //creating list nodes, nodeList is list of <element> tags
            for (int i = 0; i < nodeList.getLength(); i++) { //for every element
                Node nNode = nodeList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) { //for each element
                    Element eElement = (Element) nNode; //each element is basically element, but it is called as node xD
                    NodeList fieldNodes = eElement.getElementsByTagName("arg"); //creating list of arguments inside every element (class, constraint)
                    System.out.println("------");
                    System.out.println("NEW ELEMENT");
                    //parsing element information
                    String elementType = eElement.getAttribute("type");  //element type such as : class || constraint || generalization
                    String elementName = eElement.getAttribute("name");  //class name for example : vaškovo_fáro || kolesá
                    if (elementType.equals("operation")) {
                        System.out.println("operation");
                        String operationForClass = null;
                        String operationForArgument = null;
                        String operationName = null;
                        String operationType = null;
                        for (int j = 0; j < fieldNodes.getLength(); j++) { //for each argument
                            Node fieldNode = fieldNodes.item(j);
                            NamedNodeMap attributes = fieldNode.getAttributes(); //converting nodes (arguments) into iterable from added dependency
                            Node arg = attributes.getNamedItem("type"); //getting type attributes from each argument
                            System.out.println("OPERATION FOR CLASS: " + elementName);
                            operationForClass = elementName;
                            if (arg.getTextContent().equals("function_name")) {
                                System.out.println("FUNCTION NAME: " + fieldNode.getTextContent());
                                operationForArgument = fieldNode.getTextContent();
                            }
                            if (arg.getTextContent().equals("parameter_name")) {
                                System.out.println("OPERATION NAME: " + fieldNode.getTextContent());
                                operationName = fieldNode.getTextContent();
                            }
                            if (arg.getTextContent().equals("parameter_type")) {
                                System.out.println("OPERATION TYPE: " + fieldNode.getTextContent());
                                operationType = fieldNode.getTextContent();
                            }
                            if (operationName != null && operationType != null && operationForArgument != null) {
                                System.out.println("CREATING OPERATION");
                                ClassController found_class = reference.classDiagramController.findClass(operationForClass);
                                AttributeController attr = found_class.findAttributeByName(operationForArgument);
                                attr.addOperationType(operationName, operationType);
                            }
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<EntityController> load_sequence(SequenceDiagramController sequenceDiagramController){
        ArrayList<EntityController> entityList = new ArrayList<>();
        System.out.println("LOAD SEQUENCE AT PARSEXML");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();
            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName()); //class diagram or sequence diagram
            System.out.println("------");
            EntityController new_entity = null;
            NodeList nodeList = doc.getElementsByTagName("element"); //creating list nodes, nodeList is list of <element> tags
            System.out.println("NODE LIST_ " + nodeList.toString());
            for (int i = 0; i < nodeList.getLength(); i++) { //for every element
                Node nNode = nodeList.item(i);
                System.out.println("GET NODE NAME " + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) { //for each element
                    Element eElement = (Element) nNode; //each element is basically element, but it is called as node xD
                    NodeList fieldNodes = eElement.getElementsByTagName("arg"); //creating list of arguments inside every element (class, constraint)
                    System.out.println("------");
                    System.out.println("NEW ELEMENT");
                    //parsing element information
                    String elementType = eElement.getAttribute("type");  //element type such as : class || constraint || generalization
                    if (elementType.equals("sequence")) { //creating class
                        //TODO CREATE SEQUENCE NAMED
                        new_entity = new EntityController(sequenceDiagramController, i);
                        String elementName = eElement.getAttribute("name");  //class name for example : vaškovo_fáro || kolesá
                        new_entity.setSequenceNameTextField(elementName);
                        System.out.println("SEQUENCE FOR CLASS NAME " + elementName);
                        for (int j = 0; j < fieldNodes.getLength(); j++) { //for each argument
                            Node fieldNode = fieldNodes.item(j);
                            NamedNodeMap attributes = fieldNode.getAttributes(); //converting nodes (arguments) into iterable from added dependency
                            Node arg = attributes.getNamedItem("type"); //getting type attributes from each argument
                            if (arg.getTextContent().equals("position_x")) {
                                System.out.println("position X: " + fieldNode.getTextContent());
                            }
                            if (arg.getTextContent().equals("position_y")) {
                                System.out.println("position Y: " + fieldNode.getTextContent());
                            }
                        }
                    }
                    if (elementType.equals("life_line")){
                        String elementName = eElement.getAttribute("name");  //class name for example : vaškovo_fáro || kolesá
                        System.out.println("LIFE LINE FOR CLASS NAME: " + elementName);
                        for (int j = 0; j < fieldNodes.getLength(); j++) { //for each argument
                            Node fieldNode = fieldNodes.item(j);
                            NamedNodeMap attributes = fieldNode.getAttributes(); //converting nodes (arguments) into iterable from added dependency
                            Node arg = attributes.getNamedItem("type"); //getting type attributes from each argument
                            if (arg.getTextContent().equals("line")) {
                                System.out.println("LIFE LINE IDENTIFICATOR: " + fieldNode.getTextContent());
                            }
                            if (arg.getTextContent().equals("position_y")) {
                                System.out.println("LIFE LINE POSITION Y: " + fieldNode.getTextContent());
                            }
                            if (arg.getTextContent().equals("position_x")) {
                                System.out.println("LIFE LINE POSITION X: " + fieldNode.getTextContent());
                            }
                            if (arg.getTextContent().equals("length")) {
                                System.out.println("LIFE LINE LENGTH: " + fieldNode.getTextContent());
                            }
                        }

                    }
                }
                if (new_entity!=null){
                    entityList.add(new_entity);
                    new_entity=null;
                }

            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entityList;
    }
}
