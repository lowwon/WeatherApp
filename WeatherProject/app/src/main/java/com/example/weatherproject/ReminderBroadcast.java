package com.example.weatherproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import androidx.core.app.NotificationCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationUtils _notificationUtils = new NotificationUtils(context);
        String body = "Xem ngay thời tiết hôm nay thôi nào";

        NotificationCompat.Builder _builder = _notificationUtils.setNotification("Chúc bạn buổi sáng tốt lành",body);
        _notificationUtils.getManager().notify(101, _builder.build());
    }
//    @Override
//    public void onReceive(Context context, Intent intent)
//    {
//        NotificationUtils _notificationUtils = new NotificationUtils(context);
//        NotificationCompat.Builder _builder = _notificationUtils.setNotification("Testing", "Testing notification system");
//        _notificationUtils.getManager().notify(101, _builder.build());
//    }
}
