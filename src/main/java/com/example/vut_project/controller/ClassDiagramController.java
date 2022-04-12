package com.example.vut_project.controller;

import java.util.ArrayList;

// Class diagram stores classes
// Ještě česky pro Andyho
// V Class diagramu je list classů
// Slovensky
// V Class digrame je list classov
public class ClassDiagramController extends ElementController {

    private ArrayList<ClassController> classList;
    private int activeID = -1;

    public ClassDiagramController(String name) {
        super(name);
        this.classList = new ArrayList<>();
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
    public boolean deleteClass(ClassController name) {
        System.out.println("removing " + name.toString() + " in " + this.return_list().toString());
        if (this.classList.contains(name)) {
            this.classList.remove(name);
            System.out.println("actual list: " + this.return_list().toString());
            return true;
        }
        return false;
    }

    /**
     * Finds class in diagram
     *
     * @param name of the class
     * @return found classifier or null
     */
    public ClassController findClass(String name) {
        System.out.println("finding: " + name + " in " + this.classList.toString());

        for (ClassController Class : this.classList) {
            if (Class.getName().equals(name)) {
                return Class;
            }
        }
        return null;
    }

    /**
     * Find class in diagram by hash
     *
     * @param hash of the class
     * @return found classifier or null
     */
    public ClassController findClassHash(int hash) {
        System.out.println("finding:" + hash);
        for (ClassController Class : this.classList) {
            System.out.println(System.identityHashCode(Class));
            if (System.identityHashCode(Class) == hash) {
                return Class;
            }
        }
        return null;
    }

    /**
     * Returns list of Class names
     *
     * @return string list
     */
    public ArrayList<ClassController> return_list() {
        return classList;
    }

    public int getActiveID(){
        return this.activeID;
    }

    public void setActiveID(int identifier) {
        this.activeID = identifier;
    }
}

