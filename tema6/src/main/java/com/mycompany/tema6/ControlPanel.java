/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema6;

/**
 *
 * @author Seby
 */

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.image.BufferedImage;

public class ControlPanel extends HBox {
    private GameModel gameModel;
    private DrawingPanel drawingPanel;
    private ConfigPanel configPanel;

    public ControlPanel(GameModel gameModel, DrawingPanel drawingPanel, ConfigPanel configPanel) {
        this.gameModel = gameModel;
        this.drawingPanel = drawingPanel;
        this.configPanel = configPanel;
        setPadding(new Insets(10));
        setSpacing(10);

        Button loadButton = new Button("Load");
        Button saveButton = new Button("Save");
        Button exportButton = new Button("Export PNG");
        Button exitButton = new Button("Exit");

        loadButton.setOnAction(event -> loadGame());
        saveButton.setOnAction(event -> saveGame());
        exportButton.setOnAction(event -> exportToPNG());
        exitButton.setOnAction(event -> System.exit(0));

        getChildren().addAll(loadButton, saveButton, exportButton, exitButton);
    }

    private void saveGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Game Files", "*.game"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(gameModel);
                System.out.println("Game saved to " + file.getAbsolutePath());
            } catch (Exception e) {
                System.err.println("Error saving game: " + e.getMessage());
            }
        }
    }

    private void loadGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Game Files", "*.game"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                GameModel loadedModel = (GameModel) ois.readObject();
                gameModel.generatePoints(0, 0, 0); 
                gameModel = loadedModel;
                drawingPanel.redraw();
                if (configPanel != null) {
                    configPanel.updateScores();
                    if (gameModel.isConnected()) {
                        configPanel.showBestScore(gameModel.getBestScore());
                    }
                }
                System.out.println("Game loaded from " + file.getAbsolutePath());
            } catch (Exception e) {
                System.err.println("Error loading game: " + e.getMessage());
            }
        }
    }

    private void exportToPNG() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export to PNG");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                BufferedImage image = SwingFXUtils.fromFXImage(drawingPanel.getCanvas().snapshot(null, null), null);
                ImageIO.write(image, "png", file);
                System.out.println("Board exported to " + file.getAbsolutePath());
            } catch (Exception e) {
                System.err.println("Error exporting to PNG: " + e.getMessage());
            }
        }
    }
}