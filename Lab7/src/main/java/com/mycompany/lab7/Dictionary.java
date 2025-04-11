/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab7;

/**
 *
 * @author Seby
 */

import java.util.Arrays;
import java.util.List;

public class Dictionary {
    private List<String> words;

    public Dictionary() {
        words = Arrays.asList("CAT", "DOG", "HAT", "BAT", "RAT", "MAT", "FAN", "PAN", "CAN", "TAN");
    }

    public boolean isValidWord(String word) {
        return words.contains(word.toUpperCase());
    }

    public List<String> getWords() {
        return words;
    }
}
