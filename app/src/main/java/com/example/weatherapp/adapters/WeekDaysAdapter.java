package com.example.weatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.models.Days;

import java.util.List;

public class WeekDaysAdapter extends RecyclerView.Adapter<WeekDaysAdapter.DaysHolder> {
   private List<Days> daysList;

    public WeekDaysAdapter(List<Days> daysList) {
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

        public void onBind(Days days){
            dayImage.setImageResource(days.getDayImage());
            weekDay.setText(days.getWeekDay());
        }


    }
}
