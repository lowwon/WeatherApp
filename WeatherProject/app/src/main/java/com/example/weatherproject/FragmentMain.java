package com.example.weatherproject;

import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class FragmentMain extends Fragment{
    View view;
    TextView city, temp, main, sunRise, sunSet, feel_Like, preesure, humidity, visibility, wind, rain, uvi, may, day, time;
    String keyAPI = "bc40dd0b42c7e289bb7c950d62fb64e4&units=metric&cnt=6";
    String url = "https://api.openweathermap.org/data/2.5/onecall?lat=10.83333&lon=106.666672&appid=";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_main, container, false);
//        city = view.findViewById(R.id.city);
        temp = view.findViewById(R.id.nhietdo);
        main = view.findViewById(R.id.info);
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
        new AsyncTaskNetwork(city, temp, main, sunRise,sunSet,feel_Like,preesure,humidity,visibility,wind,rain,uvi,may,day,time).execute("https://api.openweathermap.org/data/2.5/onecall?lat=21.83333&lon=105.666672&appid=bc40dd0b42c7e289bb7c950d62fb64e4&units=metric");
        return view;

    }

}
