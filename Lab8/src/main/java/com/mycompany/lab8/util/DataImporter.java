/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab8.util;

/**
 *
 * @author Seby
 */

import com.mycompany.lab8.dao.CityDAO;
import com.mycompany.lab8.dao.ContinentDAO;
import com.mycompany.lab8.dao.CountryDAO;
import com.mycompany.lab8.model.City;
import com.mycompany.lab8.model.Continent;
import com.mycompany.lab8.model.Country;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class DataImporter {
    public void importData(String filePath) throws SQLException, IOException, CsvValidationException {
        ContinentDAO continentDAO = new ContinentDAO();
        CountryDAO countryDAO = new CountryDAO();
        CityDAO cityDAO = new CityDAO();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            csvReader.readNext(); 
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                if (record.length != 6) {
                    System.err.println("Linie invalida în CSV, se ignora: " + String.join(",", record));
                    continue; 
                }

                String continentName = record[0];
                String countryName = record[1];
                String countryCode = record[2];
                String capitalName = record[3];
                double latitude;
                double longitude;

                try {
                    latitude = Double.parseDouble(record[4]);
                    longitude = Double.parseDouble(record[5]);
                } catch (NumberFormatException e) {
                    System.err.println("Eroare la parsarea coordonatelor pentru linia: " + String.join(",", record));
                    continue;
                }

                Continent continent = continentDAO.findByName(continentName);
                if (continent == null) {
                    continent = new Continent(0, continentName);
                    continentDAO.create(continent);
                    continent = continentDAO.findByName(continentName);
                }

                Country country = countryDAO.findByName(countryName);
                if (country == null) {
                    country = new Country(0, countryName, countryCode, continent);
                    countryDAO.create(country);
                    country = countryDAO.findByName(countryName);
                }

                City capital = cityDAO.findByName(capitalName);
                if (capital == null) {
                    capital = new City(0, country, capitalName, true, latitude, longitude);
                    cityDAO.create(capital);
                }
            }
        }
    }
}