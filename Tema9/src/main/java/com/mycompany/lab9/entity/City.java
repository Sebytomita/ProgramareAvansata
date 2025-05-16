/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab9.entity;

/**
 *
 * @author Seby
 */

import javax.persistence.*;

@Entity
@Table(name = "cities")
@NamedQuery(name = "City.findByName", query = "SELECT c FROM City c WHERE c.name LIKE :namePattern")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cities_seq")
    @SequenceGenerator(name = "cities_seq", sequenceName = "cities_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "capital", nullable = false)
    private boolean capital;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    public City() {}

    public City(Country country, String name, boolean capital, double latitude, double longitude) {
        this.country = country;
        this.name = name;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCapital() {
        return capital;
    }

    public void setCapital(boolean capital) {
        this.capital = capital;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capital=" + capital +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", country=" + (country != null ? country.getName() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return id == city.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
