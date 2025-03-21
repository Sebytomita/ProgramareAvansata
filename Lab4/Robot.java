/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab4;

/**
 *
 * @author Seby
 */

public class Robot {
    private Location currentLocation;

    public Robot(Location startLocation) {
        this.currentLocation = startLocation;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location location) {
        this.currentLocation = location;
    }

    @Override
    public String toString() {
        return "Robot{currentLocation=" + currentLocation.getName() + "}";
    }
}
