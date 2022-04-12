package com.example.vut_project;

import com.example.vut_project.controller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class HelloController {
    public Group workSpaceGroup; //workspace group - contains all Class Diagrams displayed in application
    public List<Node> Class_Diagram_Element_Shape_List = new ArrayList<Node>(); //list of all nodes (aka. class diagram shapes) created in workspace by Add Element button
    public Scene scene;
    public Pane pane;
    public Pane projectSpace;
    public StackPane Stackpane;
    public AnchorPane newrectangle;
    DraggableMarker draggableMaker = new DraggableMarker(); //class, that makes all objects movable
    ClassDiagramController classDiagramController = new ClassDiagramController("AllClasses");
    int i = 0;
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

    String classNameToDelete = "default";


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    //function to display single class diagram loaded from XML file
    protected void displayLoadedClassDiagramEntity(ClassDiagramController classDiagram) throws IOException {

        for (ClassController class_name : classDiagram.return_list()) {
            AtomicInteger selectedListViewIndex = new AtomicInteger(-1);
            Pane pane = FXMLLoader.load(getClass().getResource("class_diagram_entity_template.fxml")); //new object (aba class diagram) is created as pane
            ObservableList<Node> childrens = pane.getChildren();        //get all nodes of new pane (aka. Class Diagram entity)
            pane.setLayoutX(class_name.getPosition_x());
            pane.setLayoutY(class_name.getPosition_y());
            Node id;                                                        //Just a node id to add to nodes list
            for (Node node : pane.getChildren()) {                       //find TextField
                System.out.println(node);
                if (node instanceof TextField) {
                    ((TextField) node).setText(class_name.getName());      //and set Class Diagram Name TODO replace for class diagram name
                    node.setLayoutX(10);
                    node.setLayoutY(10);
                }
                if (node instanceof VBox) {
                    ObservableList<String> attributes = FXCollections.observableArrayList(); //"Mamka", "Babka", "Dedko", "Vajcovod", "Tvoj Tatko", "Maroš", "Peder", "Ctibor", "Gábor", "Chvost", "Mrkva", "Dikobraz", "Bonsaj"
                    for (AttributeController attribute_name : class_name.getAttributes()){
                        attributes.add(attribute_name.getName());
                    }

                    ListView<String> listAttributeView = new ListView<String>(attributes);
                    listAttributeView.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                    listAttributeView.setOnMouseClicked(event ->
                    {String selectedItem = listAttributeView.getSelectionModel().getSelectedItem().toString();
                        selectedListViewIndex.set(listAttributeView.getSelectionModel().getSelectedIndex());
                    });
                    for (Node deeperNode : ((VBox) node).getChildren()){
                        if (deeperNode instanceof GridPane){
                            ((GridPane) deeperNode).getChildren().addAll(listAttributeView);
                        }
                    }
                }
            }
            projectSpace.getChildren().add(pane);                               //add it to project space pane
            ObservableList<Node> allChildren = projectSpace.getChildren();   //get all nodes from scene (node is every Class Diagram)
            int lastAddedElement = allChildren.size() - 1;                   //take length of nodes array got at line before
            id = projectSpace.getChildren().get(lastAddedElement);
            draggableMaker.makeDraggable(id);                               //make it draggable
            Class_Diagram_Element_Shape_List.add(id);                       //also add it to own list of nodes (aka. Class Diagrams
        }
    }

    protected void displayNewProjectScene() throws Exception {
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
        if (result != null) {
            File filePath = new File(result.getAbsolutePath());
            String path = filePath.toString();
            String lowercaseName = path.toLowerCase();
            if (lowercaseName.endsWith(".xml")) {
                parse.input_file_from_button(path);
                classDiagramController = parse.start_parse();
                //TODO GET CLASS NAME AND SET IT TO CLASS
                this.displayLoadedClassDiagramEntity(classDiagramController);
            } else {
                AlertBox alert = new AlertBox();
                alert.display("Error", "You need to choose XML file format");
                System.out.println("Need to be a xml file");
            }
        }
    }

    @FXML
    protected void onNewProjectButtonClick() throws Exception { //stage in new window
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
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        assert root != null;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void onNewElementClick(ActionEvent event) throws NullPointerException, IOException { //Creating new element (class diagram) after button click
        Pane pane = FXMLLoader.load(getClass().getResource("class_diagram_entity_template.fxml")); //new object (aba class diagram) is created as pane
        ObservableList<Node> childrens = pane.getChildren();
        ClassController new_class = classDiagramController.createClass("class " + i++);
        Node id;
        projectSpace.getChildren().add(pane);                               //add it to project space pane
        ObservableList<Node> allChildren = projectSpace.getChildren();  //get all nodes from scene (node is every Class Diagram)
        int lastAddedElement = allChildren.size() - 1;                   //take length of nodes array got at line before
        id = projectSpace.getChildren().get(lastAddedElement);

        for (Node node : pane.getChildren()) {                       //find TextField
            if (node instanceof TextField) {
                ((TextField) node).setText(new_class.getName());      //and set Class Diagram Name TODO replace for class diagram name
                //((TextField) node).setId(new_class.toString());
                node.setLayoutX(10);
                node.setLayoutY(10);
            }
        }

        //classDiagramController.deleteClass(new_class);

        draggableMaker.makeDraggable(id);                               //make it draggable
        Class_Diagram_Element_Shape_List.add(id);                       //also add it to own list of nodes (aka. Class Diagrams


        System.out.println("End of creating: " + classDiagramController.return_list().toString());
    }

    @FXML
    public void onClassDiagramClick(MouseEvent mouseEvent) {
        System.out.println("EVENT CLASS DIAGRAM CLICK");
        Object source = mouseEvent.getSource();
        AtomicInteger selectedListViewIndex = new AtomicInteger(-1);
        //Pane clicked = (Pane) mouseEvent.getSource();
        if (source instanceof GridPane) {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                ObservableList<String> attributes = FXCollections.observableArrayList(); //"Mamka", "Babka", "Dedko", "Vajcovod", "Tvoj Tatko", "Maroš", "Peder", "Ctibor", "Gábor", "Chvost", "Mrkva", "Dikobraz", "Bonsaj"
                ListView<String> listAttributeView = new ListView<String>(attributes);
                listAttributeView.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                listAttributeView.setOnMouseClicked(event ->
                    {String selectedItem = listAttributeView.getSelectionModel().getSelectedItem().toString();
                    selectedListViewIndex.set(listAttributeView.getSelectionModel().getSelectedIndex());
                });

                System.out.println(selectedListViewIndex);
                 //   nameTxt.setText(selectedItem);
                ((GridPane) source).getChildren().addAll(listAttributeView);
                attributeFieldCounter = attributeFieldCounter + 1;
            }
        }
        if (source instanceof Rectangle){
            System.out.println("RECTANGLE");
            Object o = mouseEvent.getSource();
            int identifier = java.lang.System.identityHashCode(o);
            System.out.println(identifier);
            System.out.println(classDiagramController.return_list().toString());
        }
}

    @FXML
    private void onClassDiagramMenuItemDeleteClick(Object source) {
        ((GridPane) source).getChildren().clear();
        attributeFieldCounter = 0;
    }

    public void onAddDiagramButtonClick(ActionEvent event) {

    }

    @FXML
    public void onAddAttributeClick(ActionEvent actionEvent) throws Exception {
        System.out.println(classDiagramController.return_list().toString());
    }

    @FXML
    public void onRemoveAttributeClick(ActionEvent actionEvent) throws Exception {
        System.out.println(classNameToDelete);
    }

    @FXML
    public void onAddFunctionClick(ActionEvent actionEvent) throws Exception {
    }

    @FXML
    public void onRemoveFunctionClick(ActionEvent actionEvent) throws Exception {
    }

    @FXML
    public void onDeleteDiagramClick(ActionEvent event) {
        System.out.println("start delete: " + classDiagramController.return_list().toString());
        setClassNameToDelete(classNameTextField.getText());
        System.out.println(classNameToDelete);
        /*ClassifierController classToDelete = classDiagramController.findClassifier(classNameToDelete);
        classDiagramController.deleteClass(classToDelete);
        System.out.println(classDiagramController.return_list().toString());*/
        //TODO delete class based on name
    }

    public void setClassNameToDelete(String name){
        this.classNameToDelete = name;
    }
}
