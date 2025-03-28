/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab4;

/**
 *
 * @author Seby
 */

public class Edge {
    private Location from;
    private Location to;
    private int time; // in minutes
    private double probability; // 0.0 to 1.0

    public Edge(Location from, Location to, int time, double probability) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.probability = probability;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public int getTime() {
        return time;
    }

    public double getProbability() {
        return probability;
    }

    @Override
    public String toString() {
        return "Edge{from=" + from.getName() + ", to=" + to.getName() + 
               ", time=" + time + ", probability=" + probability + "}";
    }
}
