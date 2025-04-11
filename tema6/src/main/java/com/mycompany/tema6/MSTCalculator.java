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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MSTCalculator {
    private List<Point> points;

    public MSTCalculator(List<Point> points) {
        this.points = points;
    }

    public double calculateMST() {
        if (points.size() <= 1) {
            System.out.println("MST: Less than 2 points, returning 0.0");
            return 0.0;
        }

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                Point p1 = points.get(i);
                Point p2 = points.get(j);
                double weight = p1.distanceTo(p2);
                edges.add(new Edge(p1, p2, weight));
            }
        }

        Collections.sort(edges, Comparator.comparingDouble(Edge::getWeight));

        System.out.println("Sorted edges:");
        for (Edge edge : edges) {
            System.out.println("Edge: (" + edge.getStart().getX() + "," + edge.getStart().getY() + ") -> (" +
                    edge.getEnd().getX() + "," + edge.getEnd().getY() + "), Weight: " + edge.getWeight());
        }

        int n = points.size();
        int numEdgesToTake = n / 2; 
        double totalWeight = 0.0;

        System.out.println("Taking first " + numEdgesToTake + " edges for Best Score:");
        for (int i = 0; i < numEdgesToTake && i < edges.size(); i++) {
            Edge edge = edges.get(i);
            totalWeight += edge.getWeight();
            System.out.println("Added edge: (" + edge.getStart().getX() + "," + edge.getStart().getY() + ") -> (" +
                    edge.getEnd().getX() + "," + edge.getEnd().getY() + "), Weight: " + edge.getWeight() +
                    ", Total Weight: " + totalWeight);
        }

        System.out.println("Best Score (first " + numEdgesToTake + " edges): " + totalWeight);
        return totalWeight;
    }

    private static class Edge {
        private Point start;
        private Point end;
        private double weight;

        public Edge(Point start, Point end, double weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        public Point getStart() {
            return start;
        }

        public Point getEnd() {
            return end;
        }

        public double getWeight() {
            return weight;
        }
    }
}