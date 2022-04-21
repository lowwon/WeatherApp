package com.example.weatherproject;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CityFollow extends AppCompatActivity {
    List<CityTemp> listCity = new ArrayList<>();
    DatabaseHandler db;
    ListView listViewv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_city_follow);
        db = new DatabaseHandler(this);
        listCity = db.getAllCities();
        listViewv = findViewById(R.id.listCityFollow);
        CityFollowAdapter cityAdapter = new CityFollowAdapter(this, listCity);
        listViewv.setAdapter(cityAdapter);
    }
}
