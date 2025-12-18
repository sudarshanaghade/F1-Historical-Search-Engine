package com.f1search.model;

public class Constructor {
    private int constructorId;
    private String constructorRef;
    private String name;
    private String nationality;

    public Constructor() {
    }

    public Constructor(int constructorId, String constructorRef, String name, String nationality) {
        this.constructorId = constructorId;
        this.constructorRef = constructorRef;
        this.name = name;
        this.nationality = nationality;
    }

    public int getConstructorId() {
        return constructorId;
    }

    public void setConstructorId(int constructorId) {
        this.constructorId = constructorId;
    }

    public String getConstructorRef() {
        return constructorRef;
    }

    public void setConstructorRef(String constructorRef) {
        this.constructorRef = constructorRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
