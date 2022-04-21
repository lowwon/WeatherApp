package com.example.weatherproject;

import java.util.ArrayList;

public class WeatherTime {
    private int temp;
    private String time;
    private String icon;
    public WeatherTime(String time, String icon, int temp){
        this.time = time;
        this.icon = icon;
        this.temp = temp;
    }
    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
