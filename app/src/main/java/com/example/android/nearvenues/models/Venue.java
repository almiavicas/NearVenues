package com.example.android.nearvenues.models;

public class Venue {

    private String name;
    private int distance;
    private String address;

    public Venue() {

    }

    public Venue(String name, String address, int distance) {
        this.name = name;
        this.address = address;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
