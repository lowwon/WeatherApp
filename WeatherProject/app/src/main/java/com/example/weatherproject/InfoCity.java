package com.example.weatherproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class InfoCity extends AppCompatActivity {
    ListView listViewI;
    Button delCity;
    DatabaseHandler db;
    TextView tam;
    dialog loadingdialog;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeColor();
        setContentView(R.layout.info_city);
        delCity = findViewById(R.id.delCityFl);
        loadingdialog = new dialog(InfoCity.this);
        tam = findViewById(R.id.tam1);
        tam.setText(R.string.delCity);
        String txt = tam.getText().toString();
        tam.setText("");
        db = new DatabaseHandler(this);
        ActionBar actionBar = getSupportActionBar();
        builder = new AlertDialog.Builder(this);
        Intent intent = getIntent();
        String lat = intent.getStringExtra("lat");
        String lon = intent.getStringExtra("lon");
        String name = intent.getStringExtra("name");
        actionBar.setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int id = intent.getIntExtra("id",0);
        delCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle(R.string.noti);
                builder.setMessage(R.string.unFl);
                builder.setPositiveButton("OK", (dialogInterface, i1) -> {
                    CityTemp ctt = new CityTemp(id, name, Double.parseDouble(lat), Double.parseDouble(lon));
                    db.deleteCity(ctt);
                    loadingdialog.startLoadingdialog();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingdialog.success( txt, 1);
                        }
                    }, 1000);
                });
                builder.setNegativeButton("Close", (dialogInterface, i) -> {
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        AsyncTaskNetWork3 myTask = new AsyncTaskNetWork3(this,listViewI);
        myTask.execute(lat, lon);
    }
    private void changeColor(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(InfoCity.this);
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
