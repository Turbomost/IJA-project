package com.example.vut_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController{
    private Stage stage;
    private Parent root;
    private Scene scene;
    @FXML
    private Label FileOpenButton;
    private Label NewProjectButton;
    @FXML
    private Label AddDiagramButton;
    @FXML
    private Label welcomeText;
    @FXML
    private Rectangle rectangle;

    DraggableMarker draggableMaker = new DraggableMarker();

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
    protected void onNewProjectButtonClick() throws Exception{
        Stage stage = new Stage();
        Pane Pane = null;
        Pane = FXMLLoader.load(getClass().getResource("new_project_view.fxml"));
        Scene scene = new Scene(Pane);
        stage.setTitle("New Project");
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    public void onMiddleButtonClick(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("new_project_view.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        assert root != null;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onAddDiagramButtonClick(ActionEvent event) {
        System.out.println("Released");
        System.out.println("init");
        draggableMaker.makeDraggable(rectangle);
    }
}