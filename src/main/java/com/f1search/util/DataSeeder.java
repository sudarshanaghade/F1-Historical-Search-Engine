package com.f1search.util;

import com.f1search.config.DatabaseConfig;
import com.f1search.service.ApiClient;
import com.f1search.service.ApiClient.ApiConstructor;
import com.f1search.service.ApiClient.ApiDriver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DataSeeder {

    public static void seedData() {
        ApiClient api = new ApiClient();

        try (Connection conn = DatabaseConfig.getConnection()) {
            if (isDataExists(conn)) {
                System.out.println("Data already exists. Skipping seed.");
                return;
            }

            System.out.println("Fetching Drivers from API...");
            List<ApiDriver> drivers = api.fetchAllDrivers();

            if (drivers.isEmpty()) {
                System.err.println("API returned no drivers. Falling back to MOCK data.");
                seedMockData(conn);
                return;
            }

            System.out.println("Fetched " + drivers.size() + " drivers. Inserting...");
            int count = 0;
            for (ApiDriver d : drivers) {
                insertDriver(conn, ++count, d.id, d.code, d.forename, d.surname, d.dob, d.nationality);
            }

            System.out.println("Fetching Constructors from API...");
            List<ApiConstructor> constructors = api.fetchAllConstructors();
            System.out.println("Fetched " + constructors.size() + " constructors. Inserting...");

            count = 0;
            for (ApiConstructor c : constructors) {
                insertConstructor(conn, ++count, c.id, c.name, c.nationality);
            }

            // Note: Fetching ALL historical results is heavy (thousands of requests).
            // For this demo, we will insert some sample stats for top drivers to ensure
            // ranking works.
            // In a full production app, we would batch fetch race results.
            seedMockStats(conn);

            System.out.println("Data seeding complete.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void seedMockData(Connection conn) throws SQLException {
        // Drivers
        insertDriver(conn, 1, "hamilton", "44", "Lewis", "Hamilton", "1985-01-07", "British");
        insertDriver(conn, 2, "schumacher", "MSC", "Michael", "Schumacher", "1969-01-03", "German");
        insertDriver(conn, 3, "max_verstappen", "33", "Max", "Verstappen", "1997-09-30", "Dutch");

        // Constructors
        insertConstructor(conn, 1, "mercedes", "Mercedes", "German");
        insertConstructor(conn, 2, "ferrari", "Ferrari", "Italian");
        insertConstructor(conn, 3, "red_bull", "Red Bull", "Austrian");

        // Results
        seedMockStats(conn);
    }

    private static void seedMockStats(Connection conn) throws SQLException {
        // Hamilton (id found by knowing he's in the list, usually 1 or close)
        // We act blind here and just insert for known refs if we had time to query IDs
        // back.
        // For simplicity, we'll just add some generic wins to ensure the ranking engine
        // isn't empty.
        // Ideally we fetch 'results.json' per driver.

        // This is a tradeoff: Real list of names, but simulated stats for performance
        // in this demo.
        // Just adding a few dummy results for search demonstration.
        // Assuming IDs are sequential from 1 as inserted above.

        insertResult(conn, 1, 1, 1, 1); // Mock win for first driver
        insertResult(conn, 1, 1, 1, 1);
        insertResult(conn, 2, 2, 2, 1); // Mock win for second driver
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
        String sql = "INSERT INTO results (driverId, constructorId, raceId, position) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, driverId);
            pstmt.setInt(2, constructorId);
            pstmt.setInt(3, raceId);
            pstmt.setInt(4, position);
            pstmt.executeUpdate();
        }
    }
}
