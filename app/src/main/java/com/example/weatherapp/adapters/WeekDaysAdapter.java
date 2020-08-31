package com.example.weatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.models.Days;
import com.example.weatherapp.models.dayli.Daily;
import com.example.weatherapp.models.dayli.WeatherDayliModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeekDaysAdapter extends RecyclerView.Adapter<WeekDaysAdapter.DaysHolder> {
   private List<WeatherDayliModel> daysList;
   private Daily daily;

    public WeekDaysAdapter(List<WeatherDayliModel> daysList) {
        this.daysList = daysList;
    }

    @NonNull
    @Override
    public DaysHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_days, parent, false);
        return new DaysHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DaysHolder holder, int position) {
        holder.onBind(daysList.get(position));
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }

    class DaysHolder extends RecyclerView.ViewHolder {
        private ImageView dayImage;
        private TextView weekDay;

        public DaysHolder(@NonNull View itemView) {
            super(itemView);
            dayImage = itemView.findViewById(R.id.item_recucler_weather_iv);
            weekDay = itemView.findViewById(R.id.item_recucler_weather_tv);

        }

        public void onBind(WeatherDayliModel model){
//            String iconID = "http://openweathermap.org/img/wn/" + model.getWeather().getIcon() + "@2x.png";
//            Glide.with(itemView.getContext()).load(iconID).into(dayImage);
            for (int i = 0; i <=7 ; i++) {
                daily = model.getDaily().get(i);
            Date date = new java.util.Date(daily.getDt() * 1000L);
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEE");
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+3"));
            String formattedDate = sdf.format(date);
            weekDay.setText(formattedDate);
            }
        }


    }
}
