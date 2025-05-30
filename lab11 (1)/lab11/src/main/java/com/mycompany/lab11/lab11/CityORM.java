/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab11.lab11;

/**
 *
 * @author Seby
 */

import jakarta.persistence.*;

@Entity
public class CityORM {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_seq")
    @SequenceGenerator(name = "city_seq", sequenceName = "city_seq", allocationSize = 1)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    public CityORM() {}

    public CityORM(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CityORM{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}