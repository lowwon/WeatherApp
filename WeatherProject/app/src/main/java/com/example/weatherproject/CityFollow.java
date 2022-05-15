package com.example.weatherproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CityFollow extends AppCompatActivity {
    List<CityTemp> listCity = new ArrayList<>();
    DatabaseHandler db;
    ListView listViewv;
    AsyncTask<String, JSONObject, String[]> asyncTaskNetWork2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeColor();
        setContentView(R.layout.list_city_follow);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.cityFl);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHandler(this);
        loadPage();
        listViewv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String lon = String.valueOf(listCity.get(i).getLon());
                String lat = String.valueOf(listCity.get(i).getLat());
                int id = listCity.get(i).getId();
                String name = listCity.get(i).getName();
                Intent intent = new Intent(CityFollow.this, InfoCity.class);
                intent.putExtra("id", id);
                intent.putExtra("lon", lon);
                intent.putExtra("lat", lat);
                intent.putExtra("name", name);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 2){
            loadPage();
        }
    }

    public void loadPage(){
        listCity = db.getAllCities();
        listViewv = findViewById(R.id.listCityFollow);
        if(listCity.size() == 0)
            return;
        String savePo = "";
        for(int i = 0; i < listCity.size(); i++){
            savePo += listCity.get(i).getLat() + "," + listCity.get(i).getLon() + "/";
        }
        String length = String.valueOf(listCity.size());
        AsyncTaskNetWork2 myTask = new AsyncTaskNetWork2(this, listCity);
        myTask.execute(savePo, length);
    }
    private void changeColor(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(CityFollow.this);
        String color = prefs.getString("listColor","Blue");
        if(color.equals("Blue")){
            setTheme(R.style.Theme_MyAppTheme1);
        }else if(color.equals("Red")){
            setTheme(R.style.Theme_MyAppTheme2);
        }
        else if(color.equals("Pink")){
            setTheme(R.style.Theme_MyAppTheme4);
        }
        else if(color.equals("Violet")){
            setTheme(R.style.Theme_MyAppTheme5);
        }
        else{
            setTheme(R.style.Theme_MyAppTheme3);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

}
