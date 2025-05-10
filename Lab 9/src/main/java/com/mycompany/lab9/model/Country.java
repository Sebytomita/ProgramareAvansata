/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab9.model;

/**
 *
 * @author Seby
 */

public class Country {
    private int id;
    private String name;
    private String code;
    private Continent continent;

    public Country(int id, String name, String code, Continent continent) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.continent = continent;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Continent getContinent() {
        return continent;
    }

    @Override
    public String toString() {
        return "Country{id=" + id + ", name='" + name + "', code='" + code + "', continent=" + continent + "}";
    }
}