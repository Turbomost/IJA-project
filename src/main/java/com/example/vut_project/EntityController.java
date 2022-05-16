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
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Entity class represents every Class in ClassDiagram. It's used for storing data and Click events.
 */
public class EntityController extends VBox {

    private final ObservableList<String> observableListOfAttributes = FXCollections.observableArrayList();
    private final ArrayList<LifeLine> LifeLineList;
    private final ArrayList<MessageLine> MessageLineList;
    public VBox sequenceVBox;
    public int i = -1;
    @FXML
    public ListView entityAttributeView;
    public AddFunctionController addFunctionController;
    public int ClickedAttributeIndex;
    Pane functionPupUpSpace;
    Scene functionPopUpScene;
    Stage functionPopUpStage;
    @FXML
    private TextField classNameTextField;
    @FXML
    private TextField sequenceDiagramNameTextField;
    @FXML
    private MenuItem deleteDiagramContextButton;
    @FXML
    private MenuItem addAttributeContextMenuButton;
    private MenuItem deleteAttributeContextMenuButton;
    @FXML
    private ContextMenu contextMenuOnElement;
    @FXML
    private VBox classVBox;
    private HelloController reference;
    private SequenceDiagramController sequenceControllerReference;
    private String old_class_name;
    private String old_attribute_name;
    private int AttrClickedFXID;
    private Object identifier;
    private int index = 0;

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
        this.MessageLineList = new ArrayList<>();
        ClassController new_class = diagram.createClass(new_name);
        classNameTextField.setText(new_class.getName());
        old_class_name = classNameTextField.getText();
        entityAttributeView.setEditable(false);
        entityAttributeView.setCellFactory(TextFieldListCell.forListView());
        this.reference = reference;
        this.reference.classDiagramController.setOverrides(this.reference);
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
        this.MessageLineList = new ArrayList<>();
        classNameTextField.setText(class_name.getName());
        old_class_name = classNameTextField.getText();
        for (AttributeController attribute : class_name.getAttributes()) {
            entityAttributeView.getItems().add(attribute.getWholeAttributeString());
        }
        //this.entityAttributeView.setItems(observableListOfAttributes);
        entityAttributeView.setEditable(false);
        entityAttributeView.setCellFactory(TextFieldListCell.forListView());
        this.reference = reference;
        this.reference.classDiagramController.setOverrides(this.reference);
    }

    public EntityController(HelloController helloControllerReference, SequenceDiagramController sequenceReference, int i) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/vut_project/sequence_diagram_entity_template.fxml")); //new object (aba class diagram) is created as pane
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        this.LifeLineList = new ArrayList<>();
        this.MessageLineList = new ArrayList<>();
        this.reference = helloControllerReference;
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
        reference.set_identifier(selectedClassName, this.identifier);
    }

    /**
     * Event for adding attributes
     *
     * @param event
     */
    public AttributeController onAddAttributeClick(ActionEvent event) {
        String[] attributeToAdd = AddAttributePopUp.AddAttrubutePopUpDisplay("Attribute", "Choose Attribute Properties", "Add Attribute");
        if (attributeToAdd == null) {
            return null;
        }
        AttributeController attr = new AttributeController(attributeToAdd[1], attributeToAdd[3], attributeToAdd[2], attributeToAdd[0], i);
        if (event != null) {
            if (reference.AddAttribute(this.classNameTextField.getText(), attr)) {
                this.entityAttributeView.getItems().add(attr.getWholeAttributeString());
            } else {
                return null;
            }
        }
        this.ClickedAttributeIndex = index;
        index++;
        this.reference.classDiagramController.setOverrides(this.reference);
        return attr;
    }

    public AttributeController onEditAttributeClick(ActionEvent event, AttributeController found_attribute) {
        String[] attributeToAdd = AddAttributePopUp.EditAttrubutePopUpDisplay("Edit Attribute", "Choose Attribute Properties", "Edit Attribute", this, found_attribute);
        if (attributeToAdd == null) {
            return null;
        }
        AttributeController attr = new AttributeController(attributeToAdd[1], attributeToAdd[3], attributeToAdd[2], attributeToAdd[0], this.entityAttributeView.getSelectionModel().getSelectedIndex());
        if (event != null) {
            if (reference.AddAttribute(this.classNameTextField.getText(), attr)) {
                this.entityAttributeView.getItems().add(attr.getWholeAttributeString());
            } else {
                return null;
            }
        }
        this.checkForOperationsInSequence();
        this.reference.classDiagramController.setOverrides(this.reference);
        return attr;
    }

    public AttributeController onAddFunctionDiagramClick(ActionEvent event) throws IOException {

        String[] attributeToAdd = AddFunctionPopUp.AddFunctionPopUpDisplay("function", "Choose Function Properties", "Add Function");
        if (attributeToAdd == null) {
            return null;
        }
        AttributeController attr = new AttributeController(attributeToAdd[1], attributeToAdd[3], attributeToAdd[2], attributeToAdd[0], this.entityAttributeView.getSelectionModel().getSelectedIndex());
        if (event != null) {
            if (reference.AddAttribute(this.classNameTextField.getText(), attr)) {
                this.entityAttributeView.getItems().add(attr.getWholeAttributeString());
            } else {
                return null;
            }
            this.ClickedAttributeIndex = index;
            index++;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vut_project/add_method_pop_up.fxml")); //new object (aba class diagram) is created as pane
        functionPupUpSpace = loader.load();
        functionPopUpScene = new Scene(functionPupUpSpace);
        functionPopUpStage = new Stage();
        functionPopUpStage.setTitle("Add operation");
        functionPopUpStage.setScene(functionPopUpScene);

        this.addFunctionController = loader.getController();

        //AttributeController attr = new AttributeController();
        this.addFunctionController.parseEntityControllerAsReference(this, attr);

        //referece.AddAttribute(this.classNameTextField.getText(), attr);

        functionPopUpStage.show();
        this.checkForOperationsInSequence();
        return attr;
    }

    public void makeResourceForFunctionLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vut_project/add_method_pop_up.fxml"));
        functionPupUpSpace = loader.load();
        functionPopUpScene = new Scene(functionPupUpSpace);
        functionPopUpStage = new Stage();
        functionPopUpStage.setTitle("Edit operation");
        functionPopUpStage.setScene(functionPopUpScene);
        for (AttributeController attr : reference.classDiagramController.findClass(classNameTextField.getText()).getAttributes()) {
            this.addFunctionController = loader.getController();
            this.addFunctionController.parseEntityControllerAsReference(this, attr);
            this.addFunctionController.displayExistingMethodParameters();
        }
    }

    public AttributeController onEditFunctionDiagramClick(ActionEvent event, int index, String old_function_name) throws IOException {

        AttributeController attr = reference.getAttributeControllerByName(classNameTextField.getText(), old_function_name);
        AttributeController new_attr = AddFunctionPopUp.EditFunctionPopUpDisplay("Edit Function", "Choose Function Properties", "Edit Function", attr);
        if (new_attr == null) {
            return null;
        }
        //System.out.println(">>UPRAVENE " + new_attr.toString());
        if (event != null) {
            //if (reference.AddAttribute(this.classNameTextField.getText(), new_attr)) {
            this.entityAttributeView.getItems().set(index, new_attr.getWholeAttributeString());
            /*} else {
                return null;
            }*/
        }
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/vut_project/add_method_pop_up.fxml")); //new object (aba class diagram) is created as pane
        //Pane functionPupUpSpace = loader.load();
        //Scene functionPopUpScene = new Scene(functionPupUpSpace);
        //Stage functionPopUpStage = new Stage();
        functionPopUpStage.setTitle("Edit function");
        functionPopUpStage.setScene(functionPopUpScene);

        //this.addFunctionController = loader.getController();
        //loader.setController(this.addFunctionController);
        //AttributeController attr = new AttributeController();
        System.out.println("#SENT " + new_attr);
        System.out.println(attr.operationTypesNames());

        ClassController refClass = reference.classDiagramController.findClass(classNameTextField.getText());
        if (refClass.findAttributeByName(new_attr.getName()) == null) {
            attr.rename(new_attr.getName());
        }
        this.addFunctionController.parseEntityControllerAsReference(this, attr);
        this.addFunctionController.displayExistingMethodParameters();
        //referece.AddAttribute(this.classNameTextField.getText(), attr);


        functionPopUpStage.show();

        attr.rename(old_function_name);
        this.reference.classDiagramController.setOverrides(this.reference);
        if (this.sequenceControllerReference != null) {
            for (MessageLine messageLine : MessageLineList) {
                messageLine.checkForOperationAvailability();
            }
        }
        return new_attr;
    }

    public void onDeleteAttributeClick(ActionEvent event) {
        String parsedAttributeName;
        int clickedFXID = entityAttributeView.getSelectionModel().getSelectedIndex();  // get cell index
        if (clickedFXID != -1) {
            String clicked = (String) entityAttributeView.getItems().get(clickedFXID);
            System.out.println(clicked); // gets text from deleted cell
            entityAttributeView.getItems().remove(clickedFXID); // remove cell from list view
            if (clicked.contains(" @Override ")) {
                parsedAttributeName = old_attribute_name.substring(12, old_attribute_name.lastIndexOf(" :"));
            } else if (clicked.contains(" <<PK>> ")) {
                parsedAttributeName = clicked.substring(10, clicked.lastIndexOf(" :"));
            } else {
                parsedAttributeName = clicked.substring(2, clicked.lastIndexOf(" :"));
            }
            reference.DeleteAttribute(this.classNameTextField.getText(), parsedAttributeName);
        }
        this.checkForOperationsInSequence();
        this.reference.classDiagramController.setOverrides(this.reference);
    }

    public void onPrimaryDiagramClick(ActionEvent event) {
        String parsedAttributeName;
        int clickedFXID = entityAttributeView.getSelectionModel().getSelectedIndex();
        if (clickedFXID != -1) {
            String clicked = (String) entityAttributeView.getItems().get(clickedFXID);
            if (clicked.contains(" @Override ")) {
                parsedAttributeName = old_attribute_name.substring(12, old_attribute_name.lastIndexOf(" :"));
            } else if (clicked.contains(" <<PK>> ")) {
                parsedAttributeName = clicked.substring(10, clicked.lastIndexOf(" :"));
            } else {
                parsedAttributeName = clicked.substring(2, clicked.lastIndexOf(" :"));
            }
            ClassController refClass = reference.classDiagramController.findClass(classNameTextField.getText());
            AttributeController attr = refClass.findAttributeByName(parsedAttributeName);
            setPrimaryAttribute(attr, clickedFXID, refClass);
            this.reference.classDiagramController.setOverrides(this.reference);
        }
    }

    @FXML
    public void onRenameAttributeDiagramClick(ActionEvent event) throws IOException {  //TODO
        this.ClickedAttributeIndex = entityAttributeView.getSelectionModel().getSelectedIndex();
        if (this.ClickedAttributeIndex == -1) {
            System.out.println("jsi mimo");
            return;
        }
        System.out.println("Clicked on <" + entityAttributeView.getItems().get(this.ClickedAttributeIndex) + ">");
        old_attribute_name = (String) entityAttributeView.getItems().get(this.ClickedAttributeIndex);

        String parsedAttributeName;
        if (old_attribute_name.contains(" @Override ")) {
            parsedAttributeName = old_attribute_name.substring(12, old_attribute_name.lastIndexOf(" :"));
        } else if (old_attribute_name.contains(" <<PK>> ")) {
            parsedAttributeName = old_attribute_name.substring(10, old_attribute_name.lastIndexOf(" :"));
        } else {
            parsedAttributeName = old_attribute_name.substring(2, old_attribute_name.lastIndexOf(" :"));
        }

        AttributeController found_attribute = reference.getAttributeControllerByName(this.classNameTextField.getText(), parsedAttributeName);
        System.out.println("FOUND REFERENCE " + found_attribute);
        System.out.println("TYPE: " + found_attribute.getType());
        System.out.println("DATATYPE: " + found_attribute.getDatatype());

        System.out.println(parsedAttributeName);
        if (found_attribute.getType().equals("attribute")) {
            this.renameAttributeInEntityController(parsedAttributeName, this.ClickedAttributeIndex, found_attribute);
        } else if (found_attribute.getType().equals("function")) {
            System.out.println("INSIDE");
            this.renameFunctionInEntityController(parsedAttributeName.substring(0, parsedAttributeName.lastIndexOf("(")), this.ClickedAttributeIndex);
        }
        this.checkForOperationsInSequence();
        this.reference.classDiagramController.setOverrides(this.reference);
    }

    public void renameFunctionInEntityController(String old_function_name, int index) throws IOException {
        AttributeController new_attr = onEditFunctionDiagramClick(null, index, old_function_name);
        //System.out.println("TO COMPARE " + old_function_name + " AND " + new_attr.getName());
        renamingProcess(old_function_name, index, new_attr);
        this.reference.classDiagramController.setOverrides(this.reference);
    }

    public void renameAttributeInEntityController(String old_attribute_name, int index, AttributeController found_attribute) {
        AttributeController new_attr = onEditAttributeClick(null, found_attribute);
        renamingProcess(old_attribute_name, index, new_attr);
        this.reference.classDiagramController.setOverrides(this.reference);
    }

    private void renamingProcess(String old_attribute_name, int index, AttributeController new_attr) {
        if (new_attr == null) {
            AlertBox.display("Note", "Please enter all parameters", "Close");
            return;
        }

        ClassController refClass = reference.classDiagramController.findClass(classNameTextField.getText());
        if (!old_attribute_name.equals(new_attr.getName())) {
            if (refClass.findAttributeByName(new_attr.getName()) != null) {
                System.out.println("TU SOM TO ZHODIL ");
                AlertBox.display("Warning", "Attribute or operation with name '" + new_attr.getName() + "' already exists!", "Close");
                return;
            }
        }
        AttributeController old_attr = refClass.findAttributeByName(old_attribute_name);
        old_attr.copyParams(new_attr);
        entityAttributeView.getItems().set(index, old_attr.getWholeAttributeString());
    }


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
            reference.DeleteAttribute(classNameTextField.getText(), old_attribute_name);
            //referece.AddAttribute(classNameTextField.getText(), new_attribute_name);
            onAddAttributeClick(null);
        }
        this.checkForOperationsInSequence();
        this.reference.classDiagramController.setOverrides(this.reference);
    }

    @FXML
    public void onClassDiagramNameEnter(ActionEvent event) {
        System.out.println(this.old_class_name);
        if (reference.RenameClass(this.old_class_name, this.classNameTextField.getText())) {
            this.old_class_name = classNameTextField.getText();
        } else {
            this.classNameTextField.setText(this.old_class_name);
            AlertBox.display("Note", "Class Diagram With Same Name Already Exists", "Understood");
        }
        this.reference.classDiagramController.setOverrides(this.reference);
    }

    @FXML
    public void onClassDiagramNameClick(Event event) {
        this.old_class_name = classNameTextField.getText();
    }

    @FXML
    public void onAddConstraintClick(Event event) {
        reference.SetConstraintFrom(classNameTextField.getText());
    }

    @FXML
    public void onPasteConstraintClick(Event event) {
        reference.SetConstraintTo(classNameTextField.getText(), "", "new constraint", "", "");
    }

    public void handleCloseButton() {
        functionPopUpStage.close();
    }

    /* *********************************     SEQUENCE DIAGRAM PART      ***************************************** */

    @FXML
    public void onDeleteDiagramClick(ActionEvent event) {
        System.out.println("Delete Diagram Context Menu Click");
        this.identifier = classVBox;
        reference.set_identifier(classNameTextField.getText(), this.identifier);
        reference.DeleteDiagram(event, this);
        this.reference.classDiagramController.setOverrides(this.reference);
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

    public void setClassNameTextField(String name) {
        this.classNameTextField.setText(name);
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

    public ArrayList<LifeLine> getLifeLineList() {
        return this.LifeLineList;
    }

    public void onAddLifeLineContextMenuClick(ActionEvent event) {
        System.out.println("On add life line click");
        sequenceControllerReference.createLifeLineBindToEntity(event, sequenceControllerReference.findEntity(sequenceDiagramNameTextField.getText()));
        System.out.println("REFERENCE " + this.reference);
        try {
            this.checkForLifeLinesInSequence();
        } catch (Exception e) {
            this.checkForLifeLineColourAfterAdd();
        }
    }

    public String getNameTextField() {
        return this.classNameTextField.getText();
    }

    public void setPrimaryAttribute(AttributeController new_attr, int index, ClassController ref_class) {
        if (new_attr.getType().equals("function")) {
            AlertBox.display("warning", "Function cannot be primary key", "OK");
            return;
        }

        if (new_attr.isPrimary()) {
            new_attr.setPrimary(false);
            entityAttributeView.getItems().set(ref_class.getAttrPosition(new_attr), new_attr.getWholeAttributeString());
            return;
        }

        for (AttributeController attr : ref_class.getAttributes()) {
            attr.setPrimary(false);
            entityAttributeView.getItems().set(ref_class.getAttrPosition(attr), attr.getWholeAttributeString());
        }

        new_attr.setPrimary(true);
        entityAttributeView.getItems().set(ref_class.getAttrPosition(new_attr), new_attr.getWholeAttributeString());
    }

    public int getAttributeIndex(String name) {
        for (int i = 0; i < entityAttributeView.getItems().size(); i++) {
            String text = entityAttributeView.getItems().get(i).toString();

            if (text.contains("(")) {

                if (text.contains(" @Override ")) {
                    if (name.equals(text.substring(12, text.lastIndexOf("(")))) {
                        return i;
                    }
                } else {
                    if (name.equals(text.substring(2, text.lastIndexOf("(")))) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    public SequenceDiagramController getSequenceControllerReference() {
        return this.sequenceControllerReference;
    }

    public void checkForOperationsInSequence() {
        for (SequenceDiagramController sequenceDiagramController : this.reference.sequenceDiagramControllerList) {
            if (sequenceDiagramController != null) {
                System.out.println(">>> NASIEL SOM REFERENCE");
                EntityController entity = sequenceDiagramController.findSequenceEntity(this.getNameTextField());
                if (entity != null) {
                    for (LifeLine lifeLine : entity.getLifeLineList()) {
                        System.out.println(">>> PRECHADZAM CEZ LIFE LINES");
                        for (MessageLine messageLine : lifeLine.getMessageLineList()) {
                            System.out.println(">>> PRECHADZAM CEZ MESSAGE LINES");
                            System.out.println(messageLine);
                            messageLine.checkForOperationAvailability();
                        }
                    }
                }
            }
        }
    }

    public void checkForLifeLinesInSequence() {
        for (SequenceDiagramController sequenceDiagramController : this.reference.sequenceDiagramControllerList) {
            if (sequenceDiagramController != null) {
                System.out.println("NIE JE NULL");
                EntityController entity = sequenceDiagramController.findSequenceEntity(this.getNameTextField());
                if (entity != null) {
                    for (LifeLine lifeLine : entity.getLifeLineList()) {
                        System.out.println("LOOP CEZ LIFE LAJNY");
                        lifeLine.checkForClassAvailability(sequenceDiagramController);
                    }
                }
            }
        }
    }

    public void checkForLifeLineColourAfterAdd() {
        for (SequenceDiagramController sequenceDiagramController : this.reference.sequenceDiagramControllerList) {
            if (sequenceDiagramController != null) {
                System.out.println("NIE JE NULL");
                EntityController entity = sequenceDiagramController.findSequenceEntity(this.getSequenceNameTextField());
                if (entity != null) {
                    for (LifeLine lifeLine : entity.getLifeLineList()) {
                        System.out.println("LOOP CEZ LIFE LAJNY");
                        lifeLine.checkForClassAvailability(sequenceDiagramController);
                    }
                }
            }
        }
    }

    public LifeLine getLifeLineByYPosition(String PositionY) {
        System.out.println("FINDING LIFE LINE FOR SEQUENCE");
        for (LifeLine lifeLine : sequenceControllerReference.findSequenceEntity(this.getSequenceNameTextField()).getLifeLineList()) {
            System.out.println("LOOP THROUGH LIFE LINES");
            //   System.out.println("COMPARING>" + lifeLine.getLifeLine().getStartY() + "<WITH>" + Double.parseDouble(PositionY) + "<");
            if (lifeLine.getLifeLine().getStartY() == Double.parseDouble(PositionY)) {
                System.out.println("FOUND " + lifeLine.toString());
                return lifeLine;
            }
        }
        return null;
    }

    public LifeLine getLifeLineByIdentification(String LifeLineIdentificator) {
        System.out.println("FINDING LIFE LINE FOR SEQUENCE");
        for (LifeLine lifeLine : sequenceControllerReference.findSequenceEntity(this.getSequenceNameTextField()).getLifeLineList()) {
            System.out.println("LOOP THROUGH LIFE LINES");
            System.out.println("COMPARING>" + lifeLine.getLifeLineIdentificator() + "<WITH>" + Integer.parseInt(LifeLineIdentificator) + "<");
            if (lifeLine.getLifeLineIdentificator() == Integer.parseInt(LifeLineIdentificator)) {
                System.out.println("FOUND " + lifeLine.toString());
                return lifeLine;
            }
        }
        return null;
    }
}
