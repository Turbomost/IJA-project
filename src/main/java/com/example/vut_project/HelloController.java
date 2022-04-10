
package com.example.vut_project;

import com.example.vut_project.controller.AlertBox;
import com.example.vut_project.controller.DraggableMarker;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.List;

public class HelloController{
    public Group workSpaceGroup; //workspace group - contains all Class Diagrams displayed in application
    public List<Node> Class_Diagram_Element_Shape_List = new ArrayList<Node>(); //list of all nodes (aka. class diagram shapes) created in workspace by Add Element button
    @FXML
    //bunch of stages and scenes and panes xD
    private Stage stage;
    private Parent root;
    public Scene scene;
    public Pane pane;
    public Pane projectSpace;
    public StackPane Stackpane;
    public AnchorPane newrectangle;
    //buttons
    @FXML
    private Label FileOpenButton;
    private Label NewProjectButton;
    @FXML
    private Label AddDiagramButton;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField classNameTextField;

    DraggableMarker draggableMaker = new DraggableMarker(); //class, that makes all objects movable

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    //function to display single class diagram loaded from XML file
    protected void displayLoadedClassDiagramEntity(String classDiagramName) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("class_diagram_entity_template.fxml")); //new object (aba class diagram) is created as pane
        ObservableList<Node> childrens = pane.getChildren();        //get all nodes of new pane (aka. Class Diagram entity)
        Node id;                                                    //Just a node id to add to nodes list
        for (Node node : pane.getChildren()){                       //find TextField
            if(node instanceof TextField){
                ((TextField)node).setText("TvojTatkoRecords");      //and set Class Diagram Name TODO replace for class diagram name
                node.setLayoutX(10);
                node.setLayoutY(10);
            }
        }
        projectSpace.getChildren().add(pane);                               //add it to project space pane
        ObservableList<Node> allChildren = projectSpace.getChildren();  //get all nodes from scene (node is every Class Diagram)
        int lastAddedElement = allChildren.size() - 1;                   //take length of nodes array got at line before
        id = projectSpace.getChildren().get(lastAddedElement);
        draggableMaker.makeDraggable(id);                               //make it draggable
        Class_Diagram_Element_Shape_List.add(id);                       //also add it to own list of nodes (aka. Class Diagrams
    }

    protected void displayNewProjectScene() throws Exception{
        stage = new Stage();
        projectSpace = FXMLLoader.load(getClass().getResource("new_project_view.fxml"));
        scene = new Scene(projectSpace);
        stage.setTitle("New Project");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onFileOpenButtonClick() throws Exception {
        ParseXML parse = new ParseXML();
        FileChooser fileChooser = new FileChooser();
        File result = fileChooser.showOpenDialog(null);  //select file to open
        if (result != null){
            File filePath = new File(result.getAbsolutePath());
            String path = filePath.toString();
            String lowercaseName = path.toLowerCase();
            if (lowercaseName.endsWith(".xml")){
                parse.input_file_from_button(path);
                parse.start_parse();
                //TODO GET CLASS NAME AND SET IT TO CLASS
                this.displayLoadedClassDiagramEntity("CLASS NAME");
            }else{
                AlertBox alert = new AlertBox();
                alert.display("Error", "You need to choose XML file format");
                System.out.println("Need to be a xml file");
            }
        }
    }
    @FXML
    protected void onNewProjectButtonClick() throws Exception{ //stage in new window
        this.displayNewProjectScene();
    }
    @FXML
    public void onMiddleButtonClick(ActionEvent event) throws IOException { //stage in same window
        Parent root = null;
        //Stackpane = new StackPane();
        projectSpace = new Pane();
        try {
            root = FXMLLoader.load(getClass().getResource("new_project_view.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene = new Scene(projectSpace, 600, 400);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        assert root != null;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onNewElementClick(ActionEvent event) throws NullPointerException, IOException { //Creating new element (class diagram) after button click
        Pane pane = FXMLLoader.load(getClass().getResource("class_diagram_entity_template.fxml")); //new object (aba class diagram) is created as pane
        ObservableList<Node> childrens = pane.getChildren();
        Node id;
        projectSpace.getChildren().addAll(pane);                               //add it to project space pane
        ObservableList<Node> allChildren = projectSpace.getChildren();  //get all nodes from scene (node is every Class Diagram)
        int lastAddedElement = allChildren.size() - 1;                   //take length of nodes array got at line before
        id = projectSpace.getChildren().get(lastAddedElement);
        draggableMaker.makeDraggable(id);                               //make it draggable
        Class_Diagram_Element_Shape_List.add(id);                       //also add it to own list of nodes (aka. Class Diagrams
    }

    public void onClassDiagramClick(MouseEvent mouseEvent) {
        //VOID FOR NOW
    }

    public void onAddDiagramButtonClick(ActionEvent event) {

    }

    @FXML
    public void onAddAttributeClick(ActionEvent actionEvent) throws Exception{

    }

    @FXML
    public void onRemoveAttributeClick(ActionEvent actionEvent) throws Exception{
    }

    @FXML
    public void onAddFunctionClick(ActionEvent actionEvent) throws Exception{
    }

    @FXML
    public void onRemoveFunctionClick(ActionEvent actionEvent) throws Exception{
    }


}
