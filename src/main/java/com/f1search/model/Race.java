package com.f1search.model;

import java.time.LocalDate;

public class Race {
    private int raceId;
    private int year;
    private int round;
    private String name;
    private LocalDate date;
    private String circuitName;

    public Race() {}

    public Race(int raceId, int year, int round, String name, LocalDate date, String circuitName) {
        this.raceId = raceId;
        this.year = year;
        this.round = round;
        this.name = name;
        this.date = date;
        this.circuitName = circuitName;
    }

    public int getRaceId() { return raceId; }
    public void setRaceId(int raceId) { this.raceId = raceId; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getRound() { return round; }
    public void setRound(int round) { this.round = round; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getCircuitName() { return circuitName; }
    public void setCircuitName(String circuitName) { this.circuitName = circuitName; }
}
