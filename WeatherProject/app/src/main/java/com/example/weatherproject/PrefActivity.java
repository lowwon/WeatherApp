package com.example.weatherproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class PrefActivity extends PreferenceActivity{
    Locale myLocale;
    String langS, colorS;
    SharedPreferences prefs;
    BackstackActivity ba;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(PrefActivity.this);
        String lang = prefs.getString("listLang","en");
        onLoadActivity(lang);
        addPreferencesFromResource(R.xml.prefs);
        builder = new AlertDialog.Builder(this);
        langS = prefs.getString("listLang","en");
        colorS = prefs.getString("listColor","Blue");
        ListPreference listPreference = (ListPreference) getPreferenceManager().findPreference("listLang");
        if(listPreference.getValue() == null){
            listPreference.setValueIndex(0);
        }
        ListPreference listPreference1 = (ListPreference) getPreferenceManager().findPreference("listColor");
        if(listPreference1.getValue() == null){
            listPreference1.setValueIndex(0);
        }
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if(!langS.equals(o.toString())){
                    onLoadActivity(o.toString());
                    builder.setTitle(R.string.success);
                    builder.setMessage(R.string.set);
                    builder.setPositiveButton("OK", (dialogInterface, i1) -> {
                        Intent intent = new Intent();
                        setResult(2, intent);
                        finish();
                        startActivity(getIntent());
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;
                }
                return false;
            }
        });
        listPreference1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if(!colorS.equals(o.toString())){
                    onLoadActivity(o.toString());
                    builder.setTitle(R.string.success);
                    builder.setMessage(R.string.set2);
                    builder.setPositiveButton("OK", (dialogInterface, i1) -> {
                        finishAffinity();
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;
                }
                return false;
            }
        });
    }
    private void onChangeLanguage(Locale locale){
        DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
        Configuration configuration = new Configuration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(locale);
        }
        getBaseContext().getResources().updateConfiguration(configuration,displayMetrics);

    }
    void onLoadActivity(String lang){
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
            default:
                break;
        }
        onChangeLanguage(myLocale);
    }
}
