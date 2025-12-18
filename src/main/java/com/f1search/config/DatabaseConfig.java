package com.f1search.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:sqlite:f1_history.db";

    public static Connection getConnection() throws SQLException {
        try {
            // Ensure the driver is registered
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC Driver not found", e);
        }
    }
}
