/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema6;

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
    private Label player1ScoreLabel;
    private Label player2ScoreLabel;
    private Label bestScoreLabel;
    private GameModel gameModel;
    private Runnable onNewGame;

    public ConfigPanel(GameModel gameModel, Runnable onNewGame) {
        this.gameModel = gameModel;
        this.onNewGame = onNewGame;
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
                gameModel.generatePoints(numPoints, 600, 400);
                onNewGame.run();
                updateScores();
                bestScoreLabel.setText("Best Score: N/A");
            } catch (NumberFormatException e) {
                numPointsField.setText("5");
            }
        });

        player1ScoreLabel = new Label("Player 1 Score: 0.0");
        player2ScoreLabel = new Label("Player 2 Score: 0.0");
        bestScoreLabel = new Label("Best Score: N/A");

        getChildren().addAll(numPointsLabel, numPointsField, newGameButton, player1ScoreLabel, player2ScoreLabel, bestScoreLabel);
    }

    public void updateScores() {
        player1ScoreLabel.setText(String.format("Player 1 Score: %.2f", gameModel.getPlayer1Score()));
        player2ScoreLabel.setText(String.format("Player 2 Score: %.2f", gameModel.getPlayer2Score()));
    }

    public void showBestScore(double bestScore) {
        bestScoreLabel.setText(String.format("Best Score: %.2f", bestScore));
    }
}