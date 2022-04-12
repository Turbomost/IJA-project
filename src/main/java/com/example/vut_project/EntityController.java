//controller of class diagram entity template
package com.example.vut_project;

import com.example.vut_project.controller.AttributeController;
import com.example.vut_project.controller.ClassController;
import com.example.vut_project.controller.ClassDiagramController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Entity class represents every Class in ClassDiagram. It's used for storing data and Click events.
 */
public class EntityController extends VBox {

    private final ObservableList<String> observableListOfAttributes = FXCollections.observableArrayList();
    @FXML
    private TextField classNameTextField;
    @FXML
    private MenuItem deleteDiagramContextButton;
    @FXML
    private ListView entityAttributeView;
    @FXML
    private MenuItem addAttributeContextMenuButton;
    @FXML
    private ContextMenu contextMenuOnElement;

    /**
     * Constructor for new Entity
     *
     * @param new_name name of new entity
     * @param diagram  ClassDiagram
     * @throws IOException
     */
    public EntityController(String new_name, ClassDiagramController diagram) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/vut_project/class_diagram_entity_template.fxml")); //new object (aba class diagram) is created as pane
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        ClassController new_class = diagram.createClass(new_name);
        classNameTextField.setText(new_class.getName());
        entityAttributeView.setEditable(true);
        entityAttributeView.setCellFactory(TextFieldListCell.forListView());
    }

    /**
     * Constructor for loading from file
     *
     * @param class_name name of new class to be created from file
     * @throws IOException
     */
    public EntityController(ClassController class_name) throws IOException { //diagram added loaded file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("class_diagram_entity_template.fxml")); //new object (aba class diagram) is created as pane
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        classNameTextField.setText(class_name.getName());
        for (AttributeController attribute : class_name.getAttributes()) {
            observableListOfAttributes.add(attribute.getName());
        }
        this.entityAttributeView.setItems(observableListOfAttributes);
        entityAttributeView.setEditable(true);
        entityAttributeView.setCellFactory(TextFieldListCell.forListView());

    }

    /**
     * Debug event that prints ClassName when clicked on Class
     *
     * @param mouseEvent
     */
    public void onClassDiagramClick(MouseEvent mouseEvent) {
        //System.out.println(this.classNameTextField.getText());
    }

    /**
     * Event for adding attributes
     *
     * @param event
     */
    public void onAddAttributeClick(ActionEvent event) {
        this.entityAttributeView.getItems().add("new_argument");
    }

}
