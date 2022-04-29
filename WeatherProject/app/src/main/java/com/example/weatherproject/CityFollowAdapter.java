package com.example.weatherproject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CityFollowAdapter extends BaseAdapter {
    List<CityTemp> cityList = new ArrayList<>();
    List<String> temp = new ArrayList<String>();
    List<String> time = new ArrayList<String>();
    LayoutInflater inflater;
    public CityFollowAdapter(Context context, List<CityTemp> cityList, List<String>  temp, List<String>  time)
    {
        this.cityList = cityList;
        this.temp = temp;
        this.time = time;
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
        TextView nameTxt = view.findViewById(R.id.nameCityFl);
        TextView timeTxt = view.findViewById(R.id.timeCityFl);
        TextView tempTxt = view.findViewById(R.id.tempCityFl);
        String nameS = cityList.get(i).getName();
        nameTxt.setText(nameS);
        tempTxt.setText(temp.get(i) + "°");
        long timeL = Long.parseLong(time.get(i));
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        Date timeD = new Date(timeL * 1000);
        String timeS = time.format(timeD);
        timeTxt.setText(timeS);
        return view;
    }
}
