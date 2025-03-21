/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab3;

/**
 *
 * @author Seby
 */

public abstract class Aircraft implements Comparable<Aircraft> {
    private String model;
    private String callSign;

    public Aircraft(String model, String callSign) {
        this.model = model;
        this.callSign = callSign;
    }

    public String getModel() {
        return model;
    }

    public String getCallSign() {
        return callSign;
    }

    @Override
    public int compareTo(Aircraft other) {
        return this.callSign.compareTo(other.callSign);
    }

    @Override
    public String toString() {
        return "Aircraft [model=" + model + ", callSign=" + callSign + "]";
    }
}
