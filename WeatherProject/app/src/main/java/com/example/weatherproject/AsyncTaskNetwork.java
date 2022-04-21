package com.example.weatherproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;
import java.util.Date;

public class AsyncTaskNetwork extends AsyncTask<String, JSONObject, Void> {
    TextView city,temp,main,sunRise,sunSet,feel,preesure,humidity;
    TextView visibility,  wind,  rain,  uvi,  may, day, time;
    RecyclerView rv,listDay;
    ImageView iconMain;
    FragmentActivity c;
    dialog loadingdialog;
    ArrayList<WeatherTime> weatherTimes = new ArrayList<>();
    ArrayList<WeatherDay> weatherDays = new ArrayList<>();
    public AsyncTaskNetwork(FragmentActivity c, TextView city, TextView temp, TextView main, TextView sunRise, TextView sunSet, TextView feel_like, TextView preesure, TextView humidity, TextView visibility, TextView wind, TextView rain, TextView uvi, TextView may, TextView day, TextView time, ImageView iconMain, RecyclerView rv, RecyclerView lstday) {
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
        this.rv = rv;
        this.iconMain = iconMain;
        this.listDay = lstday;
        c = c;
        loadingdialog = new dialog(c);
    }
    protected void onPreExecute() {
        super.onPreExecute();
        loadingdialog.startLoadingdialog();
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
        try {
            JSONObject current = jsonObject.getJSONObject("current");
            JSONArray weathernow = current.getJSONArray("weather");
            JSONArray weatherTime = jsonObject.getJSONArray("hourly");
            JSONArray weatherDay = jsonObject.getJSONArray("daily");
            Date saveTime = null;
            if (current.has("feels_like")) {
                this.feel.setText(String.valueOf(Math.round(current.getInt("feels_like") + 1)) + "°");
            }
            if (current.has("dt")) {
                currentDateTime = current.getLong("dt");
                saveTime = new Date(currentDateTime * 1000);
                SimpleDateFormat day = new SimpleDateFormat("dd/MM/yyyy");
                String dayS = day.format(saveTime);
                String timeS = time.format(saveTime);
                this.day.setText(dayS);
                this.time.setText(timeS);
            }
            if (current.has("sunrise")) {
                currentDateTime = current.getLong("sunrise");
                Date currentDate = new Date(currentDateTime * 1000);
                String date = time.format(currentDate);
                this.sunRise.setText(date);
            }
            if (current.has("sunset")) {
                currentDateTime = current.getLong("sunset");
                Date currentDate = new Date(currentDateTime * 1000);
                String date = time.format(currentDate);
                this.sunSet.setText(date);
            }
            if (current.has("temp")) {
                this.temp.setText(String.valueOf(Math.round(current.getInt("temp") + 1)) + "°");
            }
            if (current.has("pressure")) {
                this.preesure.setText(current.getString("pressure") + "pHa");
            }
            if (current.has("humidity")) {
                this.humidity.setText(current.getString("humidity") + "%");
            }
            if (current.has("uvi")) {
                this.uvi.setText(current.getString("uvi"));
            }
            if (current.has("visibility")) {
                this.visibility.setText(current.getInt("visibility") / 1000 + " km");
            }
            if (current.has("wind_speed")) {
                this.wind.setText(current.getString("wind_speed") + " Km/h");
            }
            if (current.has("clouds")) {
                this.may.setText(current.getString("clouds") + " %");
            }
            if (weathernow != null) {
                JSONObject weather = weathernow.getJSONObject(0);
                String main = weather.getString("main");
                String mainT = weather.getString("icon");
                String timeS = time.format(saveTime);
                int hour = Integer.parseInt(timeS.substring(0, 2));
                if (hour >= 6 && hour < 18) {
                    switch (mainT) {
                        case "01d":
                            this.iconMain.setImageResource(R.drawable.a01d);
                            break;
                        case "02d":
                            this.iconMain.setImageResource(R.drawable.a02d);
                            break;
                        case "03d":
                            this.iconMain.setImageResource(R.drawable.a03d);
                            break;
                        case "04d":
                            this.iconMain.setImageResource(R.drawable.a04d);
                            break;
                        case "09d":
                            this.iconMain.setImageResource(R.drawable.a09d);
                            break;
                        case "10d":
                            this.iconMain.setImageResource(R.drawable.a10d);
                            break;
                        case "11d":
                            this.iconMain.setImageResource(R.drawable.a11d);
                            break;
                        case "13d":
                            this.iconMain.setImageResource(R.drawable.a13d);
                            break;
                        case "50d":
                            this.iconMain.setImageResource(R.drawable.a50d);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + mainT);
                    }
                }
                else {
                    switch (mainT) {
                        case "01n":
                            this.iconMain.setImageResource(R.drawable.a01n);
                            break;
                        case "02n":
                            this.iconMain.setImageResource(R.drawable.a02n);
                            break;
                        case "03n":
                            this.iconMain.setImageResource(R.drawable.a03n);
                            break;
                        case "04n":
                            this.iconMain.setImageResource(R.drawable.a04n);
                            break;
                        case "09n":
                            this.iconMain.setImageResource(R.drawable.a09n);
                            break;
                        case "10n":
                            this.iconMain.setImageResource(R.drawable.a10n);
                            break;
                        case "11n":
                            this.iconMain.setImageResource(R.drawable.a11n);
                            break;
                        case "13n":
                            this.iconMain.setImageResource(R.drawable.a13n);
                            break;
                        case "50n":
                            this.iconMain.setImageResource(R.drawable.a50n);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + mainT);
                    }
                }
                this.main.setText(main);
            }
            if (weatherTime != null) {
                JSONArray Awt;
                JSONObject wt;
                String timeA, iconA;
                int tempA;
                for (int i = 0; i < 24; i++) {
                    JSONObject weather = weatherTime.getJSONObject(i);
                    Awt = weather.getJSONArray("weather");
                    wt = Awt.getJSONObject(0);
                    timeA = weather.getString("dt");
                    iconA = wt.getString("icon");
                    tempA = weather.getInt("temp");
                    WeatherTime wt1 = new WeatherTime(timeA, iconA, tempA);
                    weatherTimes.add(wt1);
                }
                WeatherAdapter adapter = new WeatherAdapter(weatherTimes);
                rv.setAdapter(adapter);
            }
            if (current.has("rain")) {
                this.rain.setText(current.getInt("rain"));
            }
            else {
                this.rain.setText(String.valueOf(0));
            }
            if(weatherDay != null){
                JSONObject weather;
                JSONObject temp;
                JSONArray wt;
                JSONObject wtInfo;
                String thu, icon;
                int max, avg, min;
                for(int i = 0; i < weatherDay.length(); i++){
                    weather = weatherDay.getJSONObject(i);
                    temp = weather.getJSONObject("temp");
                    wt = weather.getJSONArray("weather");
                    wtInfo = wt.getJSONObject(0);
                    thu = weather.getString("dt");
                    icon = wtInfo.getString("icon");
                    max = temp.getInt("max");
                    min = temp.getInt("min");
                    avg = temp.getInt("day");
                    WeatherDay weatherDay1 = new WeatherDay(thu, icon, max, avg, min);
                    weatherDays.add(weatherDay1);
                }
                WeatherDayAdapter weatherDayAdapter = new WeatherDayAdapter(weatherDays);
                listDay.setAdapter(weatherDayAdapter);
            }
            } catch(JSONException e){
                this.feel.setText("0");
                e.printStackTrace();
            }
        loadingdialog.dismissdialog();
    }
    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
    }
}
