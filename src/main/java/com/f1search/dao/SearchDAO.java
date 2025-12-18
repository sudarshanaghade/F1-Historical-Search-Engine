package com.f1search.dao;

import com.f1search.config.DatabaseConfig;
import com.f1search.model.Driver;
import com.f1search.model.Constructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchDAO {

    // Helper class to store search results with metadata
    public static class SearchResult {
        public String type; // "Driver" or "Constructor"
        public String name;
        public int id;
        public int wins;
        public int podiums;
        public int championships; // This might need complex calculation, for now we will approximate or query
                                  // separate table

        public SearchResult(String type, String name, int id, int wins, int podiums) {
            this.type = type;
            this.name = name;
            this.id = id;
            this.wins = wins;
            this.podiums = podiums;
            this.championships = 0; // Placeholder
        }
    }

    public List<SearchResult> searchDriversWithStats(String keyword) {
        List<SearchResult> results = new ArrayList<>();
        String sql = "SELECT d.driverId, d.forename, d.surname, " +
                "COUNT(CASE WHEN r.position = 1 THEN 1 END) as wins, " +
                "COUNT(CASE WHEN r.position <= 3 THEN 1 END) as podiums " +
                "FROM drivers d " +
                "LEFT JOIN results r ON d.driverId = r.driverId " +
                "WHERE d.forename LIKE ? OR d.surname LIKE ? OR d.driverRef LIKE ? " +
                "GROUP BY d.driverId";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String fullName = rs.getString("forename") + " " + rs.getString("surname");
                    results.add(new SearchResult(
                            "Driver",
                            fullName,
                            rs.getInt("driverId"),
                            rs.getInt("wins"),
                            rs.getInt("podiums")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<SearchResult> searchConstructorsWithStats(String keyword) {
        List<SearchResult> results = new ArrayList<>();
        String sql = "SELECT c.constructorId, c.name, " +
                "COUNT(CASE WHEN r.position = 1 THEN 1 END) as wins, " +
                "COUNT(CASE WHEN r.position <= 3 THEN 1 END) as podiums " +
                "FROM constructors c " +
                "LEFT JOIN results r ON c.constructorId = r.constructorId " +
                "WHERE c.name LIKE ? OR c.constructorRef LIKE ? " +
                "GROUP BY c.constructorId";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new SearchResult(
                            "Constructor",
                            rs.getString("name"),
                            rs.getInt("constructorId"),
                            rs.getInt("wins"),
                            rs.getInt("podiums")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
