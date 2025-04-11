/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab7;

/**
 *
 * @author Seby
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bag {
    private List<Tile> tiles;

    public Bag() {
        tiles = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            for (int i = 0; i < 10; i++) {
                tiles.add(new Tile(c));
            }
        }
    }

    public synchronized List<Tile> extractTiles(int count) {
        List<Tile> extracted = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count && !tiles.isEmpty(); i++) {
            int index = random.nextInt(tiles.size());
            extracted.add(tiles.remove(index));
        }
        return extracted;
    }

    public synchronized void addTiles(List<Tile> discardedTiles) {
        tiles.addAll(discardedTiles);
    }

    public synchronized boolean isEmpty() {
        return tiles.isEmpty();
    }

    public synchronized int size() {
        return tiles.size();
    }
}
