module com.example.vut_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.example.vut_project to javafx.fxml;
    exports com.example.vut_project;
    exports com.example.vut_project.controller;
    opens com.example.vut_project.controller to javafx.fxml;
}