/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema6;

/**
 *
 * @author Seby
 */

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class DrawingPanel extends Pane {
    private Canvas canvas;
    private GameModel gameModel;
    private ConfigPanel configPanel;
    private static final double DOT_RADIUS = 5;
    private Point selectedPoint;

    public DrawingPanel(GameModel gameModel) {
        this.gameModel = gameModel;
        this.canvas = new Canvas(600, 400);
        getChildren().add(canvas);
        selectedPoint = null;

        canvas.setOnMouseClicked(this::handleMouseClick);
        redraw();
    }

    public void setConfigPanel(ConfigPanel configPanel) {
        this.configPanel = configPanel;
    }

    private void handleMouseClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        Point clickedPoint = findNearestPoint(x, y);

        if (clickedPoint != null) {
            if (selectedPoint == null) {
                selectedPoint = clickedPoint;
            } else {
                if (!selectedPoint.equals(clickedPoint)) {
                    gameModel.addLine(selectedPoint, clickedPoint);
                    if (configPanel != null) {
                        configPanel.updateScores();
                        if (gameModel.isConnected()) {
                            double bestScore = gameModel.getBestScore();
                            configPanel.showBestScore(bestScore);
                            System.out.println("Game Over! Structure is connected.");
                            System.out.println("Player 1 Score: " + gameModel.getPlayer1Score() + " (Best: " + bestScore + ")");
                            System.out.println("Player 2 Score: " + gameModel.getPlayer2Score() + " (Best: " + bestScore + ")");
                        }
                    }
                }
                selectedPoint = null;
                redraw();
            }
        }
    }

    private Point findNearestPoint(double x, double y) {
        for (Point point : gameModel.getPoints()) {
            double distance = Math.sqrt(Math.pow(point.getX() - x, 2) + Math.pow(point.getY() - y, 2));
            if (distance <= DOT_RADIUS * 2) {
                return point;
            }
        }
        return null;
    }

    public double getCanvasWidth() {
        return canvas.getWidth();
    }

    public double getCanvasHeight() {
        return canvas.getHeight();
    }

    public void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (Line line : gameModel.getLines()) {
            gc.setStroke(line.getColor());
            gc.setLineWidth(2);
            gc.strokeLine(line.getStart().getX(), line.getStart().getY(), line.getEnd().getX(), line.getEnd().getY());
        }

        gc.setFill(Color.BLACK);
        for (Point point : gameModel.getPoints()) {
            gc.fillOval(point.getX() - DOT_RADIUS, point.getY() - DOT_RADIUS, 2 * DOT_RADIUS, 2 * DOT_RADIUS);
        }

        if (selectedPoint != null) {
            gc.setFill(Color.GREEN);
            gc.fillOval(selectedPoint.getX() - DOT_RADIUS, selectedPoint.getY() - DOT_RADIUS, 2 * DOT_RADIUS, 2 * DOT_RADIUS);
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }
}