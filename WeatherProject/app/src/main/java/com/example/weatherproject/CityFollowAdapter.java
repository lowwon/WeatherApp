package com.example.weatherproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CityFollowAdapter extends BaseAdapter {
    List<CityTemp> cityList = new ArrayList<>();
    LayoutInflater inflater;
    String key = "https://api.openweathermap.org/data/2.5/weather";
    String API_KEY = "&appid=bc40dd0b42c7e289bb7c950d62fb64e4&units=metric";
    public CityFollowAdapter(Context context, List<CityTemp> cityList)
    {
        this.cityList = cityList;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return cityList.size();
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
        view = inflater.inflate(R.layout.city_follow, null);
        Context ac = inflater.getContext();
        String nameCity = cityList.get(i).getName();
        String lat = String.valueOf(cityList.get(i).getLat());
        String lon = String.valueOf(cityList.get(i).getLon());
        String url = key + "?lat=" + lat + "&lon=" + lon + API_KEY;
        TextView time = view.findViewById(R.id.timeCityFl);
        TextView name = view.findViewById(R.id.nameCityFl);
        TextView temp = view.findViewById(R.id.tempCityFl);
        name.setText(nameCity);
        new AsyncTaskNetWork2(ac, time, temp).execute(url);
        return view;
    }
}
