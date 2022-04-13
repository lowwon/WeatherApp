package com.example.weatherproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AsyncTaskNetwork extends AsyncTask<String, JSONObject, Void> {

    Context context;
    TextView city,temp,main,sunRise,sunSet,feel,preesure,humidity;
    TextView visibility,  wind,  rain,  uvi,  may, day, time;
    public AsyncTaskNetwork(TextView city, TextView temp, TextView main, TextView sunRise, TextView sunSet, TextView feel_like, TextView preesure, TextView humidity, TextView visibility, TextView wind, TextView rain, TextView uvi, TextView may,TextView day, TextView time) {
        this.city = city;
        this.temp = temp;
        this.main = main;
        this.sunRise = sunRise;
        this.sunSet = sunSet;
        this.feel = feel_like;
        this.preesure = preesure;
        this.humidity = humidity;
        this.visibility = visibility;
        this.wind = wind;
        this.rain = rain;
        this.uvi = uvi;
        this.may = may;
        this.day = day;
        this.time= time;
    }
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(String... strings) {
        int b = 0;
        int resCode = 0;
        try {
            StringBuilder sb;
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setAllowUserInteraction(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("GET");
            connection.connect();
            JSONObject jsonObject;
            resCode = connection.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                jsonObject = MyJsonReader.readJsonFromUrl(strings[0]);
                publishProgress(jsonObject);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            String a = String.valueOf(b);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(JSONObject... values) {
        super.onProgressUpdate(values);
        JSONObject jsonObject = values[0];
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        long currentDateTime = 0;
        try{
            JSONObject current = jsonObject.getJSONObject("current");
            JSONArray weathernow = current.getJSONArray("weather");
            if(current.has("feels_like"))
            {
                this.feel.setText(String.valueOf(Math.round(current.getInt("feels_like") +1)) +"°");
            }
            if(current.has("dt")){
                currentDateTime = current.getLong("dt");
                Date currentDate = new Date(currentDateTime*1000);
                SimpleDateFormat day = new SimpleDateFormat("dd/MM/yyyy");
                String dayS = day.format(currentDate);
                String timeS = time.format(currentDate);
                this.day.setText(dayS);
                this.time.setText(timeS);
            }
            if(current.has("sunrise")){
                currentDateTime = current.getLong("sunrise");
                Date currentDate = new Date(currentDateTime*1000);
                String date = time.format(currentDate);
                this.sunRise.setText(date);
            }
            if(current.has("sunset")){
                currentDateTime = current.getLong("sunset");
                Date currentDate = new Date(currentDateTime*1000);
                String date = time.format(currentDate);
                this.sunSet.setText(date);
            }
            if(current.has("temp")){
                this.temp.setText(String.valueOf(Math.round(current.getInt("temp")+1)) +"°");
            }
            if(current.has("pressure")){
                this.preesure.setText(current.getString("pressure") + "pHa");
            }
            if(current.has("humidity")){
                this.humidity.setText(current.getString("humidity") + "%");
            }
            if(current.has("uvi")){
                this.uvi.setText(current.getString("uvi"));
            }
            if(current.has("visibility")){
                this.visibility.setText(current.getInt("visibility")/1000 + " km");
            }
            if(current.has("wind_speed")){
                this.wind.setText(current.getString("wind_speed")+" Km/h");
            }
            if(current.has("clouds")){
                this.may.setText(current.getString("clouds") + " %");
            }
            if(weathernow != null){
                JSONObject weather = weathernow.getJSONObject(0);
                this.main.setText(weather.getString("main"));
            }
            if(current.has("rain")){
                this.rain.setText(current.getInt("rain"));
            }
            else{
                this.rain.setText(String.valueOf(0));
            }
        } catch (JSONException e) {
            this.feel.setText("0");
            e.printStackTrace();
        }
    }

}
