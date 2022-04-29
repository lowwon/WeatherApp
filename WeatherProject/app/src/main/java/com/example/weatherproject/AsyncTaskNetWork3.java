package com.example.weatherproject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

    public class AsyncTaskNetWork3 extends AsyncTask<String, String, Void> {
        ListView lstView;
        String key = "https://api.openweathermap.org/data/2.5/onecall";
        String API_KEY = "&appid=bc40dd0b42c7e289bb7c950d62fb64e4&units=metric";
        dialog loadingdialog;
        List<String> time = new ArrayList<>();
        List<String> avg = new ArrayList<>();
        List<String> main = new ArrayList<>();
        List<String> min = new ArrayList<>();
        List<String> max = new ArrayList<>();
        List<String> night = new ArrayList<>();
        List<String> morn = new ArrayList<>();
        List<String> even = new ArrayList<>();
        Activity contextParent;
        public AsyncTaskNetWork3(InfoCity infoCity, ListView listViewI) {
            contextParent = infoCity;
            this.lstView = listViewI;
            lstView = contextParent.findViewById(R.id.listCityInfo);
            loadingdialog = new dialog(contextParent);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingdialog.startLoadingdialog();
        }
        @Override
        protected Void doInBackground(String... strings) {
            int b = 0;
            int resCode = 0;
            String lat = strings[0];
            String lon = strings[1];
            String urlz = key + "?lat=" + lat + "&lon=" + lon + API_KEY;
            try {
                URL url = new URL(urlz);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setAllowUserInteraction(false);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestMethod("GET");
                connection.connect();
                JSONObject jsonObject;
                resCode = connection.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    jsonObject = MyJsonReader.readJsonFromUrl(urlz);//save
                    JSONArray weather;
                    JSONObject wt;
                    JSONObject temp;
                    String dt, main;
                    int avg, min, max, night, eve, morn;
                    JSONArray daily = jsonObject.getJSONArray("daily");
                    for(int i = 0; i < daily.length(); i++)
                    {
                        JSONObject day = daily.getJSONObject(i);
                        dt = day.getString("dt");
                        weather = day.getJSONArray("weather");
                        wt = weather.getJSONObject(0);
                        main = wt.getString("main");
                        temp = day.getJSONObject("temp");
                        avg = temp.getInt("day");
                        min = temp.getInt("min");
                        max = temp.getInt("max");
                        night = temp.getInt("night");
                        eve = temp.getInt("eve");
                        morn = temp.getInt("morn");
                        publishProgress(dt,main, String.valueOf(avg),String.valueOf(min),String.valueOf(max),String.valueOf(night),String.valueOf(eve),String.valueOf(morn));
                    }
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                String a = String.valueOf(b);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            time.add(values[0]);
            main.add(values[1]);
            avg.add(values[2]);
            min.add(values[3]);
            max.add(values[4]);
            night.add(values[5]);
            even.add(values[6]);
            morn.add(values[7]);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            WeatherInfoCityAdapter weatherInfoCityAdapter = new WeatherInfoCityAdapter(contextParent, this.time, this.main, this.avg, this.min, this.max, this.night, this.even, this.morn);
            this.lstView.setAdapter(weatherInfoCityAdapter);
            loadingdialog.dismissdialog();
        }
    }

