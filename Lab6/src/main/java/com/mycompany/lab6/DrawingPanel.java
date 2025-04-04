/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab6;

/**
 *
 * @author Seby
 */

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class DrawingPanel extends Pane {
    private Canvas canvas;
    private GameModel gameModel;
    private static final double DOT_RADIUS = 5;

    public DrawingPanel(GameModel gameModel) {
        this.gameModel = gameModel;
        this.canvas = new Canvas(600, 400);
        getChildren().add(canvas);
        redraw();
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

        gc.setFill(Color.BLACK);
        for (Point point : gameModel.getPoints()) {
            gc.fillOval(point.getX() - DOT_RADIUS, point.getY() - DOT_RADIUS, 2 * DOT_RADIUS, 2 * DOT_RADIUS);
        }
    }
}