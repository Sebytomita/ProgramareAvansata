/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab7;

/**
 *
 * @author Seby
 */

import java.util.Random;

public class Tile {
    private char letter;
    private int points;

    public Tile(char letter) {
        this.letter = letter;
        this.points = new Random().nextInt(10) + 1;
    }

    public char getLetter() {
        return letter;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return letter + "(" + points + ")";
    }
}