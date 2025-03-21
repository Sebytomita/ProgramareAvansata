/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3;

/**
 *
 * @author Seby
 */

import java.util.*;
import java.time.LocalTime;

class SchedulingProblem {
    private Airport airport;
    private Set<Flight> flights;
    private Map<Flight, Runway> schedule;

    public SchedulingProblem(Airport airport, Set<Flight> flights) {
        this.airport = airport;
        this.flights = flights;
        this.schedule = new HashMap<>();
    }

    public Map<Flight, Runway> solveEquitably() {
        List<Flight> sortedFlights = new ArrayList<>(flights);
        sortedFlights.sort(Comparator.comparing(Flight::getStartTime));

        Map<Runway, Integer> runwayFlightCount = new HashMap<>();
        Map<Runway, Flight> runwayLastFlight = new HashMap<>();
        for (Runway runway : airport.getRunways()) {
            runwayFlightCount.put(runway, 0);
        }

        int numFlights = sortedFlights.size();
        int numRunways = airport.getRunways().size();
        int minFlightsPerRunway = numFlights / numRunways;
        int maxFlightsPerRunway = (numFlights % numRunways == 0) ? minFlightsPerRunway : minFlightsPerRunway + 1;

        for (Flight flight : sortedFlights) {
            Runway selectedRunway = null;
            int minFlights = Integer.MAX_VALUE;

            for (Runway runway : airport.getRunways()) {
                int currentCount = runwayFlightCount.get(runway);
                Flight lastFlight = runwayLastFlight.get(runway);

                if (currentCount < maxFlightsPerRunway && 
                    (lastFlight == null || !lastFlight.conflictsWith(flight))) {
                    if (currentCount < minFlights) {
                        selectedRunway = runway;
                        minFlights = currentCount;
                    }
                }
            }

            if (selectedRunway == null) {
                throw new RuntimeException("Cannot schedule equitably with " + numRunways + " runways");
            }

            schedule.put(flight, selectedRunway);
            runwayLastFlight.put(selectedRunway, flight);
            runwayFlightCount.put(selectedRunway, runwayFlightCount.get(selectedRunway) + 1);
        }

        return schedule;
    }

    public Map<Flight, Runway> solveWithIntervalAdjustment() {
        List<Flight> sortedFlights = new ArrayList<>(flights);
        sortedFlights.sort(Comparator.comparing(Flight::getStartTime));

        Map<Runway, Integer> runwayFlightCount = new HashMap<>();
        Map<Runway, Flight> runwayLastFlight = new HashMap<>();
        for (Runway runway : airport.getRunways()) {
            runwayFlightCount.put(runway, 0);
        }

        int numFlights = sortedFlights.size();
        int numRunways = airport.getRunways().size();
        int minFlightsPerRunway = numFlights / numRunways;
        int maxFlightsPerRunway = (numFlights % numRunways == 0) ? minFlightsPerRunway : minFlightsPerRunway + 1;

        for (Flight flight : sortedFlights) {
            Runway selectedRunway = null;
            int minFlights = Integer.MAX_VALUE;

            for (Runway runway : airport.getRunways()) {
                int currentCount = runwayFlightCount.get(runway);
                if (currentCount < maxFlightsPerRunway && currentCount < minFlights) {
                    selectedRunway = runway;
                    minFlights = currentCount;
                }
            }

            if (selectedRunway == null) {
                throw new RuntimeException("Cannot find a runway even with interval adjustment");
            }

            Flight lastFlight = runwayLastFlight.get(selectedRunway);
            if (lastFlight != null && lastFlight.conflictsWith(flight)) {
                LocalTime newStartTime = lastFlight.getEndTime();
                long durationMinutes = java.time.Duration.between(flight.getStartTime(), flight.getEndTime()).toMinutes();
                LocalTime newEndTime = newStartTime.plusMinutes(durationMinutes);
                flight.setStartTime(newStartTime);
                flight.setEndTime(newEndTime);
            }

            schedule.put(flight, selectedRunway);
            runwayLastFlight.put(selectedRunway, flight);
            runwayFlightCount.put(selectedRunway, runwayFlightCount.get(selectedRunway) + 1);
        }

        return schedule;
    }

    public int calculateMinimumRunways() {
        List<Flight> sortedFlights = new ArrayList<>(flights);
        sortedFlights.sort(Comparator.comparing(Flight::getStartTime));
        
        int maxOverlaps = 0;
        int currentOverlaps = 0;

        List<Map.Entry<LocalTime, Integer>> events = new ArrayList<>();
        for (Flight flight : sortedFlights) {
            events.add(new AbstractMap.SimpleEntry<>(flight.getStartTime(), 1));  
            events.add(new AbstractMap.SimpleEntry<>(flight.getEndTime(), -1));   
        }

        events.sort(Comparator.comparing(Map.Entry::getKey));

        for (Map.Entry<LocalTime, Integer> event : events) {
            currentOverlaps += event.getValue();
            maxOverlaps = Math.max(maxOverlaps, currentOverlaps);
        }

        int numFlights = flights.size();
        return (int) Math.ceil((double) numFlights / Math.max(1, numFlights / maxOverlaps));
    }

    public void printSchedule() {
        System.out.println("Flight Schedule:");
        for (Map.Entry<Flight, Runway> entry : schedule.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        Map<Runway, Integer> flightCount = new HashMap<>();
        for (Runway runway : airport.getRunways()) {
            flightCount.put(runway, 0);
        }
        for (Map.Entry<Flight, Runway> entry : schedule.entrySet()) {
            Runway runway = entry.getValue();
            flightCount.put(runway, flightCount.get(runway) + 1);
        }
        System.out.println("\nRunway Distribution:");
        for (Map.Entry<Runway, Integer> entry : flightCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " flights");
        }
    }
}
