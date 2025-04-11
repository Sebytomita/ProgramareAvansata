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
import javafx.scene.paint.Color;

public class Line implements Serializable {
    private Point start;
    private Point end;
    private Color color;

    public Line(Point start, Point end, Color color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public Color getColor() {
        return color;
    }

    public double getLength() {
        return start.distanceTo(end);
    }
}