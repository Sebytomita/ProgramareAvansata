/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema6;

/**
 *
 * @author Seby
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {
    private List<Point> points;
    private List<Line> lines;

    public Graph(List<Point> points, List<Line> lines) {
        this.points = points;
        this.lines = lines;
    }

    public boolean isConnected() {
        if (points.isEmpty()) return true;
        Set<Point> visited = new HashSet<>();
        dfs(points.get(0), visited);
        return visited.size() == points.size();
    }

    private void dfs(Point point, Set<Point> visited) {
        visited.add(point);
        for (Line line : lines) {
            if (line.getStart().equals(point) && !visited.contains(line.getEnd())) {
                dfs(line.getEnd(), visited);
            } else if (line.getEnd().equals(point) && !visited.contains(line.getStart())) {
                dfs(line.getStart(), visited);
            }
        }
    }
}