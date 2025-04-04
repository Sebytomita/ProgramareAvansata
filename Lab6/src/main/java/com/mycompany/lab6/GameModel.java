/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab6;

/**
 *
 * @author Seby
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
    private List<Point> points;
    private Random random;

    public GameModel() {
        this.points = new ArrayList<>();
        this.random = new Random();
    }

    public void generatePoints(int numPoints, double canvasWidth, double canvasHeight) {
        points.clear();
        for (int i = 0; i < numPoints; i++) {
            double x = random.nextDouble() * (canvasWidth - 20) + 10;
            double y = random.nextDouble() * (canvasHeight - 20) + 10;
            points.add(new Point(x, y));
        }
    }

    public List<Point> getPoints() {
        return new ArrayList<>(points);
    }
}