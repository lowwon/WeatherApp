package com.example.weatherproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherDayAdapter extends RecyclerView.Adapter<WeatherDayAdapter.ViewHolder> {
    private List<WeatherDay> listWeather;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView thu, max, avg, min;
        public ImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thu = (TextView) itemView.findViewById(R.id.thu);
            icon = (ImageView) itemView.findViewById(R.id.iconDay);
            max = (TextView) itemView.findViewById(R.id.maxTempDay);
            avg = (TextView) itemView.findViewById(R.id.avgTempDay);
            min = (TextView) itemView.findViewById(R.id.minTempDay);
        }
    }
    public WeatherDayAdapter (List<WeatherDay> weather){
        listWeather = weather;
    }
    @NonNull
    @Override
    public WeatherDayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View weatherView = inflater.inflate(R.layout.timeday,parent,false);
        ViewHolder viewHolder = new ViewHolder(weatherView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDayAdapter.ViewHolder holder, int position) {
        SimpleDateFormat time = new SimpleDateFormat("E");
        SimpleDateFormat sTime = new SimpleDateFormat("HH");
        TextView thuT = holder.thu;
        TextView maxT = holder.max;
        TextView avgT = holder.avg;
        TextView minT = holder.min;
        ImageView iconText = holder.icon;
        WeatherDay wtd = listWeather.get(position);
        long timeS = Long.parseLong(wtd.getThu());
        int hour = -1;
        try{
            Date date = new Date(timeS * 1000);
            String curTime = time.format(date);
            String time1 = sTime.format(date);
            hour = Integer.parseInt(time1);
            thuT.setText(curTime);
        }catch (Exception e){
            thuT.setText(String.valueOf(timeS));
        }
        if (hour >= 6 && hour < 18) {
            switch (wtd.getIcon()) {
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
            switch (wtd.getIcon()) {
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
        maxT.setText(String.valueOf(wtd.getMaxTemp()) + "°");
        avgT.setText(String.valueOf(wtd.getAvgTemp()) + "°");
        minT.setText(String.valueOf(wtd.getMinTemp()) + "°");
    }

    @Override
    public int getItemCount() {
        return listWeather.size();
    }
}
