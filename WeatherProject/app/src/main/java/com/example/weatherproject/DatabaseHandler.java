package com.example.weatherproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.AdapterView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "weatherDB";
    private static final String TABLE_CITY = "cities";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LON = "lon";


    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CITY_TABLE = "CREATE TABLE " + TABLE_CITY + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_LAT + " DOUBLE," + KEY_LON + " DOUBLE" + ")";
        sqLiteDatabase.execSQL(CREATE_CITY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
        onCreate(sqLiteDatabase);
    }
    public CityTemp getCity(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CITY, new String[]{KEY_ID, KEY_NAME, KEY_LAT, KEY_LON }, KEY_ID +"+=?", new String[] {String.valueOf(id)}, null,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        CityTemp city = new CityTemp(Integer.parseInt(cursor.getString(0)), cursor.getString(1),Double.parseDouble(cursor.getString(2)),Double.parseDouble(cursor.getString(3)));
        return city;
    }
    void addCity (CityTemp city){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, city.getName());
        values.put(KEY_LAT, city.getLat());
        values.put(KEY_LON, city.getLon());
        db.insert(TABLE_CITY, null, values);
        db.close();
    }
    public List<CityTemp> getAllCities(){
        List<CityTemp> cityList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+ TABLE_CITY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor curson = db.rawQuery(selectQuery, null);
        if(curson.moveToFirst()){
            do{
                CityTemp city = new CityTemp();
                city.setId(Integer.parseInt(curson.getString(0)));
                city.setName(curson.getString(1));
                city.setLon(Double.parseDouble(curson.getString(2)));
                city.setLat(Double.parseDouble(curson.getString(3)));
                cityList.add(city);
            }while(curson.moveToNext() );
        }
        return cityList;
    }
    public void deleteCity(@NonNull CityTemp city){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CITY, KEY_ID + " = ? ", new String[]{String.valueOf(city.getId())});
        db.close();
    }
}