package com.example.weatherproject;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class BackstackActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    public Locale myLocale;
    AlertDialog.Builder builder;
    TextView city, temp, main, sunRise, sunSet, feel_Like, preesure, humidity, visibility, wind, rain, uvi, may, day, time;
    ImageView iconMain;
    ScrollView ll;
    String key = "https://api.openweathermap.org/data/2.5/onecall";
    String key1 = "https://api.openweathermap.org/data/2.5/weather";
    String API_KEY = "&appid=bc40dd0b42c7e289bb7c950d62fb64e4&units=metric";
    private GoogleApiClient mGoogleApiClient;
    private Location location;
    String colorMain = null;
    DatabaseHandler db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(this);
        super.onCreate(savedInstanceState);
        reminderNotification();
        loadFirst();
        ActionBar actionBar = getSupportActionBar();
        boolean networkOK = isOnline();
        if (!networkOK) {
            builder.setTitle(R.string.noti);
            builder.setMessage(R.string.net);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else{
            if(checkPlayServices()){
                buildGoogleApiClient();
            }
        }
    }
    private void changeColor(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(BackstackActivity.this);
        String color = prefs.getString("listColor","Blue");
        if(color.equals("Blue")){
            setTheme(R.style.Theme_MyAppTheme1);
            colorMain = "blue";
        }else if(color.equals("Red")){
            setTheme(R.style.Theme_MyAppTheme2);
            colorMain = "red";
        }
        else{
            setTheme(R.style.Theme_MyAppTheme3);
            colorMain = "green";
        }
    }
    private void reminderNotification() {
        db = new DatabaseHandler(this);
        List<NotiTemp>notiTemps = db.getAllNoties();
        if(notiTemps.size() == 0){
            NotiTemp notiTemp = new NotiTemp(1);
            db.addNotiTemp(notiTemp);
            NotificationUtils notificationUtils = new NotificationUtils(this);
            Calendar alarmMorn = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            alarmMorn.set(Calendar.HOUR_OF_DAY, 20);
            alarmMorn.set(Calendar.MINUTE, 0);
            alarmMorn.set(Calendar.SECOND, 0);
            alarmMorn.set(Calendar.MILLISECOND, 0);
            long stringReminder = alarmMorn.getTimeInMillis();
            notificationUtils.setReminder(stringReminder);
        }
        return;
    }

    private void loadFirst(){
        changLangue();
        changeColor();
        setContentView(R.layout.backstack_activity);
        ll = (ScrollView) findViewById(R.id.main);
        if(colorMain.equals("blue")){
            ll.setBackgroundResource(R.drawable.boxmain);
        }else if(colorMain.equals("red")){
            ll.setBackgroundResource(R.drawable.boxmain2);
        }
        else{
            ll.setBackgroundResource(R.drawable.boxmain3);
        }

    }
    private void loadToScreen(){
        String url = key + "?lat=" + String.valueOf(location.getLatitude()) + "&lon=" + String.valueOf(location.getLongitude()) + API_KEY;
        String url2 = key1 + "?lat=" + String.valueOf(location.getLatitude()) + "&lon=" + String.valueOf(location.getLongitude()) + API_KEY;
        city = findViewById(R.id.city);
        temp = findViewById(R.id.nhietdo);
        if(colorMain.equals("blue")){
            temp.setBackgroundResource(R.drawable.boxtemp);
        }else if(colorMain.equals("red")){
            temp.setBackgroundResource(R.drawable.boxtemp3);
        }
        else{
            temp.setBackgroundResource(R.drawable.boxtemp2);
        }
        main = findViewById(R.id.info);
        iconMain = findViewById(R.id.themeColor);
        sunRise = findViewById(R.id.mtmoc);
        sunSet = findViewById(R.id.mtlan);
        feel_Like = findViewById(R.id.feel);
        preesure = findViewById(R.id.apsuat);
        humidity = findViewById(R.id.doam);
        visibility = findViewById(R.id.tamnhin);
        wind = findViewById(R.id.gio);
        rain = findViewById(R.id.rain);
        uvi = findViewById(R.id.uv);
        may = findViewById(R.id.may);
        day = findViewById(R.id.curDay);
        time = findViewById(R.id.curTime);
        RecyclerView rvx = (RecyclerView) findViewById(R.id.timeDay);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rvWeatherTime);
        rvx.setLayoutManager(new LinearLayoutManager(BackstackActivity.this, LinearLayoutManager.VERTICAL,false));
        rv.setLayoutManager(new LinearLayoutManager(BackstackActivity.this, LinearLayoutManager.HORIZONTAL, false));
        new AsyncTaskNetwork(BackstackActivity.this, city, temp, main, sunRise,sunSet,feel_Like,preesure,humidity,visibility,wind,rain,uvi,may,day,time, iconMain, rv,rvx).execute(url,url2);
    }
    private Boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni != null && ni.isConnected() ) {
            return true;
        }
        return false;
    }
    private synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode != ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode,this,1000).show();
            }
            else{
                Toast.makeText(this, "Thiết bị này không hỗ trợ.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent(BackstackActivity.this, MenuActivity.class);
        startActivityForResult(intent,2);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadFirst();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }
        getLocation();
    }
    public void getLocation(){
        while(true){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (location != null) {
                    loadToScreen();
                } else {
                    finish();
                }
                break;
            }
            else{
                try {
                    Thread.sleep(1000);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Lỗi kết nối: " + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }
    private void onChangeLanguage(Locale locale){
        DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
        Configuration configuration = new Configuration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(locale);
        }
        getBaseContext().getResources().updateConfiguration(configuration,displayMetrics);
    }
    public void changLangue(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(BackstackActivity.this);
        String lang = prefs.getString("listLang","en");
        switch (lang){
            case "en":
                myLocale = new Locale("en", "US");
                break;
            case "vi":
                myLocale = new Locale("vi", "VN");
                break;
            case "cn":
                myLocale = new Locale("za", "CN");
                break;
            default:break;
        }
        onChangeLanguage(myLocale);
    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
