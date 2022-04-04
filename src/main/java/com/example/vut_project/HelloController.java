package com.example.vut_project;

import eu.hansolo.tilesfx.events.AlarmEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FilenameFilter;
import java.util.jar.JarFile;

public class HelloController {
    @FXML
    public Label FileOpenButton;
    public Label NewProjectButton;
    @FXML
    private Label welcomeText;

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
    protected void onNewProjectButtonClick(){
        ProjectLayout projectLayout = new ProjectLayout();
        projectLayout.CreateProjectLayout();
    }
}