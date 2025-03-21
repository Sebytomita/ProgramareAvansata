/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3;

/**
 *
 * @author Seby
 */

public class Freighter extends Aircraft implements CargoCapable {
    private double maxPayload;

    public Freighter(String model, String callSign, double maxPayload) {
        super(model, callSign);
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
        return "Freighter [model=" + getModel() + ", callSign=" + getCallSign() + 
               ", maxPayload=" + maxPayload + "]";
    }
}