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
import java.util.List;

@Entity
@Table(name = "continents")
@NamedQuery(name = "Continent.findByName", query = "SELECT c FROM Continent c WHERE c.name LIKE :namePattern")
public class Continent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "continents_seq")
    @SequenceGenerator(name = "continents_seq", sequenceName = "continents_seq", allocationSize = 1)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "continent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Country> countries;

    public Continent() {}

    public Continent(String name) {
        this.name = name;
    }

    public Continent(String name, List<Country> countries) {
        this.name = name;
        this.countries = countries;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    @Override
    public String toString() {
        return "Continent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countries=" + (countries != null ? countries.size() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Continent)) return false;
        Continent that = (Continent) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
