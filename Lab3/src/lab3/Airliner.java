/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3;

/**
 *
 * @author Seby
 */
public class Airliner extends Aircraft implements PassengerCapable {
    private double wingSpan;
    private int passengerCapacity;

    public Airliner(String model, String callSign, double wingSpan, int passengerCapacity) {
        super(model, callSign);
        this.wingSpan = wingSpan;
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    @Override
    public void setPassengerCapacity(int capacity) {
        this.passengerCapacity = capacity;
    }

    @Override
    public String toString() {
        return "Airliner [model=" + getModel() + ", callSign=" + getCallSign() + 
               ", wingSpan=" + wingSpan + ", passengers=" + passengerCapacity + "]";
    }
}