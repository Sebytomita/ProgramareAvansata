/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab8.dao;

/**
 *
 * @author Seby
 */

import com.mycompany.lab8.model.City;
import com.mycompany.lab8.model.Continent;
import com.mycompany.lab8.model.Country;

import java.sql.*;

public class CityDAO {
    private CountryDAO countryDAO;

    public CityDAO() {
        this.countryDAO = new CountryDAO();
    }

    public void create(City city) throws SQLException {
        String sql = "INSERT INTO cities (country_id, name, capital, latitude, longitude) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, city.getCountry().getId());
            stmt.setString(2, city.getName());
            stmt.setInt(3, city.isCapital() ? 1 : 0);
            stmt.setDouble(4, city.getLatitude());
            stmt.setDouble(5, city.getLongitude());
            stmt.executeUpdate();
            
            String idSql = "SELECT cities_seq.CURRVAL FROM dual";
            try (PreparedStatement idStmt = conn.prepareStatement(idSql);
                 ResultSet rs = idStmt.executeQuery()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    city = new City(generatedId, city.getCountry(), city.getName(), city.isCapital(),
                            city.getLatitude(), city.getLongitude());
                }
            }
        }
    }

    public City findById(int id) throws SQLException {
        String sql = "SELECT ci.id AS ci_id, ci.name AS ci_name, ci.capital AS ci_capital, ci.latitude AS ci_latitude, " +
                     "ci.longitude AS ci_longitude, co.id AS co_id, co.name AS co_name, " +
                     "con.id AS con_id, con.name AS con_name " +
                     "FROM cities ci JOIN countries co ON ci.country_id = co.id " +
                     "JOIN continents con ON co.continent_id = con.id WHERE ci.id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Continent continent = new Continent(rs.getInt("con_id"), rs.getString("con_name"));
                Country country = new Country(rs.getInt("co_id"), rs.getString("co_name"),
                        rs.getString("co_code"), continent);
                return new City(rs.getInt("ci_id"), country, rs.getString("ci_name"),
                        rs.getInt("ci_capital") == 1, rs.getDouble("ci_latitude"), rs.getDouble("ci_longitude"));
            }
        }
        return null;
    }

    public City findByName(String name) throws SQLException {
        String sql = "SELECT ci.id AS ci_id, ci.name AS ci_name, ci.capital AS ci_capital, ci.latitude AS ci_latitude, " +
                     "ci.longitude AS ci_longitude, co.id AS co_id, co.name AS co_name, co.code AS co_code, " +
                     "con.id AS con_id, con.name AS con_name " +
                     "FROM cities ci JOIN countries co ON ci.country_id = co.id " +
                     "JOIN continents con ON co.continent_id = con.id WHERE ci.name = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Continent continent = new Continent(rs.getInt("con_id"), rs.getString("con_name"));
                Country country = new Country(rs.getInt("co_id"), rs.getString("co_name"),
                        rs.getString("co_code"), continent);
                return new City(rs.getInt("ci_id"), country, rs.getString("ci_name"),
                        rs.getInt("ci_capital") == 1, rs.getDouble("ci_latitude"), rs.getDouble("ci_longitude"));
            }
        }
        return null;
    }
}