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

import java.sql.*;

public class ContinentDAO {
    public void create(Continent continent) throws SQLException {
        String sql = "INSERT INTO continents (name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, continent.getName());
            stmt.executeUpdate();
            
            String idSql = "SELECT continents_seq.CURRVAL FROM dual";
            try (PreparedStatement idStmt = conn.prepareStatement(idSql);
                 ResultSet rs = idStmt.executeQuery()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    continent = new Continent(generatedId, continent.getName());
                }
            }
        }
    }

    public Continent findById(int id) throws SQLException {
        String sql = "SELECT * FROM continents WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Continent(rs.getInt("id"), rs.getString("name"));
            }
        }
        return null;
    }

    public Continent findByName(String name) throws SQLException {
        String sql = "SELECT * FROM continents WHERE name = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Continent(rs.getInt("id"), rs.getString("name"));
            }
        }
        return null;
    }
}