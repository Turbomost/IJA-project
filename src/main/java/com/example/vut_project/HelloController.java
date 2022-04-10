
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
        Pane pane = FXMLLoader.load(getClass().getResource("class_diagram_entity_template.fxml"));
        projectSpace.getChildren().add(pane);
        draggableMaker.makeDraggable(pane);
    }

    public void onClassDiagramClick(MouseEvent mouseEvent) {
        
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
