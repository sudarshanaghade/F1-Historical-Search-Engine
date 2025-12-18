package com.f1search.dao;

import com.f1search.config.DatabaseConfig;
import com.f1search.model.Race;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RaceDAO {

    public List<Race> getRacesByYear(int year) {
        List<Race> races = new ArrayList<>();
        String sql = "SELECT * FROM races WHERE year = ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, year);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    races.add(mapRowToRace(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return races;
    }

    public List<Race> searchRacesByName(String keyword) {
        List<Race> races = new ArrayList<>();
        String sql = "SELECT * FROM races WHERE name LIKE ? OR circuitName LIKE ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    races.add(mapRowToRace(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return races;
    }

    private Race mapRowToRace(ResultSet rs) throws SQLException {
        String dateString = rs.getString("date");
        LocalDate date = (dateString != null && !dateString.isEmpty()) ? LocalDate.parse(dateString) : null;

        return new Race(
                rs.getInt("raceId"),
                rs.getInt("year"),
                rs.getInt("round"),
                rs.getString("name"),
                date,
                rs.getString("circuitName"));
    }
}
