package com.example.weatherproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
    TextView rs;
    EditText txtFind;
    Button btnFind;
    ListView listViewx;
    DatabaseHandler db;
    AlertDialog.Builder builder;
    List<City> listT = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findcity);
        btnFind = (Button) findViewById(R.id.btnFindCity);
        txtFind = (EditText) findViewById(R.id.txtFindCity);
        listViewx = (ListView) findViewById(R.id.listCityFind);
        builder = new AlertDialog.Builder(this);
        loadCity();
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findInListCity();
            }
        });
        listViewx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(),"aaa",Toast.LENGTH_LONG).show();
                db = new DatabaseHandler(getApplicationContext());
                String cityName = listT.get(i).getName() + ", " + listT.get(i).getCity();
                Double lat = listT.get(i).getLat();
                Double lon = listT.get(i).getLon();
                CityTemp cityTemp = new CityTemp(cityName, lat, lon);
                List<CityTemp> listCityT = db.getAllCities();
                for (CityTemp c: listCityT) {
                    if(c.getName().equals(cityName))
                    {
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn đã theo dõi thành phố này");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                        return;
                    }
                }
                db.addCity(cityTemp);
                builder.setTitle("Thông báo");
                builder.setMessage("Thêm thành công");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
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
}
