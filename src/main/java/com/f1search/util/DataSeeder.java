package com.f1search.util;

import com.f1search.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataSeeder {

    public static void seedData() {
        try (Connection conn = DatabaseConfig.getConnection()) {
            if (isDataExists(conn)) {
                System.out.println("Data already exists. Skipping seed.");
                return;
            }

            System.out.println("Seeding mock data...");

            // Drivers
            insertDriver(conn, 1, "hamilton", "44", "Lewis", "Hamilton", "1985-01-07", "British");
            insertDriver(conn, 2, "schumacher", "MSC", "Michael", "Schumacher", "1969-01-03", "German");
            insertDriver(conn, 3, "max_verstappen", "33", "Max", "Verstappen", "1997-09-30", "Dutch");

            // Constructors
            insertConstructor(conn, 1, "mercedes", "Mercedes", "German");
            insertConstructor(conn, 2, "ferrari", "Ferrari", "Italian");
            insertConstructor(conn, 3, "red_bull", "Red Bull", "Austrian");

            // Results (Mock stats)
            // Hamilton: 3 wins (id=1)
            insertResult(conn, 1, 1, 1, 1);
            insertResult(conn, 1, 1, 1, 1);
            insertResult(conn, 1, 1, 1, 1);

            // Schumacher: 2 wins (id=2)
            insertResult(conn, 2, 2, 2, 1);
            insertResult(conn, 2, 2, 2, 1);
            // Schumacher: 1 podium (2nd place)
            insertResult(conn, 2, 2, 2, 2);

            System.out.println("Data seeding complete.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isDataExists(Connection conn) throws SQLException {
        try (java.sql.Statement stmt = conn.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM drivers")) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    private static void insertDriver(Connection conn, int id, String ref, String code, String forename, String surname,
            String dob, String nationality) throws SQLException {
        String sql = "INSERT OR IGNORE INTO drivers (driverId, driverRef, code, forename, surname, dob, nationality) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, ref);
            pstmt.setString(3, code);
            pstmt.setString(4, forename);
            pstmt.setString(5, surname);
            pstmt.setString(6, dob);
            pstmt.setString(7, nationality);
            pstmt.executeUpdate();
        }
    }

    private static void insertConstructor(Connection conn, int id, String ref, String name, String nationality)
            throws SQLException {
        String sql = "INSERT OR IGNORE INTO constructors (constructorId, constructorRef, name, nationality) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, ref);
            pstmt.setString(3, name);
            pstmt.setString(4, nationality);
            pstmt.executeUpdate();
        }
    }

    private static void insertResult(Connection conn, int driverId, int constructorId, int raceId, int position)
            throws SQLException {
        // Simple insert for stats calculation
        String sql = "INSERT INTO results (driverId, constructorId, raceId, position) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, driverId);
            pstmt.setInt(2, constructorId);
            pstmt.setInt(3, raceId); // Mock raceId
            pstmt.setInt(4, position);
            pstmt.executeUpdate();
        }
    }
}
