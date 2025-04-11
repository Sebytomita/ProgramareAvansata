/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema6;

/**
 *
 * @author Seby
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;

public class GameModel implements Serializable {
    private List<Point> points;
    private List<Line> lines;
    private Random random;
    private int currentPlayer;
    private double player1Score;
    private double player2Score;
    private double bestScore;

    public GameModel() {
        this.points = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.random = new Random();
        this.currentPlayer = 1;
        this.player1Score = 0;
        this.player2Score = 0;
        this.bestScore = Double.POSITIVE_INFINITY;
    }

    public void generatePoints(int numPoints, double canvasWidth, double canvasHeight) {
        points.clear();
        lines.clear();
        currentPlayer = 1;
        player1Score = 0;
        player2Score = 0;
        bestScore = Double.POSITIVE_INFINITY;
        for (int i = 0; i < numPoints; i++) {
            double x = random.nextDouble() * (canvasWidth - 20) + 10;
            double y = random.nextDouble() * (canvasHeight - 20) + 10;
            points.add(new Point(x, y));
        }
        
        MSTCalculator mst = new MSTCalculator(points);
        bestScore = mst.calculateMST();
    }

    public void addLine(Point start, Point end) {
        Color color = currentPlayer == 1 ? Color.BLUE : Color.RED;
        Line line = new Line(start, end, color);
        lines.add(line);
        double length = line.getLength();
        if (currentPlayer == 1) {
            player1Score += length;
        } else {
            player2Score += length;
        }
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    public boolean isConnected() {
        Graph graph = new Graph(points, lines);
        return graph.isConnected();
    }

    public double getBestScore() {
        return bestScore == Double.POSITIVE_INFINITY ? 0.0 : bestScore;
    }

    public List<Point> getPoints() {
        return new ArrayList<>(points);
    }

    public List<Line> getLines() {
        return new ArrayList<>(lines);
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public double getPlayer1Score() {
        return player1Score;
    }

    public double getPlayer2Score() {
        return player2Score;
    }
}