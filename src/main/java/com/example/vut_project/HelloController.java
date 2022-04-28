/**
 * @Brief: Main functions for program
 * @File: HelloController
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project;

import com.example.vut_project.controller.AlertBox;
import com.example.vut_project.controller.ClassController;
import com.example.vut_project.controller.ClassDiagramController;
import com.example.vut_project.controller.DraggableMarker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Controller with main functions
 */
public class HelloController {

    public Group workSpaceGroup; //workspace group - contains all Class Diagrams displayed in application
    public List<Node> Class_Diagram_Element_Shape_List = new ArrayList<Node>(); //list of all nodes (aka. class diagram shapes) created in workspace by Add Element button
    public List<EntityController> Entity_Controller_list = new ArrayList<EntityController>();
    public Scene scene;
    public Pane pane;
    public Pane projectSpace;
    public StackPane Stackpane;
    public AnchorPane newrectangle;

    DraggableMarker draggableMaker = new DraggableMarker(); //class, that makes all objects movable
    ClassDiagramController classDiagramController = new ClassDiagramController("AllClasses");

    int i = 0;
    private Object identifier;
    private String identifier_name;

    @FXML
    //bunch of stages and scenes and panes xD
    private Stage stage;
    private Parent root;
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
    private int attributeFieldCounter = 0;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    /**
     * Function to display single class diagram loaded from XML file
     *
     * @param classDiagram Displaying classes from this ClassDiagram
     * @throws IOException
     */
    protected void displayLoadedClassDiagramEntity(ClassDiagramController classDiagram) throws IOException {
        for (ClassController class_name : classDiagram.return_list()) {
            EntityController new_entity = new EntityController(class_name, this);
            draggableMaker.makeDraggable(new_entity);
            new_entity.setLayoutX(class_name.getPosition_x());
            new_entity.setLayoutY(class_name.getPosition_y());
            Entity_Controller_list.add(new_entity);                             //save to list
            projectSpace.getChildren().add(new_entity);                         //add it to project space pane
        }
    }

    /**
     * Function for displaying new project
     *
     * @throws Exception
     */
    protected void displayNewProjectScene() throws Exception {
        stage = new Stage();
        projectSpace = FXMLLoader.load(getClass().getResource("new_project_view.fxml"));
        scene = new Scene(projectSpace);
        stage.setTitle("New Project");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Function for opening project from file
     *
     * @throws Exception
     */
    @FXML
    protected void onFileOpenButtonClick() throws Exception {
        ParseXML parse = new ParseXML();
        FileChooser fileChooser = new FileChooser();
        File result = fileChooser.showOpenDialog(null);  //select file to open
        if (result != null) {
            File filePath = new File(result.getAbsolutePath());
            String path = filePath.toString();
            String lowercaseName = path.toLowerCase();
            if (lowercaseName.endsWith(".xml")) {
                parse.input_file_from_button(path);
                classDiagramController = parse.start_parse();
                this.displayLoadedClassDiagramEntity(classDiagramController);
            } else {
                AlertBox alert = new AlertBox();
                alert.display("Error", "You need to choose XML file format");
                System.out.println("Need to be a xml file");
            }
        }
    }

    /**
     * Function for clicking on New Project button
     *
     * @throws Exception
     */
    @FXML
    protected void onNewProjectButtonClick() throws Exception { //stage in new window
        this.displayNewProjectScene();
    }

    /**
     * Function for start button
     *
     * @param event event for mouse pressed action
     * @throws IOException
     */
    @FXML
    public void onMiddleButtonClick(ActionEvent event) throws IOException { //stage in same window
        Parent root = null;
        projectSpace = new Pane();
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("new_project_view.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene = new Scene(projectSpace, 600, 400);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        assert root != null;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Function for creating new Class (button new Class)
     *
     * @param event mouse pressed action
     * @throws NullPointerException
     * @throws IOException
     */
    @FXML
    public void onNewElementClick(ActionEvent event) throws NullPointerException, IOException { //Creating new element (class diagram) after button click
        String new_name = "class " + i++;
        EntityController new_entity = new EntityController(new_name, classDiagramController, this);
        projectSpace.getChildren().add(new_entity);
        draggableMaker.makeDraggable(new_entity);
        Entity_Controller_list.add(new_entity);
    }

    /**
     * Event for clicking on Class
     *
     * @param mouseEvent mouse click event
     */
    @FXML
    public void onClassDiagramClick(MouseEvent mouseEvent) {
        System.out.println("EVENT CLASS DIAGRAM CLICK");
        Object source = mouseEvent.getSource();
        this.identifier = System.identityHashCode(source);
        System.out.println(classDiagramController.return_list().toString());
        System.out.println(this.identifier);
        System.out.println(classDiagramController.return_list().toString());
        AtomicInteger selectedListViewIndex = new AtomicInteger(-1);
        if (source instanceof GridPane) {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                ObservableList<String> attributes = FXCollections.observableArrayList(); //"Mamka", "Babka", "Dedko", "Vajcovod", "Tvoj Tatko", "Maroš", "Peder", "Ctibor", "Gábor", "Chvost", "Mrkva", "Dikobraz", "Bonsaj"
                ListView<String> listAttributeView = new ListView<String>(attributes);
                listAttributeView.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                listAttributeView.setOnMouseClicked(event -> {
                    String selectedItem = listAttributeView.getSelectionModel().getSelectedItem();
                    selectedListViewIndex.set(listAttributeView.getSelectionModel().getSelectedIndex());
                });

                System.out.println(selectedListViewIndex);
                ((GridPane) source).getChildren().addAll(listAttributeView);
                attributeFieldCounter = attributeFieldCounter + 1;
            }
        }
    }
    /**
     * @param event
     */
    public void onAddDiagramButtonClick(ActionEvent event) {
        // TODO
    }

    /**
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    public void onRemoveAttributeClick(ActionEvent actionEvent) throws Exception {
        // TODO
    }

    /**
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    public void onAddFunctionClick(ActionEvent actionEvent) throws Exception {
        // TODO
    }

    /**
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    public void onRemoveFunctionClick(ActionEvent actionEvent) throws Exception {
        // TODO
    }

    /**
     * @param event
     */
    @FXML
    public void onDeleteDiagramClick(ActionEvent event) {
        System.out.println("Before deleting: " + classDiagramController.return_list().toString());
        classDiagramController.deleteClass(classDiagramController.findClass(identifier_name));
        System.out.println("After deleting: " + classDiagramController.return_list().toString());
        projectSpace.getChildren().remove(identifier);
    }
    // sets the actual selected class diagram
    public void set_identifier(String class_name, Object object){
        this.identifier = object;
        this.identifier_name = class_name;
    }
}
