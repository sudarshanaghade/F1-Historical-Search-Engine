package com.f1search.model;

import java.time.LocalDate;

public class Driver {
    private int driverId;
    private String driverRef;
    private String code;
    private String forename;
    private String surname;
    private LocalDate dob;
    private String nationality;
    private String url;

    public Driver() {
    }

    public Driver(int driverId, String driverRef, String code, String forename, String surname, LocalDate dob,
            String nationality) {
        this.driverId = driverId;
        this.driverRef = driverRef;
        this.code = code;
        this.forename = forename;
        this.surname = surname;
        this.dob = dob;
        this.nationality = nationality;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getDriverRef() {
        return driverRef;
    }

    public void setDriverRef(String driverRef) {
        this.driverRef = driverRef;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return forename + " " + surname + " (" + nationality + ")";
    }
}
