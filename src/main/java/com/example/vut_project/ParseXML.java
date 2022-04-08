package com.example.vut_project;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.events.Attribute;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ParseXML {
    private static String FILENAME = "";


    public void input_file_from_button(String file_path){
        FILENAME = file_path;
    }

    public void start_parse(){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();
            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());

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
                    System.out.println("type : "  + eElement.getAttribute("type")); //element type such as : class || constraint || generalization
                    System.out.println("name : " + eElement.getAttribute("name")); //class name for example : vaškovo_fáro || kolesá
                    //TODO -> CALL FOO from CONTROLLER -> CREATE CLASS AND SET ITS NAME
                    for (int j = 0; j < fieldNodes.getLength(); j++){ //for each argument
                        Node fieldNode = fieldNodes.item(j);
                        NamedNodeMap attributes = fieldNode.getAttributes(); //converting nodes (arguments) into iterable from added dependency
                        Node arg = attributes.getNamedItem("type"); //getting type attributes from each argument
                        Node mandatory = attributes.getNamedItem("mandatory");
                        Node genclass = attributes.getNamedItem("class");
                        if (arg != null){
                            //TODO -> FILL CLASS WITH ITS ATTRIBUTES
                            if (arg.getTextContent().equals("primarykey")) {
                                System.out.println("PK>   " + fieldNode.getTextContent());
                            }
                            if (arg.getTextContent().equals("attribute")) {
                                System.out.println("ATTR> " + fieldNode.getTextContent());
                            }
                            if (arg.getTextContent().equals("function")) {
                                System.out.println("FUNC> " + fieldNode.getTextContent());
                            }
                        }
                        if (mandatory != null && genclass != null){
                            String result_cardinality = "";
                            System.out.println("ATTC> " + genclass.getNodeValue());     //Attached To Class - class name
                            //System.out.println("MAND> " + mandatory.getNodeValue());    //mandatory on physical class side (null - 0, notnull - 1)
                            //System.out.println("CARD> " + fieldNode.getTextContent());  //cardinality on physical class side (1 or N:M)
                            //concat mandatory
                            if(mandatory.getNodeValue() == "null"){
                                result_cardinality += "0..";
                            }
                            else if(mandatory.getNodeValue() != "null"){
                                result_cardinality += "1..";
                            }else{
                                System.out.println("ERROR missing cardinality while parsing XML");
                            }
                            //TODO -> SET CARDINALITY (AS ATTRIBUTE) TO CLASS
                            result_cardinality += fieldNode.getTextContent();
                            System.out.println("CARD> " + result_cardinality);
                        }
                    }
                }
            }
        }catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
