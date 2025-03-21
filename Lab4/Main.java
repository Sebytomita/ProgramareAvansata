/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab4;

/**
 *
 * @author Seby
 */

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Step 4: Create an array of locations
        Location[] locations = {
            new Location("Base1", LocationType.FRIENDLY),
            new Location("Base2", LocationType.FRIENDLY),
            new Location("BaseN", LocationType.NEUTRAL),
            new Location("BaseA", LocationType.ENEMY),
            new Location("BaseB", LocationType.ENEMY),
            new Location("Base0", LocationType.NEUTRAL)
        };

        List<Location> locationList = Arrays.asList(locations);

        TreeSet<Location> friendlyLocations = locationList.stream()
            .filter(loc -> loc.getType() == LocationType.FRIENDLY)
            .collect(Collectors.toCollection(TreeSet::new));
        
        System.out.println("Friendly Locations:");
        friendlyLocations.forEach(System.out::println);

        LinkedList<Location> enemyLocations = locationList.stream()
            .filter(loc -> loc.getType() == LocationType.ENEMY)
            .sorted(Comparator.comparing(Location::getType)
                .thenComparing(Location::getName))
            .collect(Collectors.toCollection(LinkedList::new));
        
        System.out.println("\nEnemy Locations:");
        enemyLocations.forEach(System.out::println);
    }
}
