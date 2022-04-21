package com.example.weatherproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;

public class WeatherInfoCityAdapter extends BaseAdapter{
    String a[] = new String[7];
    LayoutInflater inflater;
    String lat,lon;
    String key = "https://api.openweathermap.org/data/2.5/onecall";
    String API_KEY = "&appid=bc40dd0b42c7e289bb7c950d62fb64e4&units=metric";
    public WeatherInfoCityAdapter(Context context, String lat, String lon){
        inflater = (LayoutInflater.from(context));
        this.lat = lat;
        this.lon = lon;
    }
    @Override
    public int getCount() {
        return a.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.list_city_info, null);
        Context ac = inflater.getContext();
        TextView time = view.findViewById(R.id.dateTimeInfo);
        TextView main = view.findViewById(R.id.mainInfo);
        TextView temp = view.findViewById(R.id.tempInfo);
        TextView max = view.findViewById(R.id.maxTempInfo);
        TextView min = view.findViewById(R.id.minTempInfo);
        TextView night = view.findViewById(R.id.NightTempInfo);
        TextView eve = view.findViewById(R.id.EveTempInfo);
        TextView morn = view.findViewById(R.id.MornTempInfo);
        String url = key + "?lat=" + lat + "&lon=" + lon + API_KEY;
        new AsyncTaskNetWork3(ac, time, main, temp,max,min, night, eve, morn, i).execute(url);
        return view;
    }
}
