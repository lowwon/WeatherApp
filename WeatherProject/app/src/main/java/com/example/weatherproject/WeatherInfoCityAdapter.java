package com.example.weatherproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherInfoCityAdapter extends BaseAdapter{
    LayoutInflater inflater;
    List<String> time = new ArrayList<>();
    List<String> avg = new ArrayList<>();
    List<String> main = new ArrayList<>();
    List<String> min = new ArrayList<>();
    List<String> max = new ArrayList<>();
    List<String> night = new ArrayList<>();
    List<String> morn = new ArrayList<>();
    List<String> even = new ArrayList<>();
    public WeatherInfoCityAdapter(Context context, List<String> time, List<String> main,List<String> avg , List<String> min,List<String> max , List<String> night, List<String> even , List<String> morn ){
        inflater = (LayoutInflater.from(context));
        this.time = time;
        this.avg = avg;
        this.main = main;
        this.min = min;
        this.max = max;
        this.night = night;
        this.even = even;
        this.morn = morn;
    }
    @Override
    public int getCount() {
        return time.size();
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
        TextView time = view.findViewById(R.id.dateTimeInfo);
        TextView main = view.findViewById(R.id.mainInfo);
        TextView temp = view.findViewById(R.id.tempInfo);
        TextView max = view.findViewById(R.id.maxTempInfo);
        TextView min = view.findViewById(R.id.minTempInfo);
        TextView night = view.findViewById(R.id.NightTempInfo);
        TextView eve = view.findViewById(R.id.EveTempInfo);
        TextView morn = view.findViewById(R.id.MornTempInfo);
        long timeL = Long.parseLong(this.time.get(i));
        Date saveTime = new Date(timeL * 1000);
        SimpleDateFormat timefr = new SimpleDateFormat("E, dd/MM/yyyy");
        String timeS = timefr.format(saveTime);
        String mainS = this.main.get(i);
        String tempS = this.avg.get(i)+ "°";
        String maxS = this.max.get(i)+ "°";
        String minS = this.min.get(i)+ "°";
        String nightS = this.night.get(i)+ "°";
        String mornS = this.morn.get(i)+ "°";
        String evenS = this.even.get(i)+ "°";
        time.setText(timeS);
        main.setText(mainS);
        temp.setText(tempS);
        max.setText(maxS);
        min.setText(minS);
        night.setText(nightS);
        eve.setText(evenS);
        morn.setText(mornS);
        return view;
    }
}
