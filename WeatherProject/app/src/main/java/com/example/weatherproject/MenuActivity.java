package com.example.weatherproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MenuActivity extends AppCompatActivity {
    View view;
    Button find, follow, setting, rate, contact;
    ImageView as;
    Locale myLocale;
    String colorMain = null;
    int check = 0;
    TextView txt1, txt2, txt3;
    dialog loadingdialog;
    RelativeLayout rl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingdialog = new dialog(MenuActivity.this);
        loadPage();
    }
    private void changeColor(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MenuActivity.this);
        String color = prefs.getString("listColor","Blue");
        if(color.equals("Blue")){
            setTheme(R.style.Theme_MyAppTheme1);
            colorMain = "blue";
        }else if(color.equals("Red")){
            setTheme(R.style.Theme_MyAppTheme2);
            colorMain = "red";
        }
        else if(color.equals("Pink")){
            setTheme(R.style.Theme_MyAppTheme4);
            colorMain = "pink";
        }
        else if(color.equals("Violet")){
            setTheme(R.style.Theme_MyAppTheme5);
            colorMain = "violet";
        }
        else{
            setTheme(R.style.Theme_MyAppTheme3);
            colorMain = "green";
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 2){
            check = 1;
            loadPage();
        }
    }
    public void loadPage(){
        changLangue();
        changeColor();
        setContentView(R.layout.menu);
        rl = (RelativeLayout) findViewById(R.id.menu);
        txt1 = findViewById(R.id.menutxt1);
        txt2 = findViewById(R.id.menutxt2);
        txt3 = findViewById(R.id.menutxt3);
        if(colorMain.equals("blue")){
            rl.setBackgroundResource(R.drawable.boxmain);
            txt1.setBackgroundResource(R.drawable.boxsettingtxt);
            txt2.setBackgroundResource(R.drawable.boxsettingtxt);
            txt3.setBackgroundResource(R.drawable.boxsettingtxt);
        }else if(colorMain.equals("red")){
            rl.setBackgroundResource(R.drawable.boxmain2);
            txt1.setBackgroundResource(R.drawable.boxsettingtxt2);
            txt2.setBackgroundResource(R.drawable.boxsettingtxt2);
            txt3.setBackgroundResource(R.drawable.boxsettingtxt2);
        }
        else if(colorMain.equals("pink")){
            rl.setBackgroundResource(R.drawable.boxmain4);
            txt1.setBackgroundResource(R.drawable.boxsettingtxt4);
            txt2.setBackgroundResource(R.drawable.boxsettingtxt4);
            txt3.setBackgroundResource(R.drawable.boxsettingtxt4);
        }
        else if(colorMain.equals("violet")){
            rl.setBackgroundResource(R.drawable.boxmain5);
            txt1.setBackgroundResource(R.drawable.boxsettingtxt5);
            txt2.setBackgroundResource(R.drawable.boxsettingtxt5);
            txt3.setBackgroundResource(R.drawable.boxsettingtxt5);
        }
        else{
            rl.setBackgroundResource(R.drawable.boxmain3);
            txt1.setBackgroundResource(R.drawable.boxsettingtxt3);
            txt2.setBackgroundResource(R.drawable.boxsettingtxt3);
            txt3.setBackgroundResource(R.drawable.boxsettingtxt3);
        }

//        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        find = findViewById(R.id.btnFind);
        setting = findViewById(R.id.caidat);
        follow = findViewById(R.id.btnLoveCity);
        rate = findViewById(R.id.btnRate);
        contact = findViewById(R.id.btnContact);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, FindCity.class);
                startActivity(intent);
            }
        });
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, CityFollow.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,PrefActivity.class);
                startActivityForResult(intent, 2);
                if(check == 1){
                    Intent intentx = new Intent();
                    setResult(2, intentx);
                }
                else{
                    Intent intentx = new Intent();
                    setResult(1, intentx);
                }
//                startActivity(intent);
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingdialog.startContactdialog();
            }
        });
    }
    public void onChangeLanguage(Locale locale){
        DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
        Configuration configuration = new Configuration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(locale);
        }
        getBaseContext().getResources().updateConfiguration(configuration,displayMetrics);
    }
    public void changLangue(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MenuActivity.this);
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
            case "ru":
                myLocale = new Locale("ru", "RU");
                break;
            case "de":
                myLocale = new Locale("de", "DE");
                break;
            case "it":
                myLocale = new Locale("it", "IT");
                break;
            default:break;
        }
        onChangeLanguage(myLocale);
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
