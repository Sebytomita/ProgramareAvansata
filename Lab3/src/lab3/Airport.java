/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3;

/**
 *
 * @author Seby
 */

import java.util.ArrayList;
import java.util.List;

class Airport {
    private List<Runway> runways;

    public Airport(int numberOfRunways) {
        this.runways = new ArrayList<>();
        for (int i = 1; i <= numberOfRunways; i++) {
            runways.add(new Runway(i));
        }
    }

    public List<Runway> getRunways() {
        return runways;
    }

    @Override
    public String toString() {
        return "Airport with " + runways.size() + " runways";
    }
}