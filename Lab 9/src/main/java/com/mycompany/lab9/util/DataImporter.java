/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab9.util;

/**
 *
 * @author Seby
 */

import com.mycompany.lab9.entity.Continent;
import com.mycompany.lab9.entity.Country;
import com.mycompany.lab9.entity.City;
import com.mycompany.lab9.repository.ContinentRepository;
import com.mycompany.lab9.repository.CountryRepository;
import com.mycompany.lab9.repository.CityRepository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataImporter {
    public static void importData(String filePath) throws IOException, CsvValidationException {
        Map<String, Continent> continentCache = new HashMap<>();
        Map<String, Country> countryCache = new HashMap<>();

        ContinentRepository continentRepository = new ContinentRepository();
        CountryRepository countryRepository = new CountryRepository();
        CityRepository cityRepository = new CityRepository();

        try {
            continentRepository.getEntityManager().getTransaction().begin();
            continentRepository.getEntityManager().createNativeQuery("DELETE FROM cities").executeUpdate();
            continentRepository.getEntityManager().createNativeQuery("DELETE FROM countries").executeUpdate();
            continentRepository.getEntityManager().createNativeQuery("DELETE FROM continents").executeUpdate();
            continentRepository.getEntityManager().createNativeQuery("DROP SEQUENCE cities_seq").executeUpdate();
            continentRepository.getEntityManager().createNativeQuery("CREATE SEQUENCE cities_seq START WITH 1 INCREMENT BY 1").executeUpdate();
            continentRepository.getEntityManager().createNativeQuery("DROP SEQUENCE countries_seq").executeUpdate();
            continentRepository.getEntityManager().createNativeQuery("CREATE SEQUENCE countries_seq START WITH 1 INCREMENT BY 1").executeUpdate();
            continentRepository.getEntityManager().createNativeQuery("DROP SEQUENCE continents_seq").executeUpdate();
            continentRepository.getEntityManager().createNativeQuery("CREATE SEQUENCE continents_seq START WITH 1 INCREMENT BY 1").executeUpdate();
            continentRepository.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            if (continentRepository.getEntityManager().getTransaction().isActive()) {
                continentRepository.getEntityManager().getTransaction().rollback();
            }
            throw new RuntimeException("Error clearing tables: " + e.getMessage(), e);
        }

        try (Reader reader = new InputStreamReader(DataImporter.class.getClassLoader().getResourceAsStream(filePath));
             CSVReader csvReader = new CSVReader(reader)) {
            csvReader.readNext(); 
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                String continentName = record[0]; 
                String countryName = record[1];   
                String countryCode = record[2];   
                String cityName = record[3];
                double latitude = Double.parseDouble(record[4]);
                double longitude = Double.parseDouble(record[5]);
      
                Continent continent = continentCache.computeIfAbsent(continentName, name -> {
                    List<Continent> existing = continentRepository.findByName(name);
                    if (existing.isEmpty()) {
                        Continent newContinent = new Continent(name);
                        continentRepository.create(newContinent);
                        return continentRepository.findByName(name).get(0);
                    }
                    return existing.get(0);
                });

                Country country = countryCache.computeIfAbsent(countryName, name -> {
                    List<Country> existing = countryRepository.findByName(name);
                    if (existing.isEmpty()) {
                        Country newCountry = new Country(name, countryCode, continent);
                        countryRepository.create(newCountry);
                        return countryRepository.findByName(name).get(0);
                    }
                    return existing.get(0);
                });

                City city = new City(country, cityName, true, latitude, longitude);
                cityRepository.create(city);
            }
        } finally {
            continentRepository.close();
            countryRepository.close();
            cityRepository.close();
        }
    }
}