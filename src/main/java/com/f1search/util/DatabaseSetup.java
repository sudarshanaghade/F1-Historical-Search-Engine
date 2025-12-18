package com.f1search.util;

import com.f1search.config.DatabaseConfig;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseSetup {

    public static void initializeDatabase() {
        try (Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement()) {

            String schema = loadSchemaParams();
            String[] statements = schema.split(";");

            for (String sql : statements) {
                if (!sql.trim().isEmpty()) {
                    stmt.execute(sql);
                }
            }
            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to initialize database: " + e.getMessage());
        }
    }

    private static String loadSchemaParams() {
        try (InputStream is = DatabaseSetup.class.getClassLoader().getResourceAsStream("schema.sql")) {
            if (is == null) {
                throw new RuntimeException("schema.sql not found");
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading schema.sql", e);
        }
    }
}
