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
        System.out.println("Manual Test Instance:");
        testManualInstance();

        System.out.println("\nRandom Test Instances:");
        testRandomInstances(5); // Generate and test 5 random instances
    }

    private static void testManualInstance() {
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
        Map<Flight, Runway> schedule = null;
        try {
            System.out.println("Trying equitable scheduling:");
            schedule = problem.solveEquitably();
            problem.printSchedule();
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            int minRunways = problem.calculateMinimumRunways();
            System.out.println("Minimum runways needed for equitable distribution: " + minRunways);

            System.out.println("\nTrying with interval adjustment:");
            problem = new SchedulingProblem(airport, flights); //reset
            schedule = problem.solveWithIntervalAdjustment();
            problem.printSchedule();
        }
    }

    private static void testRandomInstances(int numInstances) {
        Random random = new Random();
        List<Aircraft> aircraftList = generateRandomAircraft(10, random);

        for (int i = 1; i <= numInstances; i++) {
            System.out.println("\nRandom Instance " + i + ":");
            int numFlights = random.nextInt(10) + 5; // 5 to 14 flights
            int numRunways = random.nextInt(3) + 1;  // 1 to 3 runways

            Airport airport = new Airport(numRunways);
            Set<Flight> flights = generateRandomFlights(numFlights, aircraftList, random);

            SchedulingProblem problem = new SchedulingProblem(airport, flights);
            Map<Flight, Runway> schedule = null;
            try {
                System.out.println("Trying equitable scheduling with " + numRunways + " runways and " + numFlights + " flights:");
                schedule = problem.solveEquitably();
                problem.printSchedule();
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
                int minRunways = problem.calculateMinimumRunways();
                System.out.println("Minimum runways needed for equitable distribution: " + minRunways);

                System.out.println("\nTrying with interval adjustment:");
                problem = new SchedulingProblem(airport, flights); //reset
                schedule = problem.solveWithIntervalAdjustment();
                problem.printSchedule();
            }
        }
    }

    private static List<Aircraft> generateRandomAircraft(int count, Random random) {
        List<Aircraft> aircraftList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String callSign = "AC" + (100 + i);
            int type = random.nextInt(3);
            switch (type) {
                case 0:
                    aircraftList.add(new Airliner("Boeing " + callSign, callSign, 35.0 + random.nextDouble() * 10, 100 + random.nextInt(200)));
                    break;
                case 1:
                    aircraftList.add(new Freighter("Freight " + callSign, callSign, 5000 + random.nextDouble() * 10000));
                    break;
                case 2:
                    aircraftList.add(new Drone("Drone " + callSign, callSign, 30 + random.nextDouble() * 30, 200 + random.nextDouble() * 300));
                    break;
            }
        }
        return aircraftList;
    }

    private static Set<Flight> generateRandomFlights(int count, List<Aircraft> aircraftList, Random random) {
        Set<Flight> flights = new HashSet<>();
        for (int i = 0; i < count; i++) {
            Aircraft aircraft = aircraftList.get(random.nextInt(aircraftList.size()));
            String flightId = "FL" + String.format("%03d", i + 1);

            int startHour = 8 + random.nextInt(12);
            int startMinute = random.nextInt(60);
            LocalTime startTime = LocalTime.of(startHour, startMinute);

            int duration = 15 + random.nextInt(31);
            LocalTime endTime = startTime.plusMinutes(duration);

            flights.add(new Flight(aircraft, flightId, startTime, endTime));
        }
        return flights;
    }
}
