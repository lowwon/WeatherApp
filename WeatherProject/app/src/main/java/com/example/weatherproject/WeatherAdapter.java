package com.example.weatherproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView time, temp;
        public ImageView icon;
        public ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            time = (TextView) itemView.findViewById(R.id.time);
            temp = (TextView) itemView.findViewById(R.id.temp);
        }
    }
    private List<WeatherTime> listWeather;
    public WeatherAdapter (List<WeatherTime> weather){
        listWeather = weather;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View weatherView = inflater.inflate(R.layout.list_weather_time,parent,false);
        ViewHolder viewHolder = new ViewHolder(weatherView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder viewHolder, int position) {
        WeatherTime wt = listWeather.get(position);
        TextView timeText = viewHolder.time;
        String time = wt.getTime();
        SimpleDateFormat sTime = new SimpleDateFormat("HH");
        int hour = -1;
        try{
            long a = Long.parseLong(time);
            Date Date = new Date(a*1000);
            String time1 = sTime.format(Date);
            hour = Integer.parseInt(time1);
            timeText.setText(time1);
        }catch (Exception e){
            timeText.setText(time);
        }
        ImageView iconText = viewHolder.icon;
        if (hour >= 6 && hour < 18) {
            switch (wt.getIcon()) {
                case "01d":
                    iconText.setImageResource(R.drawable.a01d);
                    break;
                case "02d":
                    iconText.setImageResource(R.drawable.a02d);
                    break;
                case "03d":
                    iconText.setImageResource(R.drawable.a03d);
                    break;
                case "04d":
                    iconText.setImageResource(R.drawable.a04d);
                    break;
                case "09d":
                    iconText.setImageResource(R.drawable.a09d);
                    break;
                case "10d":
                    iconText.setImageResource(R.drawable.a10d);
                    break;
                case "11d":
                    iconText.setImageResource(R.drawable.a11d);
                    break;
                case "13d":
                    iconText.setImageResource(R.drawable.a13d);
                    break;
                case "50d":
                    iconText.setImageResource(R.drawable.a50d);
                    break;
                default:
                    break;
            }
        }
        else {
            switch (wt.getIcon()) {
                case "01n":
                    iconText.setImageResource(R.drawable.a01n);
                    break;
                case "02n":
                    iconText.setImageResource(R.drawable.a02n);
                    break;
                case "03n":
                    iconText.setImageResource(R.drawable.a03n);
                    break;
                case "04n":
                    iconText.setImageResource(R.drawable.a04n);
                    break;
                case "09n":
                    iconText.setImageResource(R.drawable.a09n);
                    break;
                case "10n":
                    iconText.setImageResource(R.drawable.a10n);
                    break;
                case "11n":
                    iconText.setImageResource(R.drawable.a11n);
                    break;
                case "13n":
                    iconText.setImageResource(R.drawable.a13n);
                    break;
                case "50n":
                    iconText.setImageResource(R.drawable.a50n);
                    break;
                default:
                    break;
            }
        }
        TextView tempText = viewHolder.temp;
        tempText.setText(String.valueOf(wt.getTemp()) + "°");
    }

    @Override
    public int getItemCount() {
        return listWeather.size();
    }

}
