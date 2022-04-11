package com.example.vut_project.controller;

import java.util.ArrayList;

// Class diagram stores classes
// Ještě česky pro Andyho
// V Class diagramu je list classů
// Slovensky
// V Class digrame je list classov
public class ClassDiagramController extends ElementController {

    private ArrayList<ClassController> classList;
    private ArrayList<ClassifierController> classifierList;

    public ClassDiagramController(String name) {
        super(name);
        this.classList = new ArrayList<>();
        this.classifierList = new ArrayList<>();
    }

    /**
     * Creates a new class in class diagram and add the class to classList
     *
     * @param name of the new class
     * @return Object of the new class
     */
    public ClassController createClass(String name) {
        ClassController newClass = new ClassController(name);
        System.out.println("creating: " + name);
        if (this.classList.contains(newClass)) {
            return null;
        }
        this.classList.add(newClass);
        System.out.println("Created! : " + this.return_list().toString());
        return newClass;
    }

    /**
     * Removes a class from class diagram and from ClassList
     *
     * @param name of class to be removed
     * @return true if successful, otherwise false
     */
    public boolean deleteClass(ClassifierController name) {
        System.out.println("removing " + name.toString() + " in " + this.return_list().toString());
        if (this.classList.contains(name)) {
            this.classList.remove(name);
            System.out.println("actual list: " + this.return_list().toString());
            return true;
        }
        return false;
    }

    /**
     * Finds classifier in diagram
     *
     * @param name of the classifier
     * @return found classifier or null
     */
    public ClassifierController findClassifier(String name) {
        System.out.println("finding: " + name + " in " + this.classList.toString());
        for (ClassifierController Classifier : this.classifierList) {
            if (Classifier.getName().equals(name)) {
                return Classifier;
            }
        }

        for (ClassController Classifier : this.classList) {
            if (Classifier.getName().equals(name)) {
                return Classifier;
            }
        }
        return null;
    }

    /**
     * Returns list of Class names
     *
     * @return string list
     */
    public ArrayList<String> return_list() {
        ArrayList<String> list = new ArrayList<>();
        for (ClassController Class : classList) {
            list.add(Class.getName());
        }
        return list;
    }
}

