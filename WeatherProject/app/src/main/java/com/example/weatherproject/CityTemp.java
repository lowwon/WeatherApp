package com.example.weatherproject;

public class CityTemp {
    private int id;
    private String name;
    private double lon;
    private double lat;

    public CityTemp(int id, String name, double lon, double lat) {
        this.setId(id);
        this.setName(name);
        this.setLon(lon);
        this.setLat(lat);
    }
    public CityTemp(String name, double lon, double lat) {
        this.setName(name);
        this.setLon(lon);
        this.setLat(lat);
    }
    public CityTemp() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
