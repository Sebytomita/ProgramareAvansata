/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab6;

/**
 *
 * @author Seby
 */

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

public class ConfigPanel extends HBox {
    private TextField numPointsField;
    private Button newGameButton;

    public ConfigPanel(GameModel gameModel, DrawingPanel drawingPanel) {
        setPadding(new Insets(10));
        setSpacing(10);

        Label numPointsLabel = new Label("Number of Dots:");
        numPointsField = new TextField("5");
        numPointsField.setPrefWidth(50);

        newGameButton = new Button("New Game");

        newGameButton.setOnAction(event -> {
            try {
                int numPoints = Integer.parseInt(numPointsField.getText());
                if (numPoints <= 0) {
                    throw new NumberFormatException("Number of dots must be positive");
                }
                gameModel.generatePoints(numPoints, drawingPanel.getCanvasWidth(), drawingPanel.getCanvasHeight());
                drawingPanel.redraw();
            } catch (NumberFormatException e) {
                numPointsField.setText("5");
            }
        });

        getChildren().addAll(numPointsLabel, numPointsField, newGameButton);
    }
}