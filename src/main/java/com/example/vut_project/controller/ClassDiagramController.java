/**
 * @Brief: Class diagram representation
 * @File: ClassDiagramController
 * @Author(s): A. Ľupták, V. Valenta
 */

package com.example.vut_project.controller;

import com.example.vut_project.EntityController;
import com.example.vut_project.HelloController;
import com.example.vut_project.SequenceDiagramController;

import java.util.ArrayList;

/**
 * Class diagram stores classes. It stores all classes in arraylist
 */
public class ClassDiagramController extends ElementController {

    private final ArrayList<ClassController> classList;

    /**
     * Default constructor for ClassDiagram
     *
     * @param name name of new ClassDiagram
     */
    public ClassDiagramController(String name) {
        super(name);
        this.classList = new ArrayList<>();
    }

    /**
     * Creates a new class in class diagram and add the class to classList
     *
     * @param name name of the new class
     * @return Object of the new class
     */
    public ClassController createClass(String name) {
        ClassController newClass = new ClassController(name);
        System.out.println("creating: " + name);
        if (this.classList.contains(newClass)) {
            AlertBox.display("Note", "Class Diagram Already Exists", "Understood");
            return null;
        }
        this.classList.add(newClass);
        System.out.println("Created! : " + this.getClassList().toString());
        return newClass;
    }

    /**
     * Removes a class from class diagram and from ClassList
     *
     * @param name of class to be removed
     * @return true if successful, otherwise false
     */
    public int deleteClass(ClassController name, ArrayList<SequenceDiagramController> sequenceDiagramControllerList) {
        int choice = 1;
        if (this.classList.contains(name)) {
            for (SequenceDiagramController sequenceDiagramController : sequenceDiagramControllerList) {
                if (sequenceDiagramController != null) {
                    EntityController entity = sequenceDiagramController.findEntity(name.getName());
                    if (entity != null) {
                        choice = YesNoAlertBox.display("Warning", "Do you wanna delete class '" + name.getName() + "' from sequence diagram?", "Yes", "No");
                        if (choice == 1) {
                            entity.onDeleteSequenceDiagramContextMenuClick(null);
                        } else if (choice == -1) {
                            return -1;
                        }
                        else{
                            choice = 0;
                        }
                    }
                }
            }
            name.delete_constraints();
            this.classList.remove(name);
            return choice;
        }
        return -1;
    }

    /**
     * Finds class in diagram
     *
     * @param name of the class
     * @return found classifier or null
     */
    public ClassController findClass(String name) {
        //System.out.println("finding: " + name + " in " + this.classList.toString());

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

    public java.util.List<String> getClassList() {
        ArrayList<String> nameList = new ArrayList<>();
        for (ClassController attr : this.classList) {
            nameList.add(attr.getName());
        }
        return nameList;
    }

    public java.util.List<String> getUniqueClassList(ArrayList<String> elements) {
        ArrayList<String> nameList = new ArrayList<>();
        for (String attr : getClassList()) {
            if (!elements.contains(attr))
                nameList.add(attr);
        }
        return nameList;
    }

    public void setOverrides(HelloController reference) {
        for (ClassController Class : this.classList) {
            Class.setGeneralizations();
            Class.updateOverrides(reference);
        }
    }
}

