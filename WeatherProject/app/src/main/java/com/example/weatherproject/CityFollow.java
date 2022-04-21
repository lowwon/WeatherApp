package com.example.weatherproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
        listViewv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String lon = String.valueOf(listCity.get(i).getLon());
                String lat = String.valueOf(listCity.get(i).getLat());
                String name = listCity.get(i).getName();
                Intent intent = new Intent(CityFollow.this, InfoCity.class);
                intent.putExtra("lon", lon);
                intent.putExtra("lat", lat);
                intent.putExtra("name", name);
                startActivityForResult(intent, 2);
            }
        });
    }
}
