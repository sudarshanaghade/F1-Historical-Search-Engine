package com.f1search.dao;

import com.f1search.config.DatabaseConfig;
import com.f1search.model.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class DriverDAO {

    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM drivers";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                drivers.add(mapRowToDriver(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    public List<Driver> searchDriversByName(String keyword) {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM drivers WHERE forename LIKE ? OR surname LIKE ? OR driverRef LIKE ?";

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    drivers.add(mapRowToDriver(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    private Driver mapRowToDriver(ResultSet rs) throws SQLException {
        String dobString = rs.getString("dob");
        LocalDate dob = (dobString != null && !dobString.isEmpty()) ? LocalDate.parse(dobString) : null;

        return new Driver(
                rs.getInt("driverId"),
                rs.getString("driverRef"),
                rs.getString("code"),
                rs.getString("forename"),
                rs.getString("surname"),
                dob,
                rs.getString("nationality"));
    }
}
