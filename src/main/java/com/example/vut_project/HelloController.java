/**
 * @Brief: Main functions for program
 * @File: HelloController
 * @Author(s): A. Ľupták, V. Valenta
 */

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

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
    public Scene sequenceScene;
    public Pane pane;
    public Pane projectSpace;
    public Pane sequenceSpace;
    public StackPane Stackpane;
    public AnchorPane newrectangle;
    public ArrayList<SequenceDiagramController> sequenceDiagramControllerList = new ArrayList<SequenceDiagramController>();
    public Center startCenter;
    public Center endCenter;
    public Button quitButton;
    public ClassDiagramController classDiagramController = new ClassDiagramController("AllClasses");
    DraggableMarker draggableMaker = new DraggableMarker(); //class, that makes all objects movable
    int i = 0;
    int position = 0;
    private Object identifier;
    private String identifier_name;
    private ClassController constraint_from;
    private ClassController constraint_to;
    @FXML
    //bunch of stages and scenes and panes xD
    private Stage stage;
    private Stage sequenceStage;
    private Parent root;
    //buttons
    @FXML
    private Label FileOpenButton;
    private Label NewProjectButton;
    @FXML
    private Label AddDiagramButton;
    @FXML
    private Label welcomeText;
    private VBox sequenceDiagramEditor;
    @FXML
    private TextField classNameTextField;
    private int attributeFieldCounter = 0;
    private Window nodeForStage;

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
            new_entity.makeResourceForFunctionLoad();
            draggableMaker.makeDraggable(new_entity, class_name);
            new_entity.setLayoutX(class_name.getPosition_x());
            new_entity.setLayoutY(class_name.getPosition_y() + 60);
            Entity_Controller_list.add(new_entity);                             //save to list
            projectSpace.getChildren().add(new_entity);                         //add it to project space pane
        }
        this.classDiagramController.setOverrides(this);
        this.refreshSequenceDiagram();
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
                projectSpace.getChildren().clear();
                this.classDiagramController = parse.start_parse(this);
                parse.load_constraints(this);
                parse.load_operations(this);
                this.displayLoadedClassDiagramEntity(this.classDiagramController);
            } else {
                AlertBox.display("Error", "You need to choose XML file format", "I will choose an XML file next time");
                System.out.println("Need to be a xml file");
            }
        }
    }

    @FXML
    protected void onSequenceDiagramClick(ActionEvent event) throws IOException { // space for sequence diagram
        System.out.println("Sequence diagram");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sequence_editor.fxml"));
        sequenceSpace = loader.load();
        sequenceScene = new Scene(sequenceSpace);
        sequenceStage = new Stage();
        sequenceStage.setTitle("Sequence Diagram");
        sequenceStage.setScene(sequenceScene);
        sequenceStage.show();
        this.sequenceDiagramControllerList.add(loader.getController());
        this.sequenceDiagramControllerList.get(sequenceDiagramControllerList.size() - 1).parseHelloControllerAsReference(this);

    }

    /**
     * Function for start button
     *
     * @param event event for mouse pressed action
     * @throws IOException
     */
    @FXML
    public void onMiddleButtonClick(ActionEvent event) throws IOException { //stage in same window
        root = null;
        projectSpace = new Pane();
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("new_project_view.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene = new Scene(projectSpace, 600, 400);
        nodeForStage = ((Node) event.getSource()).getScene().getWindow();
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
        while(this.classDiagramController.findClass("class " + i) != null){
            i++;
        }
        String new_name = "class " + i;
        EntityController new_entity = new EntityController(new_name, this.classDiagramController, this);
        projectSpace.getChildren().add(new_entity);
        draggableMaker.makeDraggable(new_entity, this.classDiagramController.findClass(new_name));
        position = (i++) % 20;
        new_entity.setLayoutX(position * 20);
        new_entity.setLayoutY(position * 20);
        Entity_Controller_list.add(new_entity);
        new_entity.checkForLifeLinesInSequence();
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
        System.out.println(this.classDiagramController.getClassList().toString());
        System.out.println(this.identifier);
        System.out.println(this.classDiagramController.getClassList().toString());
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

    public void DeleteDiagram(ActionEvent event, EntityController entityControllerReference) {
        ClassController deleting_class = this.classDiagramController.findClass(identifier_name);
        System.out.println("Before deleting: " + this.classDiagramController.getClassList().toString());
        int delete = this.classDiagramController.deleteClass(deleting_class, this.sequenceDiagramControllerList);
        System.out.println("After deleting: " + this.classDiagramController.getClassList().toString());

        if (delete > -1) {
            projectSpace.getChildren().remove(identifier);
            if (this.constraint_from == deleting_class) {
                this.constraint_from = null;
            }
        }

        if (delete == 0) {
            entityControllerReference.checkForLifeLinesInSequence();
        }

        this.classDiagramController.setOverrides(this);
    }

    // sets the actual selected class diagram
    public void set_identifier(String class_name, Object object) {
        this.identifier = object;
        this.identifier_name = class_name;
    }

    public void DeleteAttribute(String class_name, String attribute_name) {
        ClassController curClass = this.classDiagramController.findClass(class_name);
        AttributeController attribute_deleting;
        if (curClass != null) {
            attribute_deleting = curClass.findAttributeByName(attribute_name);
            if (attribute_deleting != null) {
                System.out.println("Before: " + curClass.getAttributesList().toString());
                curClass.removeAttribute(curClass.findAttributeByName(attribute_name));
                System.out.println("After: " + curClass.getAttributesList().toString());
            } else {
                AlertBox.display("Error", "Error while deleting attribute '" + attribute_name + "'", "OK");
            }
        } else {
            AlertBox.display("Error", "Error while deleting in class'" + class_name + "'", "OK");
        }
        this.classDiagramController.setOverrides(this);
    }

    public void renameFunctionBasic(String class_name, String function_name) {
        ClassController curClass = this.classDiagramController.findClass(class_name);
        AttributeController function_to_change;
        if (curClass != null) {
            function_to_change = curClass.findAttributeByName(function_name);
            if (function_to_change != null) {
                System.out.println("Before: " + curClass.getAttributesList().toString());
                function_to_change.getType();
                function_to_change.getAccessType();
                function_to_change.getDatatype();
                curClass.removeAttribute(curClass.findAttributeByName(function_name));
                System.out.println("After: " + curClass.getAttributesList().toString());
            }
        }
        this.classDiagramController.setOverrides(this);
    }


    public boolean AddAttribute(String class_name, AttributeController attr) {
        ClassController curClass = this.classDiagramController.findClass(class_name);
        System.out.println("before adding");
        boolean returnValue = curClass.addAttribute(attr);
        this.classDiagramController.setOverrides(this);
        return returnValue;
    }

    public AttributeController getAttributeControllerByName(String class_name, String attribute_name) {
        ClassController curClass = this.classDiagramController.findClass(class_name);
        return curClass.findAttributeByName(attribute_name);
    }

    public boolean RenameClass(String old_name, String new_name) {
        ClassController curClass = this.classDiagramController.findClass(old_name);
        for (String old_class : this.classDiagramController.getClassList()) {
            if (old_class.equals(new_name)) {
                return false;
            }
        }
        if (curClass == null) {
            return false;
        }
        System.out.println("Before: Classes" + this.classDiagramController.getClassList().toString());
        curClass.rename(new_name);
        for (SequenceDiagramController sequenceDiagramController : sequenceDiagramControllerList) {
            if (sequenceDiagramController != null) {
                EntityController entity = sequenceDiagramController.findEntity(old_name);
                if (entity != null) {
                    entity.setSequenceNameTextField(new_name);
                }

            }
        }
        System.out.println("After: Classes" + this.classDiagramController.getClassList().toString());
        identifier_name = new_name;
        this.classDiagramController.setOverrides(this);

        EntityController new_entity = findEntityByName(curClass.getName());
        new_entity.checkForLifeLinesInSequence();
        new_entity.checkForOperationsInSequence();

        return true;
    }

    public void SetConstraintFrom(String constraint_from) {
        System.out.println("CLASS TO FIND " + constraint_from);
        this.constraint_from = this.classDiagramController.findClass(constraint_from);
        System.out.println(this.constraint_from);
        System.out.println("From: " + this.constraint_from.getName());
    }

    public void SetConstraintTo(String constraint_to, String type, String name, String left, String right) {
        if (this.constraint_from != null) {
            this.constraint_to = classDiagramController.findClass(constraint_to);
            if (this.constraint_to == this.constraint_from) {
                return;
            }
            for (BoundLine line : this.constraint_from.getConstraintList()) {
                if (this.constraint_to.equals(line.getConstraintFrom()) && this.constraint_from.equals(line.getConstraintTo()) || this.constraint_from.equals(line.getConstraintFrom()) && this.constraint_to.equals(line.getConstraintTo())) {
                    return;
                }
            }
//            System.out.println("To: " + this.constraint_to.getName() + " From: " + this.constraint_from.getName());
            Label label = new Label();
            BoundLine boundLine = new BoundLine(this.constraint_from.getPosition_x(), this.constraint_from.getPosition_y(), this.constraint_to.getPosition_x(), this.constraint_to.getPosition_y(), this.constraint_from, this.constraint_to, this, type, label, name, left, right);
            boundLine.create_line();
            //this.constraint_from.addConstraint(boundLine);
            //this.constraint_to.addConstraint(boundLine);
            boundLine.setSelfReference(boundLine);
            boundLine.setStrokeWidth(3.0);
            boundLine.toBack();
            boundLine.setViewOrder(1.0);
            boundLine.create_label();
            //label.setText(boundLine.getLineString());
            //label.setAlignment(Pos.CENTER);
            //label.layoutXProperty().bind(boundLine.endXProperty().subtract(boundLine.endXProperty().subtract(boundLine.startXProperty()).divide(2)).subtract(50));
            //label.layoutYProperty().bind(boundLine.endYProperty().subtract(boundLine.endYProperty().subtract(boundLine.startYProperty()).divide(2)).subtract(20));
            projectSpace.getChildren().addAll(boundLine, label, boundLine.arrow1, boundLine.arrow2, boundLine.arrow3, boundLine.arrow4);
        }
        this.classDiagramController.setOverrides(this);
    }

    public void DeleteConstraint(BoundLine toDelete, ClassController from, ClassController to) {
        ClassController remember_from = this.constraint_from;
        ClassController remember_to = this.constraint_to;
        this.constraint_from = from;
        this.constraint_to = to;
        System.out.println("DELETING IN CLASS CONTROLLER");
        System.out.println(this.constraint_from.getName() + " " + this.constraint_from.getConstraintListString());
        System.out.println(this.constraint_to.getName() + " " + this.constraint_to.getConstraintListString());
        this.constraint_from.removeConstraint(toDelete);
        this.constraint_to.removeConstraint(toDelete);
        System.out.println("AFTER DELETING IN CLASS CONTROLLER");
        System.out.println(this.constraint_from.getName() + " " + this.constraint_from.getConstraintListString());
        System.out.println(this.constraint_to.getName() + " " + this.constraint_to.getConstraintListString());
        projectSpace.getChildren().remove(toDelete.getPLabel());
        projectSpace.getChildren().remove(toDelete.arrow1);
        projectSpace.getChildren().remove(toDelete.arrow2);
        projectSpace.getChildren().remove(toDelete.arrow3);
        projectSpace.getChildren().remove(toDelete.arrow4);
        projectSpace.getChildren().remove(toDelete);
        this.constraint_from = remember_from;
        this.constraint_to = remember_to;
        this.classDiagramController.setOverrides(this);
    }

    public void onQuitButtonClick(ActionEvent event) {
        System.out.println("QUIT");
        System.exit(0);
    }

    public void onSaveClick(ActionEvent event) {
        System.out.println("Saving file");
        ParseXML parseXML = new ParseXML();
        parseXML.saveClassDiagramInFile(this);
    }

    public EntityController findEntityByName(String name) {
        for (EntityController entity : this.Entity_Controller_list) {
            if (entity.getNameTextField().equals(name)) {
                return entity;
            }
        }
        return null;
    }

    public void refreshSequenceDiagram(){
        try {
            for(SequenceDiagramController sequenceDiagramController : sequenceDiagramControllerList){
                for (EntityController entityController : sequenceDiagramController.getEntityList()){
                    for (LifeLine lifeLine : entityController.getLifeLineList()){
                        lifeLine.checkForClassAvailability(sequenceDiagramController);
                        for (MessageLine messageLine : lifeLine.getMessageLineList()){
                            messageLine.checkForOperationAvailability();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("!!! SOMETHING AT SEQUENCE REFRESH WENT WRONG !!!");
        }
    }

}
