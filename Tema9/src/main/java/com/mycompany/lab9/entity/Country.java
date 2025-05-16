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
@Table(name = "countries")
@NamedQuery(name = "Country.findByName", query = "SELECT c FROM Country c WHERE c.name LIKE :namePattern")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countries_seq")
    @SequenceGenerator(name = "countries_seq", sequenceName = "countries_seq", allocationSize = 1)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "continent_id")
    private Continent continent;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<City> cities;

    public Country() {}

    public Country(String name, String code, Continent continent) {
        this.name = name;
        this.code = code;
        this.continent = continent;
    }

    public Country(String name, String code, Continent continent, List<City> cities) {
        this.name = name;
        this.code = code;
        this.continent = continent;
        this.cities = cities;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", continent=" + (continent != null ? continent.getName() : "null") +
                ", cities=" + (cities != null ? cities.size() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;
        Country country = (Country) o;
        return id == country.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
