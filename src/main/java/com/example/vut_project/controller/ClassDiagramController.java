package com.example.vut_project.controller;

import java.util.ArrayList;

// Class diagram stores classes ( TODO classifiers? )
public class ClassDiagramController extends ElementController {

    private ArrayList<ClassController> classList;

    public ClassDiagramController(String name) {
        super(name);
        this.classList = new ArrayList<>();
    }

    /**
     * Creates a new class in class diagram and add the class to classList
     * @param name of the new class
     * @return Object of the new class
     */
    public ClassController createClass(String name) {
        ClassController newClass = new ClassController(name);
        if (this.classList.contains(newClass)) {
            return null;
        }
        this.classList.add(newClass);
        return newClass;
    }

    /**
     * Removes a class from class diagram and from ClassList
     * @param name of class to be removed
     * @return true if successful, otherwise false
     */
    public boolean deleteClass(ClassController name) {
        if (this.classList.contains(name)) {
            return false;
        }
        this.classList.remove(name);
        return true;

        // TODO idk if this works
    }
}

