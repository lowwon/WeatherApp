package com.example.weatherproject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.Inflater;

public class AsyncTaskNetWork2 extends AsyncTask<String, JSONObject, Void>  {
    TextView time,temp;
    Context view;
    dialog loadingdialog;
    public AsyncTaskNetWork2(Context v, TextView time, TextView temp) {
        this.time = time;
        this.temp = temp;
        view = v;
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
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        String hour = null;
        int temp = 0;
        long currentDateTime = 0;
        try {
            JSONObject main = jsonObject.getJSONObject("main");
            currentDateTime = jsonObject.getLong("dt");
            Date saveTime = new Date(currentDateTime * 1000);
            hour = time.format(saveTime);
            temp = main.getInt("temp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.time.setText(hour);
        this.temp.setText(String.valueOf(temp) + "°");
        loadingdialog.dismissdialog();
    }
}
