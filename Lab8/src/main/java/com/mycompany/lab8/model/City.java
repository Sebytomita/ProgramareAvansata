/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab8.model;

/**
 *
 * @author Seby
 */

public class City {
    private int id;
    private Country country;
    private String name;
    private boolean capital;
    private double latitude;
    private double longitude;

    public City(int id, Country country, String name, boolean capital, double latitude, double longitude) {
        this.id = id;
        this.country = country;
        this.name = name;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public Country getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public boolean isCapital() {
        return capital;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "City{id=" + id + ", country=" + country.getName() + ", name='" + name + "', capital=" + capital +
                ", latitude=" + latitude + ", longitude=" + longitude + "}";
    }
}
