package com.example.weatherproject;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AsyncTaskNetWork2 extends AsyncTask<String, String, Void>  {
    dialog loadingdialog;
    String key = "https://api.openweathermap.org/data/2.5/weather";
    String API_KEY = "&appid=bc40dd0b42c7e289bb7c950d62fb64e4&units=metric";
    List<String> time = new ArrayList<>();
    List<String> temp = new ArrayList<>();
    List<CityTemp> ct = new ArrayList<>();
    ListView lstview = null;
    Activity contextParent;
    AlertDialog.Builder builder;
    CityFollowAdapter cityAdapter;
    public AsyncTaskNetWork2(CityFollow cityFollow, List<CityTemp> listCity) {
        contextParent = cityFollow;
        ct = listCity;
        lstview = (ListView) contextParent.findViewById(R.id.listCityFollow);
        loadingdialog = new dialog((Activity) cityFollow);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingdialog.startLoadingdialog();
    }

    @Override
    protected Void doInBackground(String... strings) {
        String l = strings[1];
        int length = Integer.parseInt(l);
        String [][] saveP = new String[length][2];
        String []SaveP1 = strings[0].split("/");
        for (int i = 0; i < SaveP1.length;i++){
            saveP[i] = SaveP1[i].split(",");
        }
        StringBuilder sb;
        for(int i = 0; i < saveP.length; i++) {
            int b = 0;
            int resCode = 0;
            URL url = null;
            try {
                url = new URL(key + "?lat=" + saveP[i][0] + "&lon=" + saveP[i][1] + API_KEY);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setAllowUserInteraction(false);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestMethod("GET");
                connection.connect();
                JSONObject jsonObject;
                resCode = connection.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    jsonObject = MyJsonReader.readJsonFromUrl(String.valueOf(url));
                    JSONObject a = jsonObject.getJSONObject("main");
                    publishProgress(jsonObject.getString("dt"), String.valueOf(a.getInt("temp")));
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        time.add(values[0]);
        temp.add(values[1]);
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        CityFollowAdapter cityAdapter = new CityFollowAdapter(contextParent, ct, temp, time);
        lstview.setAdapter(cityAdapter);
        loadingdialog.dismissdialog();
    }
}
