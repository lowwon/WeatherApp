package com.example.weatherproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BackstackActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    int checksetting = 0;
    Geocoder geocoder;
    Location location;
    GoogleApiClient gac;
    TextView city;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backstack_activity);
        if(checkPlayServices()){
            buildGoogleApiClient();
        }
        initFragment();
    }

    private synchronized void buildGoogleApiClient() {
        if (gac == null) {
            gac = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
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
        if(id == R.id.reorder){
            if(checksetting == 0){
                checksetting = 1;
                replaceFragmentContent(new FragmentMenu());
            }
            else
            {
                checksetting = 0;
                onBackPressed();
            }

        }
        return super.onOptionsItemSelected(item);
    }
    private void replaceFragmentContent (Fragment fragment){
        if(fragment != null){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            ft.replace(R.id.container_body, fragment);
            ft.addToBackStack(fragment.getClass().getSimpleName());
            ft.commit();
        }
    }
    private void initFragment (){
        FragmentMain fragmain = new FragmentMain();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft1 = fm.beginTransaction();
        ft1.replace(R.id.container_body, fragmain);
        ft1.commit();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getLocation();
    }
    public void getLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        } else {
            location = LocationServices.FusedLocationApi.getLastLocation(gac);
            TextView citya = (TextView) findViewById(R.id.city);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude,1);
                    String city = addresses.get(0).getLocality();
                    citya.setText(city);
                } catch (IOException e) {
                    citya.setText("Error");
                    e.printStackTrace();
                }
            } else {
                citya.setText("Không biết");
            }
        }
    }
    @Override
    public void onConnectionSuspended(int i) {
        gac.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Lỗi kết nối: " + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        gac.connect();
        super.onStart();
    }
    @Override
    protected void onStop() {
        gac.disconnect();
        super.onStop();
    }
}