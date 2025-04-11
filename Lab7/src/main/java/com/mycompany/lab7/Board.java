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

public class Board {
    private List<String> words;

    public Board() {
        words = new ArrayList<>();
    }

    public synchronized void submitWord(String word, Player player) {
        words.add(word);
        System.out.println(player.getName() + " submitted word: " + word);
    }

    public List<String> getWords() {
        return words;
    }
}