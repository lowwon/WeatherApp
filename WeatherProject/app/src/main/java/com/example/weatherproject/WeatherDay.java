package com.example.weatherproject;

public class WeatherDay {
    private String City;
    private int temperature;
    private String feel;
    private String TimeMTMoc;
    private String TimeMTLan;
    private String Humidity;
    private String Rain;
    private String Wind;
    private String tempFeel;
    private int NumberRain;
    private int UV;
    private int Pressure;
    private int Foresight;
    private WeatherTime[] time = new WeatherTime[6];

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel;
    }

    public String getTimeMTMoc() {
        return TimeMTMoc;
    }

    public void setTimeMTMoc(String timeMTMoc) {
        TimeMTMoc = timeMTMoc;
    }

    public String getTimeMTLan() {
        return TimeMTLan;
    }

    public void setTimeMTLan(String timeMTLan) {
        TimeMTLan = timeMTLan;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getRain() {
        return Rain;
    }

    public void setRain(String rain) {
        Rain = rain;
    }

    public String getWind() {
        return Wind;
    }

    public void setWind(String wind) {
        Wind = wind;
    }

    public String getTempFeel() {
        return tempFeel;
    }

    public void setTempFeel(String tempFeel) {
        this.tempFeel = tempFeel;
    }

    public int getNumberRain() {
        return NumberRain;
    }

    public void setNumberRain(int numberRain) {
        NumberRain = numberRain;
    }

    public int getUV() {
        return UV;
    }

    public void setUV(int UV) {
        this.UV = UV;
    }

    public int getPressure() {
        return Pressure;
    }

    public void setPressure(int pressure) {
        Pressure = pressure;
    }

    public int getForesight() {
        return Foresight;
    }

    public void setForesight(int foresight) {
        Foresight = foresight;
    }

    public WeatherTime[] getTime() {
        return time;
    }

    public void setTime(WeatherTime time, int i) {
        this.time[i] = time;
    }
}
