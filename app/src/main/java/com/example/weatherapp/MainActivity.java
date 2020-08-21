package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.adapters.WeekDaysAdapter;
import com.example.weatherapp.models.Days;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private RecyclerView recyclerView;
private WeekDaysAdapter adapter;
private List <Days> list;
private TextView time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time = findViewById(R.id.data_tv);
        recyclerView = findViewById(R.id.days_recyclerview);
        recyclerView.addItemDecoration( new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        list =  new ArrayList<>();
        list.add(new Days("Sunday", R.drawable.ic_sunny));
        list.add(new Days("Monday", R.drawable.ic_cloud));
        list.add(new Days("Thursday", R.drawable.ic_cloud_black));
        list.add(new Days("Wednesday", R.drawable.ic_flare));
        list.add(new Days("Thursday ", R.drawable.ic_sunny));
        list.add(new Days("Friday ", R.drawable.ic_cloud_black));
        list.add(new Days("Saturday ", R.drawable.ic_flare));
        adapter = new WeekDaysAdapter(list);
        recyclerView.setAdapter(adapter);
        setTime();


    }

    public void setTime(){
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("EEE yyyy-MM-dd|HH:mm");
        String formattedDate = df.format(c.getTime());
        time.setText(formattedDate);
    }
}
