/**
 * @Brief: Parsing XMl file and creating class diagram
 * @File: ParseXML
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project;

import com.example.vut_project.controller.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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

    public ArrayList<EntityController> load_sequence(SequenceDiagramController sequenceDiagramController) {
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
                                new_entity.setLayoutX(Double.parseDouble(fieldNode.getTextContent()));
                            }
                            if (arg.getTextContent().equals("position_y")) {
                                System.out.println("position Y: " + fieldNode.getTextContent());
                                new_entity.setLayoutY(Double.parseDouble(fieldNode.getTextContent()));
                            }
                        }
                    }

                }
                if (new_entity != null) {
                    entityList.add(new_entity);
                    new_entity = null;
                }

            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entityList;
    }

    public void load_life_lines(SequenceDiagramController sequenceDiagramController) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
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
            Element eElement = (Element) nNode; //each element is basically element, but it is called as node xD
            if (nNode.getNodeType() == Node.ELEMENT_NODE) { //for each element
                NodeList fieldNodes = eElement.getElementsByTagName("arg"); //creating list of arguments inside every element (class, constraint)
                System.out.println("------");
                System.out.println("NEW ELEMENT");
                //parsing element information
                String elementType = eElement.getAttribute("type");  //element type such as : class || constraint || generalization
                if (elementType.equals("life_line")) {
                    DragResizer resizableMaker;
                    resizableMaker = new DragResizer();
                    String elementName = eElement.getAttribute("name");  //class name for example : vaškovo_fáro || kolesá
                    System.out.println("LIFE LINE FOR CLASS NAME: " + elementName);
                    EntityController line_for_entity = null;
                    LifeLine life_line_class = null;
                    AnchorPane p = null;
                    for (int j = 0; j < fieldNodes.getLength(); j++) { //for each argument
                        Node fieldNode = fieldNodes.item(j);
                        NamedNodeMap attributes = fieldNode.getAttributes(); //converting nodes (arguments) into iterable from added dependency
                        Node arg = attributes.getNamedItem("type"); //getting type attributes from each argument
                        if (arg.getTextContent().equals("line")) {
                            p = new AnchorPane();
                            System.out.println("SEARCHING FOR: " + fieldNode.getTextContent());
                            System.out.println("EELEMENT NAME:" + elementName);
                            System.out.println(sequenceDiagramController);
                            line_for_entity = sequenceDiagramController.findSequenceEntity(elementName);
                            sequenceDiagramController.createLifeLineBindToEntity(null, line_for_entity);
                            System.out.println("LINE FOR ENTITY: " + line_for_entity.getSequenceNameTextField());
                            System.out.println("LIFE LINE IDENTIFICATOR: " + fieldNode.getTextContent());
                        }
                        if (arg.getTextContent().equals("position_y")) {
                            System.out.println("LIFE LINE POSITION Y: " + fieldNode.getTextContent());
                            line_for_entity.getLifeLineList().get(line_for_entity.getLifeLineList().size()-1).getAnchorPane().setLayoutY(Double.parseDouble(fieldNode.getTextContent()));
                        }
                        if (arg.getTextContent().equals("position_x")) {
                            System.out.println("LIFE LINE POSITION X: " + fieldNode.getTextContent());
                        }
                        if (arg.getTextContent().equals("length")) {
                            System.out.println("LIFE LINE LENGTH: " + fieldNode.getTextContent());
                            int lastIndex = line_for_entity.getLifeLineList().size()-1;
                            line_for_entity.getLifeLineList().get(lastIndex).getAnchorPane().setMinHeight(Double.parseDouble(fieldNode.getTextContent()));
                            System.out.println(">>>> " + line_for_entity.getLifeLineList().get(lastIndex).getAnchorPane().getMinHeight());
                            line_for_entity.getLifeLineList().get(lastIndex).getLifeLine().setEndY(line_for_entity.getLifeLineList().get(lastIndex).getLifeLine().getStartY() + Double.parseDouble(fieldNode.getTextContent()));
                        }
                    }
                }
            }
        }
    }
    public void saveClassDiagramInFile(HelloController helloControllerReference){
        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("program");
            doc.appendChild(rootElement);
//TODO for loop from here FOR CLASS CREATING
            // element
            Element superElement = doc.createElement("element");
            rootElement.appendChild(superElement);

            // setting attribute to element
            Attr attr = doc.createAttribute("type");
            attr.setValue("class");
            superElement.setAttributeNode(attr);
            attr = doc.createAttribute("name");
            attr.setValue("CLASS_NAME");                                                // TODO inset class name
            superElement.setAttributeNode(attr);
//TODO for loop for CLASS ATTRIBUTES AND FUNCTIONS
            // element
            Element argElement = doc.createElement("arg");
            Attr attrType = doc.createAttribute("type");
            attrType.setValue("attribute");                                             // TODO function or attribute
            argElement.setAttributeNode(attrType);
            argElement.appendChild(doc.createTextNode("ATTRIBUTE_NAME"));         // TODO inset attribute name
            superElement.appendChild(argElement);

            // subelement attribute access
            Element attrAccessElement = doc.createElement("attr_access");
            Attr subAttrAccess = doc.createAttribute("type");
            subAttrAccess.setValue("attribute_access");
            attrAccessElement.setAttributeNode(subAttrAccess);
            attrAccessElement.appendChild(doc.createTextNode("+"));              // TODO insert access type + - #
            superElement.appendChild(attrAccessElement);

            // subelement attribute type
            Element attrTypeElement = doc.createElement("attr_type");
            Attr subAttrType = doc.createAttribute("type");
            subAttrType.setValue("attribute_type");
            attrTypeElement.setAttributeNode(subAttrType);
            attrTypeElement.appendChild(doc.createTextNode("String"));               // TODO insert data type
            superElement.appendChild(attrTypeElement);
//TODO END LOOP FOR CLASS ATTRIBUTES AND FUNCTIONS
            // position X
            Element classPositionElementX = doc.createElement("arg");
            Attr subPositionX = doc.createAttribute("type");
            subPositionX.setValue("position_x");
            classPositionElementX.setAttributeNode(subPositionX);
            classPositionElementX.appendChild(doc.createTextNode("500"));               // TODO position
            superElement.appendChild(classPositionElementX);

            // position Y
            Element classPositionElementY = doc.createElement("arg");
            Attr subPositionY = doc.createAttribute("type");
            subPositionY.setValue("position_y");
            classPositionElementY.setAttributeNode(subPositionY);
            classPositionElementY.appendChild(doc.createTextNode("200"));               // TODO position
            superElement.appendChild(classPositionElementY);
// TODO for LOOP for CLASS CREATING till HERE

// TODO for LOOP for creating CONSTRAINTS
            superElement = doc.createElement("element");
            rootElement.appendChild(superElement);

            // setting attribute to element
            attr = doc.createAttribute("type");
            attr.setValue("constraint");
            superElement.setAttributeNode(attr);
            attr = doc.createAttribute("name");
            attr.setValue("CONSTRAINT_NAME");                                           // TODO constraint name
            superElement.setAttributeNode(attr);
            attr = doc.createAttribute("left");
            attr.setValue("left");                                                // TODO left cardinality
            superElement.setAttributeNode(attr);
            attr = doc.createAttribute("right");
            attr.setValue("right");                                                // TODO right cardinality
            superElement.setAttributeNode(attr);
            attr = doc.createAttribute("consType");
            attr.setValue("CONSTRAINT_GENERALIZATION");                                       // TODO constraint type
            superElement.setAttributeNode(attr);

            argElement = doc.createElement("arg");
            attrType = doc.createAttribute("type");
            attrType.setValue("constraint_from");
            argElement.setAttributeNode(attrType);
            argElement.appendChild(doc.createTextNode("CONSTRAINT_FROM"));         // TODO constraint from
            superElement.appendChild(argElement);

            argElement = doc.createElement("arg");
            attrType = doc.createAttribute("type");
            attrType.setValue("constraint_to");
            argElement.setAttributeNode(attrType);
            argElement.appendChild(doc.createTextNode("CONSTRAINT_TO"));         // TODO constraint to
            superElement.appendChild(argElement);
// TODO end loop creating constraints

//TODO operations loop
            superElement = doc.createElement("element");
            rootElement.appendChild(superElement);

            attr = doc.createAttribute("type");
            attr.setValue("operation");
            superElement.setAttributeNode(attr);
            attr = doc.createAttribute("name");
            attr.setValue("CLASS NAME");                        // TODO operation bellong to class name
            superElement.setAttributeNode(attr);

            argElement = doc.createElement("arg");
            attrType = doc.createAttribute("type");
            attrType.setValue("function_name");
            argElement.setAttributeNode(attrType);
            argElement.appendChild(doc.createTextNode("FUNCTION_NAME"));         // TODO function name
            superElement.appendChild(argElement);

            argElement = doc.createElement("arg");
            attrType = doc.createAttribute("type");
            attrType.setValue("parameter_name");
            argElement.setAttributeNode(attrType);
            argElement.appendChild(doc.createTextNode("PARAMETER_NAME"));         // TODO function parameter name
            superElement.appendChild(argElement);

            argElement = doc.createElement("arg");
            attrType = doc.createAttribute("type");
            attrType.setValue("parameter_type");
            argElement.setAttributeNode(attrType);
            argElement.appendChild(doc.createTextNode("PARAMETER_TYPE"));         // TODO operation parameter type
            superElement.appendChild(argElement);
//TODO END LOOP FOR OPERATIONS

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:\\Users\\pindo\\OneDrive\\Documents\\GitHub\\IJA_Project\\data\\writetest.xml")); // TODO edit path
            transformer.transform(source, result);

            // Output to console for testing, but its broken so vscode and format
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
