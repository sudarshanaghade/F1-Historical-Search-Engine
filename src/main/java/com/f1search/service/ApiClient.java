package com.f1search.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiClient {
    private static final String DRIVERS_URL = "https://ergast.com/api/f1/drivers.json?limit=1000";
    private static final String CONSTRUCTORS_URL = "https://ergast.com/api/f1/constructors.json?limit=1000";

    public List<ApiDriver> fetchAllDrivers() {
        List<ApiDriver> drivers = new ArrayList<>();
        try {
            String jsonResponse = get(DRIVERS_URL);
            JsonObject root = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray driverTable = root.getAsJsonObject("MRData")
                    .getAsJsonObject("DriverTable")
                    .getAsJsonArray("Drivers");

            for (JsonElement el : driverTable) {
                JsonObject obj = el.getAsJsonObject();
                drivers.add(new ApiDriver(
                        obj.get("driverId").getAsString(),
                        obj.has("code") ? obj.get("code").getAsString() : null,
                        obj.get("givenName").getAsString(),
                        obj.get("familyName").getAsString(),
                        obj.get("dateOfBirth").getAsString(),
                        obj.get("nationality").getAsString()));
            }
        } catch (Exception e) {
            System.err.println("Failed to fetch drivers: " + e.getMessage());
        }
        return drivers;
    }

    public List<ApiConstructor> fetchAllConstructors() {
        List<ApiConstructor> constructors = new ArrayList<>();
        try {
            String jsonResponse = get(CONSTRUCTORS_URL);
            JsonObject root = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray constructorTable = root.getAsJsonObject("MRData")
                    .getAsJsonObject("ConstructorTable")
                    .getAsJsonArray("Constructors");

            for (JsonElement el : constructorTable) {
                JsonObject obj = el.getAsJsonObject();
                constructors.add(new ApiConstructor(
                        obj.get("constructorId").getAsString(),
                        obj.get("name").getAsString(),
                        obj.get("nationality").getAsString()));
            }
        } catch (Exception e) {
            System.err.println("Failed to fetch constructors: " + e.getMessage());
        }
        return constructors;
    }

    private String get(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }

    // Inner DTOs
    public static class ApiDriver {
        public String id;
        public String code;
        public String forename;
        public String surname;
        public String dob;
        public String nationality;

        public ApiDriver(String id, String code, String forename, String surname, String dob, String nationality) {
            this.id = id;
            this.code = code;
            this.forename = forename;
            this.surname = surname;
            this.dob = dob;
            this.nationality = nationality;
        }
    }

    public static class ApiConstructor {
        public String id;
        public String name;
        public String nationality;

        public ApiConstructor(String id, String name, String nationality) {
            this.id = id;
            this.name = name;
            this.nationality = nationality;
        }
    }
}
