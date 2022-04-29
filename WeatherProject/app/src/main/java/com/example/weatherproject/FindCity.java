package com.example.weatherproject;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FindCity extends AppCompatActivity {
    List<City> listCity = new ArrayList<>();
    TextView tam;
    EditText txtFind;
    Button btnFind;
    ListView listViewx;
    DatabaseHandler db;
    AlertDialog.Builder builder;
    dialog loadingdialog;
    List<City> listT = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeColor();
        setContentView(R.layout.findcity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.citySearch);
        loadingdialog = new dialog(FindCity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnFind = (Button) findViewById(R.id.btnFindCity);
        txtFind = (EditText) findViewById(R.id.txtFindCity);
        tam = findViewById(R.id.tam2);
        listViewx = (ListView) findViewById(R.id.listCityFind);
        builder = new AlertDialog.Builder(this);
        loadCity();
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findInListCity();
            }
        });
        listViewx.setOnItemClickListener((adapterView, view, i, l) -> {
            db = new DatabaseHandler(getApplicationContext());
            String cityName = listT.get(i).getName() + ", " + listT.get(i).getCity();
            Double lat = listT.get(i).getLat();
            Double lon = listT.get(i).getLon();
            CityTemp cityTemp = new CityTemp(cityName, lat, lon);
            List<CityTemp> listCityT = db.getAllCities();
            if(listCityT.size() == 10)
            {
                tam.setText(R.string.err1);
                String err = tam.getText().toString();
                loadingdialog.startLoadingdialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingdialog.fail(err);
                    }
                }, 1000);
                return;
            }
            for (CityTemp c: listCityT) {
                if(c.getName().equals(cityName))
                {
                    tam.setText(R.string.err2);
                    String err = tam.getText().toString();
                    loadingdialog.startLoadingdialog();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingdialog.fail(err);
                        }
                    }, 1000);
                    return;
                }
            }
            db.addCity(cityTemp);
            tam.setText(R.string.addsuc);
            String err = tam.getText().toString();
            loadingdialog.startLoadingdialog();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingdialog.success(err, 0);
                }
            }, 1000);
        });
    }
    private void changeColor(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(FindCity.this);
        String color = prefs.getString("listColor","Blue");
        if(color.equals("Blue")){
            setTheme(R.style.Theme_MyAppTheme1);
        }else if(color.equals("Red")){
            setTheme(R.style.Theme_MyAppTheme2);
        }
        else{
            setTheme(R.style.Theme_MyAppTheme3);
        }
    }
    public void loadCity(){
        String json = null;
        JSONArray listCityJson;
        try{
            json = ReadJsonFile.loadJSONFromAsset(FindCity.this);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            listCityJson = jsonObject.getJSONArray("city");
        } catch (JSONException e) {
            return;
        }
        String id = null;
        String name = null;
        String country = null;
        double lon = 0;
        double lat = 0;
        JSONObject object;
        for(int i = 0; i < listCityJson.length(); i++){
            try {
                object = listCityJson.getJSONObject(i);
                id = object.getString("city");
                name = object.getString("admin_name");
                country = object.getString("country");
                lon = object.getDouble("lng");
                lat = object.getDouble("lat");
                City city = new City(id, name, country, lon, lat);
                listCity.add(city);
            } catch (JSONException e) {
                return;
            }
        }
    }
    public void findInListCity(){
        String txt = String.valueOf(txtFind.getText());
        listT.clear();
        for(int i = 0; i < listCity.size(); i++){
            City city = listCity.get(i);
            if(city.getCity().contains(txt) || city.getName().contains(txt))
            {
                listT.add(city);
            }
            CityAdapter cityAdapter = new CityAdapter(this, listT);
            listViewx.setAdapter(cityAdapter);
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
