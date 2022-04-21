package com.example.weatherproject;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

public class ReadJsonFile {
    public static String loadJSONFromAsset(Context context)
            throws IOException {
//        AssetManager manager = context.getAssets();
        InputStream is = context.getResources().openRawResource(R.raw.vn);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer, "UTF-8");
    }

}
