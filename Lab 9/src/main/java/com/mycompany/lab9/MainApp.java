/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab9;

/**
 *
 * @author Seby
 */

import com.mycompany.lab9.entity.City;
import com.mycompany.lab9.repository.CityRepository;
import com.mycompany.lab9.util.DataImporter;
import com.mycompany.lab9.util.DistanceCalculator;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        CityRepository cityRepository = new CityRepository();
        try {
            // Import data
            DataImporter.importData("world-capitals.csv");
            System.out.println("Data imported successfully!");

            // Calculate distances between capitals
            List<City> capitals = cityRepository.findCapitals();

            City paris = cityRepository.findByName("Paris").stream().findFirst().orElse(null);
            City berlin = cityRepository.findByName("Berlin").stream().findFirst().orElse(null);
            City tokyo = cityRepository.findByName("Tokyo").stream().findFirst().orElse(null);

            if (paris != null && berlin != null && tokyo != null) {
                System.out.printf("Distance between %s and %s: %.2f km%n",
                        paris.getName(), berlin.getName(),
                        DistanceCalculator.calculateDistance(paris, berlin));
                System.out.printf("Distance between %s and %s: %.2f km%n",
                        paris.getName(), tokyo.getName(),
                        DistanceCalculator.calculateDistance(paris, tokyo));
                System.out.printf("Distance between %s and %s: %.2f km%n",
                        berlin.getName(), tokyo.getName(),
                        DistanceCalculator.calculateDistance(berlin, tokyo));
            } else {
                System.out.println("One or more cities not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cityRepository.close();
        }
    }
}