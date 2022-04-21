package com.example.weatherproject;

import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class FragmentMain extends Fragment{
    View view;
    TextView city, temp, main, sunRise, sunSet, feel_Like, preesure, humidity, visibility, wind, rain, uvi, may, day, time;
    ImageView iconMain;
    ListView lstday;
    String keyAPI = "bc40dd0b42c7e289bb7c950d62fb64e4&units=metric&cnt=6";
    String url = "https://api.openweathermap.org/data/2.5/onecall?lat=10.83333&lon=106.666672&appid=";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_main, container, false);
        final FragmentActivity c = getActivity();
        temp = view.findViewById(R.id.nhietdo);
        main = view.findViewById(R.id.info);
        iconMain = view.findViewById(R.id.themeColor);
        sunRise = view.findViewById(R.id.mtmoc);
        sunSet = view.findViewById(R.id.mtlan);
        feel_Like = view.findViewById(R.id.feel);
        preesure = view.findViewById(R.id.apsuat);
        humidity = view.findViewById(R.id.doam);
        visibility = view.findViewById(R.id.tamnhin);
        wind = view.findViewById(R.id.gio);
        rain = view.findViewById(R.id.rain);
        uvi = view.findViewById(R.id.uv);
        may = view.findViewById(R.id.may);
        day = view.findViewById(R.id.curDay);
        time = view.findViewById(R.id.curTime);
        RecyclerView rvx = (RecyclerView) view.findViewById(R.id.timeDay);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rvWeatherTime);
        rvx.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL,false));
        rv.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false));
        new AsyncTaskNetwork(c, city, temp, main, sunRise,sunSet,feel_Like,preesure,humidity,visibility,wind,rain,uvi,may,day,time, iconMain, rv,rvx).execute("https://api.openweathermap.org/data/2.5/onecall?lat=10.83333&lon=106.666672&appid=bc40dd0b42c7e289bb7c950d62fb64e4&units=metric");
        return view;

    }

}
