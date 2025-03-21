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

class Flight {
    private Aircraft aircraft;
    private String flightId;
    private LocalTime startTime;
    private LocalTime endTime;

    public Flight(Aircraft aircraft, String flightId, LocalTime startTime, LocalTime endTime) {
        this.aircraft = aircraft;
        this.flightId = flightId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public String getFlightId() {
        return flightId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean conflictsWith(Flight other) {
        return this.startTime.isBefore(other.endTime) && 
               other.startTime.isBefore(this.endTime);
    }

    @Override
    public String toString() {
        return "Flight " + flightId + " [" + startTime + " - " + endTime + "] " + 
               aircraft.getCallSign();
    }
}