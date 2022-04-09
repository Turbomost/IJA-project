package com.example.vut_project;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
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
    public StackPane Stackpane;

    //buttons
    @FXML
    private Label FileOpenButton;
    private Label NewProjectButton;
    @FXML
    private Label AddDiagramButton;
    @FXML
    private Label welcomeText;
    @FXML
    private Rectangle rectangle;

    DraggableMarker draggableMaker = new DraggableMarker(); //class, that makes all objects movable

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    protected void displayLoadedProjectFromXMLFile() throws Exception{
        this.displayNewProjectScene();
        //TODO display all Class Diagrams
    }

    protected void displayNewProjectScene() throws Exception{
        stage = new Stage();
        pane = FXMLLoader.load(getClass().getResource("new_project_view.fxml"));
        scene = new Scene(pane);
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
                    this.displayLoadedProjectFromXMLFile();
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
            Stackpane = new StackPane();
            try {
                root = FXMLLoader.load(getClass().getResource("new_project_view.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            scene = new Scene(Stackpane, 600, 400);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            assert root != null;
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        @FXML
        public void onAddDiagramButtonClick(ActionEvent event) { //redundant function, can be erased, but erase also rectangle
            draggableMaker.makeDraggable(rectangle);
        }

        public void onNewElementClick(ActionEvent event) throws NullPointerException, IOException { //Creating new element (class diagram) after button click
            workSpaceGroup.getChildren().add(FXMLLoader.load(getClass().getResource("class_diagram_entity_template.fxml"))); //load Class diagram shape from fxml file and add it to scene
            ObservableList<Node> allChildren = workSpaceGroup.getChildren(); //get all nodes from scene (node is every Class Diagram)
            int lastAddedElement = allChildren.size() - 1;                   //take length of nodes array got at line before
            Node id = workSpaceGroup.getChildren().get(lastAddedElement);   //take id of new added node (aka. element aka. Class Diagram)
            draggableMaker.makeDraggable(id);                               //make it draggable
            Class_Diagram_Element_Shape_List.add(id);                       //also add it to own list of nodes (aka. Class Diagrams)
        }

}