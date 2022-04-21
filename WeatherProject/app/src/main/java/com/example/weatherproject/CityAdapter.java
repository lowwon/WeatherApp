package com.example.weatherproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CityAdapter extends BaseAdapter {
    List<City> cityList = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    public CityAdapter(Context context, List<City> cityList)
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
        view = inflater.inflate(R.layout.list_city_find, null);
        TextView txt = view.findViewById(R.id.cityinfo);
        City cityInfo = cityList.get(i);
        String info = cityInfo.getCity() + ", " + cityInfo.getName() + ", " + cityInfo.getCountry();
        txt.setText(info);
        return view;
    }
}
