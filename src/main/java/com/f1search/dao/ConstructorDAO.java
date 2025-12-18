package com.f1search.dao;

import com.f1search.config.DatabaseConfig;
import com.f1search.model.Constructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConstructorDAO {

    public List<Constructor> getAllConstructors() {
        List<Constructor> constructors = new ArrayList<>();
        String sql = "SELECT * FROM constructors";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                constructors.add(mapRowToConstructor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return constructors;
    }

    public List<Constructor> searchConstructorsByName(String keyword) {
        List<Constructor> constructors = new ArrayList<>();
        String sql = "SELECT * FROM constructors WHERE name LIKE ? OR constructorRef LIKE ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    constructors.add(mapRowToConstructor(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return constructors;
    }

    private Constructor mapRowToConstructor(ResultSet rs) throws SQLException {
        return new Constructor(
                rs.getInt("constructorId"),
                rs.getString("constructorRef"),
                rs.getString("name"),
                rs.getString("nationality"));
    }
}
