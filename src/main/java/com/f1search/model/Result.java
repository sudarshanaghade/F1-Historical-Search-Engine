package com.f1search.model;

public class Result {
    private int resultId;
    private int raceId;
    private int driverId;
    private int constructorId;
    private int grid;
    private Integer position; // Can be null if DNF
    private float points;
    private boolean winner;

    public Result() {
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getConstructorId() {
        return constructorId;
    }

    public void setConstructorId(int constructorId) {
        this.constructorId = constructorId;
    }

    public int getGrid() {
        return grid;
    }

    public void setGrid(int grid) {
        this.grid = grid;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
