/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3;

/**
 *
 * @author Seby
 */

import java.time.LocalTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Airliner airliner1 = new Airliner("Boeing 100A", "BA123", 39.5, 120);
        Freighter freighter1 = new Freighter("Boeing 200F", "FX456", 10000);
        Drone drone1 = new Drone("Drone 1", "DR789", 45.0, 500.0);

        Airport airport = new Airport(2);

        Set<Flight> flights = new HashSet<>();
        flights.add(new Flight(airliner1, "FL001", 
            LocalTime.of(10, 0), LocalTime.of(10, 30)));
        flights.add(new Flight(freighter1, "FL002", 
            LocalTime.of(10, 15), LocalTime.of(10, 45)));
        flights.add(new Flight(drone1, "FL003", 
            LocalTime.of(11, 0), LocalTime.of(11, 15)));

        SchedulingProblem problem = new SchedulingProblem(airport, flights);
        try {
            Map<Flight, Runway> schedule = problem.solve();
            problem.printSchedule();
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}