//controller of class diagram entity template
package com.example.vut_project;

import com.example.vut_project.controller.AttributeController;
import com.example.vut_project.controller.ClassController;
import com.example.vut_project.controller.ClassDiagramController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EntityController extends VBox {

    private ObservableList<String> observableListOfAttributes = FXCollections.observableArrayList();
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

    public EntityController(String new_name, ClassDiagramController diagram) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/vut_project/class_diagram_entity_template.fxml")); //new object (aba class diagram) is created as pane
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        ClassController new_class = diagram.createClass(new_name);
        classNameTextField.setText(new_class.getName());
    }

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

    }
    public void onClassDiagramClick(MouseEvent mouseEvent){
        System.out.println(this.classNameTextField.getText());
    }
    public void onAddAttributeClick(MouseEvent event){
        System.out.println("CLICKED ON ADD ATTRIBUTE");
    }

}
