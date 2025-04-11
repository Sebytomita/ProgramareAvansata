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

public class Player implements Runnable {
    private String name;
    private Game game;
    private List<Tile> tiles;
    private int score;

    public Player(String name, Game game) {
        this.name = name;
        this.game = game;
        this.tiles = new ArrayList<>();
        this.score = 0;
    }

    @Override
    public void run() {
        while (game.isGameRunning() && !game.getBag().isEmpty()) {
           
            if (tiles.size() < 7) {
                int tilesToExtract = 7 - tiles.size();
                List<Tile> extracted = game.getBag().extractTiles(tilesToExtract);
                tiles.addAll(extracted);
                System.out.println(name + " extracted: " + extracted);
            }
  
            String word = formWord();
            if (word != null) {
                int wordPoints = calculateWordPoints(word);
                score += wordPoints;
                System.out.println(name + " formed word: " + word + " (" + wordPoints + " points), Total Score: " + score);

                game.getBoard().submitWord(word, this);

                removeTilesForWord(word);

                int k = word.length();
                List<Tile> newTiles = game.getBag().extractTiles(k);
                tiles.addAll(newTiles);
                System.out.println(name + " extracted after submitting: " + newTiles);
            } else {
                System.out.println(name + " cannot form a word, discarding: " + tiles);
                game.getBag().addTiles(tiles);
                tiles.clear();
                List<Tile> newTiles = game.getBag().extractTiles(7);
                tiles.addAll(newTiles);
                System.out.println(name + " extracted after discarding: " + newTiles);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name + " has finished playing.");
    }

    private String formWord() {
        for (String word : game.getDictionary().getWords()) {
            if (canFormWord(word)) {
                return word;
            }
        }
        return null;
    }

    private boolean canFormWord(String word) {
        List<Character> availableLetters = new ArrayList<>();
        for (Tile tile : tiles) {
            availableLetters.add(tile.getLetter());
        }

        for (char c : word.toCharArray()) {
            if (!availableLetters.remove(Character.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    private int calculateWordPoints(String word) {
        int points = 0;
        List<Character> usedLetters = new ArrayList<>();
        for (char c : word.toCharArray()) {
            for (Tile tile : tiles) {
                if (tile.getLetter() == c && !usedLetters.contains(c)) {
                    points += tile.getPoints();
                    usedLetters.add(c);
                    break;
                }
            }
        }
        return points;
    }

    private void removeTilesForWord(String word) {
        List<Tile> tilesToRemove = new ArrayList<>();
        for (char c : word.toCharArray()) {
            for (Tile tile : tiles) {
                if (tile.getLetter() == c) {
                    tilesToRemove.add(tile);
                    break;
                }
            }
        }
        tiles.removeAll(tilesToRemove);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}