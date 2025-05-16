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

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataImporter {

    public static void importData(String filePath) throws IOException, CsvValidationException {
        EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
        ContinentRepository continentRepository = new ContinentRepository(em);
        CountryRepository countryRepository = new CountryRepository(em);
        CityRepository cityRepository = new CityRepository(em);

        // --- Reset baze de date și secvențe ---
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            em.createNativeQuery("DELETE FROM cities").executeUpdate();
            em.createNativeQuery("DELETE FROM countries").executeUpdate();
            em.createNativeQuery("DELETE FROM continents").executeUpdate();

            dropSequenceSafe(em, "cities_seq");
            dropSequenceSafe(em, "countries_seq");
            dropSequenceSafe(em, "continents_seq");

            em.createNativeQuery("CREATE SEQUENCE cities_seq START WITH 1 INCREMENT BY 1").executeUpdate();
            em.createNativeQuery("CREATE SEQUENCE countries_seq START WITH 1 INCREMENT BY 1").executeUpdate();
            em.createNativeQuery("CREATE SEQUENCE continents_seq START WITH 1 INCREMENT BY 1").executeUpdate();

            tx.commit();
        } catch (Exception e) {
            LoggerUtil.logException(e);
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error clearing tables: " + e.getMessage(), e);
        }

        // --- Import din fișier CSV ---
        Map<String, Continent> continentCache = new HashMap<>();
        Map<String, Country> countryCache = new HashMap<>();

        try (Reader reader = new InputStreamReader(
                DataImporter.class.getClassLoader().getResourceAsStream(filePath));
             CSVReader csvReader = new CSVReader(reader)) {

            csvReader.readNext(); // skip header
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

            LoggerUtil.getLogger().info("Data import completed successfully.");

        } catch (Exception e) {
            LoggerUtil.logException(e);
            throw new RuntimeException("Error importing data: " + e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    private static void dropSequenceSafe(EntityManager em, String sequenceName) {
        try {
            em.createNativeQuery("DROP SEQUENCE " + sequenceName).executeUpdate();
        } catch (Exception e) {
            LoggerUtil.getLogger().warning("Could not drop sequence " + sequenceName + ": " + e.getMessage());
        }
    }
}
