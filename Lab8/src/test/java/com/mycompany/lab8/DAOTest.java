/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Seby
 */
package com.mycompany.lab8;

import com.mycompany.lab8.dao.ContinentDAO;
import com.mycompany.lab8.dao.CountryDAO;
import com.mycompany.lab8.dao.DatabaseConnection;
import com.mycompany.lab8.model.Continent;
import com.mycompany.lab8.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DAOTest {
    @BeforeEach
    public void setUp() throws SQLException {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            PreparedStatement disableFKCities = conn.prepareStatement("ALTER TABLE cities DISABLE CONSTRAINT fk_cities_country");
            disableFKCities.executeUpdate();

            PreparedStatement disableFKCountries = conn.prepareStatement("ALTER TABLE countries DISABLE CONSTRAINT fk_countries_continent");
            disableFKCountries.executeUpdate();

            /// sterge datele din tabele (în ordinea corectă: cities -> countries -> continents)
            PreparedStatement deleteCities = conn.prepareStatement("DELETE FROM cities");
            deleteCities.executeUpdate();

            PreparedStatement deleteCountries = conn.prepareStatement("DELETE FROM countries");
            deleteCountries.executeUpdate();

            PreparedStatement deleteContinents = conn.prepareStatement("DELETE FROM continents");
            deleteContinents.executeUpdate();

            PreparedStatement enableFKCities = conn.prepareStatement("ALTER TABLE cities ENABLE CONSTRAINT fk_cities_country");
            enableFKCities.executeUpdate();

            PreparedStatement enableFKCountries = conn.prepareStatement("ALTER TABLE countries ENABLE CONSTRAINT fk_countries_continent");
            enableFKCountries.executeUpdate();

            resetSequence(conn, "cities_seq");
            resetSequence(conn, "countries_seq");
            resetSequence(conn, "continents_seq");
        }
    }

    private void resetSequence(Connection conn, String sequenceName) throws SQLException {
        PreparedStatement getCurrentVal = conn.prepareStatement("SELECT " + sequenceName + ".NEXTVAL FROM dual");
        ResultSet rs = getCurrentVal.executeQuery();
        rs.next();
        long currentVal = rs.getLong(1);

        long increment = -(currentVal - 1);

        PreparedStatement alterIncrement = conn.prepareStatement("ALTER SEQUENCE " + sequenceName + " INCREMENT BY " + increment);
        alterIncrement.executeUpdate();

        PreparedStatement getNextVal = conn.prepareStatement("SELECT " + sequenceName + ".NEXTVAL FROM dual");
        getNextVal.executeQuery();

        PreparedStatement resetIncrement = conn.prepareStatement("ALTER SEQUENCE " + sequenceName + " INCREMENT BY 1");
        resetIncrement.executeUpdate();
    }

    @Test
    public void testDAOs() throws SQLException {
        System.out.println("Incep testul...");
        // Test ContinentDAO
        ContinentDAO continentDAO = new ContinentDAO();
        System.out.println("Am creat ContinentDAO");
        Continent europe = new Continent(0, "Europe");
        System.out.println("Am creat continentul Europe");
        continentDAO.create(europe);
        System.out.println("Am inserat continentul Europe");
        europe = continentDAO.findByName("Europe");
        System.out.println("Found continent: " + europe);
        assertNotNull(europe, "Continent should be found");

        // Test CountryDAO
        CountryDAO countryDAO = new CountryDAO();
        System.out.println("Am creat CountryDAO");
        Country france = new Country(0, "France", "FR", europe);
        System.out.println("Am creat tara France");
        countryDAO.create(france);
        System.out.println("Am inserat tara France");
        france = countryDAO.findByName("France");
        System.out.println("Found country: " + france);
        assertNotNull(france, "Country should be found");
    }
}