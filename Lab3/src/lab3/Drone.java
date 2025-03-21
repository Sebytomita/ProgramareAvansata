/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3;

/**
 *
 * @author Seby
 */

public class Drone extends Aircraft implements CargoCapable {
    private double batteryLife;
    private double maxPayload;

    public Drone(String model, String callSign, double batteryLife, double maxPayload) {
        super(model, callSign);
        this.batteryLife = batteryLife;
        this.maxPayload = maxPayload;
    }

    @Override
    public double getMaxPayload() {
        return maxPayload;
    }

    @Override
    public void setMaxPayload(double payload) {
        this.maxPayload = payload;
    }

    @Override
    public String toString() {
        return "Drone [model=" + getModel() + ", callSign=" + getCallSign() + 
               ", batteryLife=" + batteryLife + ", maxPayload=" + maxPayload + "]";
    }
}