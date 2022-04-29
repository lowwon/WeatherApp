package com.example.weatherproject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    Context c;
    AlertDialog.Builder builder;
    dialog loadingdialog;
    ArrayList<WeatherTime> weatherTimes = new ArrayList<>();
    ArrayList<WeatherDay> weatherDays = new ArrayList<>();

    public AsyncTaskNetwork(Context backstackActivity, TextView city, TextView temp, TextView main, TextView sunRise, TextView sunSet, TextView feel_like, TextView preesure, TextView humidity, TextView visibility, TextView wind, TextView rain, TextView uvi, TextView may, TextView day, TextView time, ImageView iconMain, RecyclerView rv, RecyclerView lstday) {
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
        c = backstackActivity;
        builder = new AlertDialog.Builder(c);
        loadingdialog = new dialog((Activity) c);
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
            JSONObject jsonObject2;
            resCode = connection.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                jsonObject = MyJsonReader.readJsonFromUrl(strings[0]);
                jsonObject2 = MyJsonReader.readJsonFromUrl(strings[1]);
                publishProgress(jsonObject,jsonObject2);
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
        JSONObject jsonCity = values[1];
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        long currentDateTime = 0;
        try {
            JSONObject current = jsonObject.getJSONObject("current");
            JSONArray weathernow = current.getJSONArray("weather");
            JSONArray weatherTime = jsonObject.getJSONArray("hourly");
            JSONArray weatherDay = jsonObject.getJSONArray("daily");
            Date saveTime = null;
            if(jsonCity.has("name")){
                this.city.setText(jsonCity.getString("name"));
            }
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
                        break;
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
