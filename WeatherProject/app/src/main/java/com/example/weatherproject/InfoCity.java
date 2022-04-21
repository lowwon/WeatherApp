package com.example.weatherproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InfoCity extends AppCompatActivity {
    ListView listViewI;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_city);
        Intent intent = getIntent();
        String lat = intent.getStringExtra("lat");
        String lon = intent.getStringExtra("lon");
        String name = intent.getStringExtra("name");
        listViewI = (ListView) findViewById(R.id.listCityInfo);
        WeatherInfoCityAdapter weatherInfoCityAdapter = new WeatherInfoCityAdapter(this, lat, lon);
        listViewI.setAdapter(weatherInfoCityAdapter);
    }
}
