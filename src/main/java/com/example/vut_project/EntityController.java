/**
 * @Brief: Entity for easier class identification at scene
 * @File: EntityController
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project;

import com.example.vut_project.controller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Entity class represents every Class in ClassDiagram. It's used for storing data and Click events.
 */
public class EntityController extends VBox {

    private final ObservableList<String> observableListOfAttributes = FXCollections.observableArrayList();
    public VBox sequenceVBox;
    @FXML
    private TextField classNameTextField;
    @FXML
    private TextField sequenceDiagramNameTextField;
    @FXML
    private MenuItem deleteDiagramContextButton;
    @FXML
    private ListView entityAttributeView;
    @FXML
    private MenuItem addAttributeContextMenuButton;
    private MenuItem deleteAttributeContextMenuButton;
    @FXML
    private ContextMenu contextMenuOnElement;
    @FXML
    private VBox classVBox;
    private HelloController referece;
    private SequenceDiagramController sequenceControllerReference;
    private final ArrayList<LifeLine> LifeLineList;

    private String old_class_name;
    private String old_attribute_name;
    private int AttrClickedFXID;
    private Object identifier;
    private int index = 0;
    public int i = -1;

    /**
     * Constructor for new Entity
     *
     * @param new_name name of new entity
     * @param diagram  ClassDiagram
     * @throws IOException
     */
    public EntityController(String new_name, ClassDiagramController diagram, HelloController reference) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/vut_project/class_diagram_entity_template.fxml")); //new object (aba class diagram) is created as pane
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        this.LifeLineList = new ArrayList<>();
        ClassController new_class = diagram.createClass(new_name);
        classNameTextField.setText(new_class.getName());
        old_class_name = classNameTextField.getText();
        entityAttributeView.setEditable(true);
        entityAttributeView.setCellFactory(TextFieldListCell.forListView());
        this.referece = reference;
    }

    /**
     * Constructor for loading from file
     *
     * @param class_name name of new class to be created from file
     * @throws IOException
     */
    public EntityController(ClassController class_name, HelloController reference) throws IOException { //diagram added loaded file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("class_diagram_entity_template.fxml")); //new object (aba class diagram) is created as pane
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        this.LifeLineList = new ArrayList<>();
        classNameTextField.setText(class_name.getName());
        old_class_name = classNameTextField.getText();
        for (AttributeController attribute : class_name.getAttributes()) {
            observableListOfAttributes.add(attribute.getName());
        }
        this.entityAttributeView.setItems(observableListOfAttributes);
        entityAttributeView.setEditable(true);
        entityAttributeView.setCellFactory(TextFieldListCell.forListView());
        this.referece = reference;
    }

    public EntityController(SequenceDiagramController sequenceReference, int i) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/vut_project/sequence_diagram_entity_template.fxml")); //new object (aba class diagram) is created as pane
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        this.LifeLineList = new ArrayList<>();
        this.sequenceControllerReference = sequenceReference;
        this.i = i;
    }

    /**
     * Debug event that prints ClassName when clicked on Class
     *
     * @param mouseEvent
     */
    public void onClassDiagramClick(MouseEvent mouseEvent) {
        System.out.println("ON CLASS DIAGRAM CLICK");
        System.out.println(this.classNameTextField.getText());
        this.identifier = mouseEvent.getSource();
        System.out.println(this.identifier);
        String selectedClassName = classNameTextField.getText();
        referece.set_identifier(selectedClassName, this.identifier);
    }

    /**
     * Event for adding attributes
     *
     * @param event
     */
    public AttributeController onAddAttributeClick(ActionEvent event) {
        String[] attributeToAdd = AddAttributePopUp.AddAttrubutePopUpDisplay("Attribute", "Choose Attribute Properties", "Add");
        if(attributeToAdd == null) return null;
        AttributeController attr = new AttributeController((String) attributeToAdd[1],(String) attributeToAdd[3],(String) attributeToAdd[2],(String) attributeToAdd[0]);
        if (referece.AddAttribute(this.classNameTextField.getText(), attr)){ //TODO
            if(event != null) this.entityAttributeView.getItems().add(attr.getWholeAttributeString());
                index++;
                return attr;

        }
        return null;
    }

    public void onDeleteAttributeClick(ActionEvent event) {
        int clickedFXID = entityAttributeView.getSelectionModel().getSelectedIndex();  // get cell index
        if (clickedFXID != -1) {
            String clicked = (String) entityAttributeView.getItems().get(clickedFXID);
            System.out.println(clicked); // gets text from deleted cell
            entityAttributeView.getItems().remove(clickedFXID); // remove cell from list viewW
            referece.DeleteAttribute(this.classNameTextField.getText(), clicked);
        }
    }

    @FXML
    public void onSingleAttributeClick(MouseEvent event) {  //TODO
        int ClickedAttributeIndex = entityAttributeView.getSelectionModel().getSelectedIndex();
        if (ClickedAttributeIndex == -1) {
            return;
        }
        System.out.println("Clicked on " + entityAttributeView.getItems().get(ClickedAttributeIndex));
        old_attribute_name = (String) entityAttributeView.getItems().get(ClickedAttributeIndex);
        String parsedAttributeName = old_attribute_name.substring(2, old_attribute_name.lastIndexOf(":"));
        System.out.println(parsedAttributeName);
        this.renameAttributeInEntityController(parsedAttributeName, ClickedAttributeIndex);
    }

    public void renameAttributeInEntityController(String old_attribute_name, int index){
            AttributeController new_attr = onAddAttributeClick(null);
            System.out.println("CHANGING");
            System.out.println("OLD NAME: " + old_attribute_name);
            System.out.println("Attribute changed, new name: " + new_attr.getName());
            referece.DeleteAttribute(classNameTextField.getText(), old_attribute_name);
            referece.AddAttribute(classNameTextField.getText(), new_attr);
            entityAttributeView.getItems().set(index, new_attr.getWholeAttributeString());
        }


    @FXML
    public void onEnterClick(KeyEvent event) {  // attribute click
        String new_attribute_name;
        if (event.getCode() != KeyCode.ENTER) {
            AttrClickedFXID = entityAttributeView.getSelectionModel().getSelectedIndex();
            if (AttrClickedFXID == -1) {
                return;
            }
        }
        if (event.getCode() == KeyCode.ENTER) {
            if (AttrClickedFXID == -1) {
                return;
            }
            new_attribute_name = (String) entityAttributeView.getItems().get(AttrClickedFXID);
            System.out.println("CHANGING");
            System.out.println("OLD NAME: " + old_attribute_name);
            System.out.println("Attribute changed, new name: " + new_attribute_name);
            referece.DeleteAttribute(classNameTextField.getText(), old_attribute_name);
            //referece.AddAttribute(classNameTextField.getText(), new_attribute_name);
            onAddAttributeClick(null);
        }
    }

    @FXML
    public void onClassDiagramNameEnter(ActionEvent event) {
        System.out.println(this.old_class_name);
        if (referece.RenameClass(this.old_class_name, this.classNameTextField.getText())) {
            this.old_class_name = classNameTextField.getText();
        } else {
            this.classNameTextField.setText(this.old_class_name);
            AlertBox.display("Note", "Class Diagram With Same Name Already Exists", "Understood");
        }
    }

    @FXML
    public void onClassDiagramNameClick(Event event) {
        this.old_class_name = classNameTextField.getText();
    }

    @FXML
    public void onAddConstraintClick(Event event) {
        referece.SetConstraintFrom(classNameTextField.getText());
    }

    @FXML
    public void onPasteConstraintClick(Event event) {
        referece.SetConstraintTo(classNameTextField.getText());
    }

    /* *********************************     SEQUENCE DIAGRAM PART      ***************************************** */

    @FXML
    public void onDeleteDiagramClick(ActionEvent event) {
        System.out.println("Delete Diagram Context Menu Click");
        this.identifier = classVBox;
        referece.set_identifier(classNameTextField.getText(), this.identifier);
        referece.DeleteDiagram(event);
    }

    @FXML
    public void onSequenceClicked(MouseEvent event) {
        System.out.println("CLICKED ON SEQUENCE DIAGRAM");
    }

    @FXML
    public void onSequenceDiagramNameClick(MouseEvent event) {
        System.out.println("Sequence Diagram name Click");
        // TODO get old sequence diagram name
    }

    @FXML
    public void onSequenceDiagramNameEntered(Event event) {
        System.out.println("Sequence diagram name changed");
        //TODO get new sequence diagram name
    }

    @FXML
    public void onDeleteSequenceDiagramContextMenuClick(Event event) {
        System.out.println("On delete sequence diagram context menu button click");
        System.out.println(sequenceControllerReference);
        sequenceControllerReference.sequenceSpace.getChildren().remove(this);
        sequenceControllerReference.EntityList.remove(this);
        sequenceControllerReference.deleteAllLifeLines(LifeLineList);
        this.deleteAllLifeLines();
    }

    public String getSequenceNameTextField() {
        return this.sequenceDiagramNameTextField.getText();
    }

    public void setSequenceNameTextField(String name) {
        System.out.println(name);
        this.sequenceDiagramNameTextField.setText(name);
    }

    public void addLifeLine(LifeLine life_line) {
        LifeLineList.add(life_line);
    }

    public boolean deleteLifeLine(LifeLine life_line) {
        System.out.println("lifelines before: " + LifeLineList);
        if (LifeLineList.contains(life_line)) {
            LifeLineList.remove(life_line);
            System.out.println("lifelines after: " + LifeLineList);
            return true;
        } else {
            return false;
        }
    }

    public void deleteAllLifeLines() {
        while (LifeLineList.size() != 0) {
            this.deleteLifeLine(LifeLineList.get(0));
        }
    }

    public ArrayList<LifeLine> getLifeLineList(){
        return this.LifeLineList;
    }

    public void onAddLifeLineContextMenuClick(ActionEvent event){
        System.out.println("On add life line click");
        sequenceControllerReference.createLifeLineBindToEntity(event, sequenceControllerReference.findEntity(sequenceDiagramNameTextField.getText()));
    }
}
