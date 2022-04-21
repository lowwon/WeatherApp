package com.example.weatherproject;

public class WeatherDay {
    private String thu;
    private String icon;
    private int maxTemp;
    private int avgTemp;
    private int minTemp;
    public WeatherDay(String thu, String icon, int maxTemp, int avgTemp,int minTemp ){
        this.thu = thu;
        this.icon = icon;
        this.maxTemp = maxTemp;
        this.avgTemp = avgTemp;
        this.minTemp = minTemp;
    }
    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(int avgTemp) {
        this.avgTemp = avgTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }
}
