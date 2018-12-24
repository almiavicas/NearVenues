package com.example.android.nearvenues;

public class Venue {

    private String name;
    private int distance;
    private String address;

    public Venue(String name, String address, int distance) {
        this.name = name;
        this.address = address;
        this.distance = distance;
    }
}
