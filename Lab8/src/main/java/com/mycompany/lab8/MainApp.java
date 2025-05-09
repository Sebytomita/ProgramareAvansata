/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab8;

/**
 *
 * @author Seby
 */

import com.mycompany.lab8.dao.CityDAO;
import com.mycompany.lab8.model.City;
import com.mycompany.lab8.util.DataImporter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.sql.SQLException;

public class MainApp {
    private static final double EARTH_RADIUS = 6371; 

    public static void main(String[] args) {
        try {
           
            DataImporter importer = new DataImporter();
            importer.importData("src/main/resources/world-capitals.csv");
            System.out.println("Data imported successfully!");
           
            CityDAO cityDAO = new CityDAO();
            City paris = cityDAO.findByName("Paris");
            City berlin = cityDAO.findByName("Berlin");
            City tokyo = cityDAO.findByName("Tokyo");

            if (paris != null && berlin != null) {
                double distance = calculateDistance(paris, berlin);
                System.out.printf("Distance between %s and %s: %.2f km%n", paris.getName(), berlin.getName(), distance);
            }

            if (paris != null && tokyo != null) {
                double distance = calculateDistance(paris, tokyo);
                System.out.printf("Distance between %s and %s: %.2f km%n", paris.getName(), tokyo.getName(), distance);
            }

            if (berlin != null && tokyo != null) {
                double distance = calculateDistance(berlin, tokyo);
                System.out.printf("Distance between %s and %s: %.2f km%n", berlin.getName(), tokyo.getName(), distance);
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        } catch (CsvValidationException e) {
            System.err.println("Error parsing CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static double calculateDistance(City city1, City city2) {
        double lat1 = Math.toRadians(city1.getLatitude());
        double lon1 = Math.toRadians(city1.getLongitude());
        double lat2 = Math.toRadians(city2.getLatitude());
        double lon2 = Math.toRadians(city2.getLongitude());

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;

        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return EARTH_RADIUS * c;
    }
}
