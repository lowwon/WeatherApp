package com.example.weatherproject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AsyncTaskNetWork3 extends AsyncTask<String, JSONObject, Void> {
    TextView time,temp,main, min, max, night, eve, morn;
    Context view;
    int posi;
    dialog loadingdialog;
    public AsyncTaskNetWork3(Context ac, TextView time, TextView main, TextView temp, TextView max, TextView min, TextView night, TextView eve, TextView morn, int i) {
        this.time = time;
        this.temp = temp;
        this.max = max;
        this.min = min;
        this.main = main;
        this.eve = eve;
        this.night = night;
        this.morn = morn;
        view = ac;
        posi = i;
        loadingdialog = new dialog((Activity) view);
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
        SimpleDateFormat time = new SimpleDateFormat("E, dd/MM/yyyy");
        String timeX = null;
        long currentDateTime = 0;
        JSONArray daily;
        String day = null, max = null,min = null, night = null,eve = null, morn = null ;
        try {
            JSONObject c;
            JSONObject temp;
            JSONObject main;
            JSONArray wt;
            daily = jsonObject.getJSONArray("daily");
            c = daily.getJSONObject(posi);
            wt = c.getJSONArray("weather");
            main = wt.getJSONObject(0);
            currentDateTime = c.getLong("dt");
            Date saveTime = new Date(currentDateTime * 1000);
            temp = c.getJSONObject("temp");
            timeX = time.format(saveTime);
            String main1 = main.getString("main");
            this.time.setText(timeX);
            this.main.setText(main1);
            day = String.valueOf(temp.getInt("day")) + "°";
            max = String.valueOf(temp.getInt("max"))+ "°";
            min = String.valueOf(temp.getInt("min"))+ "°";
            night = String.valueOf(temp.getInt("night"))+ "°";
            eve = String.valueOf(temp.getInt("eve"))+ "°";
            morn = String.valueOf(temp.getInt("morn"))+ "°";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.temp.setText(day);
        this.max.setText(max);
        this.min.setText(min);
        this.night.setText(night);
        this.eve.setText(eve);
        this.morn.setText(morn);
        loadingdialog.dismissdialog();
    }
}
