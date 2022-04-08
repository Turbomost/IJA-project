package com.example.vut_project;

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
    public List <Class_Diagram_Element_Shape> Class_Diagram_Element_Shape_List = new ArrayList<>(); //list of all class diagram shapes created in workspace by Add Element button
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
    @FXML
    protected void onFileOpenButtonClick(){
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
                }else{
                    AlertBox alert = new AlertBox();
                    alert.display("Error", "You need to choose XML file format");
                    System.out.println("Need to be a xml file");
                }
            }
    }
        @FXML
        protected void onNewProjectButtonClick() throws Exception{ //stage in new window
            stage = new Stage();
            pane = FXMLLoader.load(getClass().getResource("new_project_view.fxml"));
            scene = new Scene(pane);
            stage.setTitle("New Project");
            stage.setScene(scene);
            stage.show();
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

        public void onNewElementClick(ActionEvent event) throws NullPointerException{ //Creating new element (class diagram) after button click
            System.out.println("CLICK");
            Class_Diagram_Element_Shape r = new Class_Diagram_Element_Shape(50, 50 ,30,30); //create CD element
            Class_Diagram_Element_Shape_List.add(r); //add new class diagram element to list
            draggableMaker.makeDraggable(r);        //give element power to move
            workSpaceGroup.getChildren().add(r);    //display it on scene
        }

        public class Class_Diagram_Element_Shape extends Rectangle{ //crating own shape to create in diagram
            public Class_Diagram_Element_Shape(int x, int y, int w, int h){
                this.setLayoutX(x);
                this.setLayoutY(y);
                this.setWidth(w);
                this.setHeight(h);
            }
        }
}