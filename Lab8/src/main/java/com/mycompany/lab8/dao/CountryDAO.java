/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab8.dao;

/**
 *
 * @author Seby
 */


import com.mycompany.lab8.model.Continent;
import com.mycompany.lab8.model.Country;

import java.sql.*;

public class CountryDAO {
    private ContinentDAO continentDAO;

    public CountryDAO() {
        this.continentDAO = new ContinentDAO();
    }

    public void create(Country country) throws SQLException {
        String sql = "INSERT INTO countries (name, code, continent_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, country.getName());
            stmt.setString(2, country.getCode());
            stmt.setInt(3, country.getContinent().getId());
            stmt.executeUpdate();
         
            String idSql = "SELECT countries_seq.CURRVAL FROM dual";
            try (PreparedStatement idStmt = conn.prepareStatement(idSql);
                 ResultSet rs = idStmt.executeQuery()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    country = new Country(generatedId, country.getName(), country.getCode(), country.getContinent());
                }
            }
        }
    }

    public Country findById(int id) throws SQLException {
        String sql = "SELECT c.id AS c_id, c.name AS c_name, c.code AS c_code, co.id AS co_id, co.name AS co_name " +
                     "FROM countries c JOIN continents co ON c.continent_id = co.id WHERE c.id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Continent continent = new Continent(rs.getInt("co_id"), rs.getString("co_name"));
                return new Country(rs.getInt("c_id"), rs.getString("c_name"), rs.getString("c_code"), continent);
            }
        }
        return null;
    }

    public Country findByName(String name) throws SQLException {
        String sql = "SELECT c.id AS c_id, c.name AS c_name, c.code AS c_code, co.id AS co_id, co.name AS co_name " +
                     "FROM countries c JOIN continents co ON c.continent_id = co.id WHERE c.name = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Continent continent = new Continent(rs.getInt("co_id"), rs.getString("co_name"));
                return new Country(rs.getInt("c_id"), rs.getString("c_name"), rs.getString("c_code"), continent);
            }
        }
        return null;
    }
}
