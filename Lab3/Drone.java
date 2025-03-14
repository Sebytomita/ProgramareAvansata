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

    public Drone(String model, String callSign, double batteryLife) {
        super(model, callSign);
        this.batteryLife = batteryLife;
    }

    @Override
    public double getMaxPayload() {
       return maxPayload;
    }

    @Override
    public String toString() {
        return "Drone [model=" + getModel() + ", callSign=" + getCallSign() + 
               ", batteryLife=" + batteryLife + "]";
    }
}
