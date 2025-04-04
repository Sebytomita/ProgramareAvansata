/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab6;

/**
 *
 * @author Seby
 */

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlPanel extends HBox {
    public ControlPanel() {
        setPadding(new Insets(10));
        setSpacing(10);

        Button loadButton = new Button("Load");
        Button saveButton = new Button("Save");
        Button exitButton = new Button("Exit");

        exitButton.setOnAction(event -> System.exit(0));

        loadButton.setOnAction(event -> System.out.println("Load functionality not implemented yet."));
        saveButton.setOnAction(event -> System.out.println("Save functionality not implemented yet."));

        getChildren().addAll(loadButton, saveButton, exitButton);
    }
}