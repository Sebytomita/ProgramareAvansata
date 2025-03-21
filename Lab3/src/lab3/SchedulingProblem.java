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

class SchedulingProblem {
    private Airport airport;
    private Set<Flight> flights;
    private Map<Flight, Runway> schedule;

    public SchedulingProblem(Airport airport, Set<Flight> flights) {
        this.airport = airport;  
        this.flights = flights;
        this.schedule = new HashMap<>();
    }

    public Map<Flight, Runway> solve() {
        List<Flight> sortedFlights = new ArrayList<>(flights);
        sortedFlights.sort(Comparator.comparing(Flight::getStartTime));

        Map<Runway, Flight> runwayLastFlight = new HashMap<>();

        for (Flight flight : sortedFlights) {
            boolean scheduled = false;
            for (Runway runway : airport.getRunways()) {
                Flight lastFlight = runwayLastFlight.get(runway);
                if (lastFlight == null || !lastFlight.conflictsWith(flight)) {
                    schedule.put(flight, runway);
                    runwayLastFlight.put(runway, flight);
                    scheduled = true;
                    break;
                }
            }
            if (!scheduled) {
                throw new RuntimeException("Cannot schedule all flights!");
            }
        }
        return schedule;
    }

    public void printSchedule() {
        System.out.println("Flight Schedule:");
        for (Map.Entry<Flight, Runway> entry : schedule.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
