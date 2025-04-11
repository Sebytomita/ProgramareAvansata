/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema6;

/**
 *
 * @author Seby
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameModel gameModel = new GameModel();
        DrawingPanel drawingPanel = new DrawingPanel(gameModel);
        ConfigPanel configPanel = new ConfigPanel(gameModel, drawingPanel::redraw);
        drawingPanel.setConfigPanel(configPanel);
        ControlPanel controlPanel = new ControlPanel(gameModel, drawingPanel, configPanel);

        BorderPane root = new BorderPane();
        root.setTop(configPanel);
        root.setCenter(drawingPanel);
        root.setBottom(controlPanel);

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setTitle("Dots and Lines Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}