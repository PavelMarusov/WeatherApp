package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.adapters.WeekDaysAdapter;
import com.example.weatherapp.data.network.WeatherApi;
import com.example.weatherapp.models.Days;
import com.example.weatherapp.models.Main;
import com.example.weatherapp.models.Sys;
import com.example.weatherapp.models.WeatherModel;
import com.example.weatherapp.models.Wind;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WeatherApi.WeatherCallback {
private RecyclerView recyclerView;
private WeekDaysAdapter adapter;
private List <Days> list;
private ConstraintLayout weatherLayout;
private  ImageView couldImage;
private TextView time;
private TextView temperature;
private TextView maxTemperature;
private TextView minTemperature;
private TextView humidity;
private TextView pressure;
private TextView wind;
private TextView sunrise_tv;
private TextView sunset_tv;
private TextView lengthDay;
private TextView cityName;
private TextView typeWeather;
private Main  mMain;
private Wind mWind;

private Sys mSys;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.weatherApi.getWeather(this);
        weatherLayout = findViewById(R.id.general_layout);
        couldImage = findViewById(R.id.could_iv);
        time = findViewById(R.id.data_tv);
        temperature = findViewById(R.id.temperatyre);
        maxTemperature = findViewById(R.id.max_temp_tv);
        minTemperature = findViewById(R.id.min_temp_tv);
        humidity = findViewById(R.id.humidity);
        pressure = findViewById(R.id.pressure);
        wind = findViewById(R.id.wind);
        sunrise_tv= findViewById(R.id.sunrise);
        sunset_tv= findViewById(R.id.sunset);
        cityName = findViewById(R.id.city_name_tv);
        lengthDay= findViewById(R.id.day_length_tv);
        typeWeather= findViewById(R.id.weathe_type_tv);
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


    @Override
    public void onSuccess(WeatherModel model) {
        setSunrise(model);
        setSunset(model);
        setLengthDay(model);
        setWeatherType(model);
        mMain = model.getMain();
        mWind = model.getWind();
        cityName.setText(model.getName());
        temperature.setText(String.valueOf(mMain.getTemp()));
        maxTemperature.setText(String.valueOf(mMain.getTemp_max()-273.15));
        minTemperature.setText(String.valueOf(mMain.getTemp_min()-273.15));
        humidity.setText(String.valueOf(mMain.getHumidity()) + "%");
        pressure.setText(String.valueOf(mMain.getPressure()));
        wind.setText(String.valueOf(mWind.getSpeed()));

    }

    @Override
    public void onFailure(Exception ex) {
        Toast.makeText(this, "Посмотри в окно))", Toast.LENGTH_SHORT).show();
    }
public void setWeatherType(WeatherModel model){
        mMain = model.getMain();
        int could = mMain.getHumidity();
        if (could>68){
            couldImage.setImageResource(R.drawable.drizzle);
            typeWeather.setText("Дождь");
            weatherLayout.setBackgroundResource(R.drawable.wealprain);
        }if (could > 55 && could < 60){
            couldImage.setImageResource(R.drawable.cloudy);
            typeWeather.setText("Пасмурно");
            weatherLayout.setBackgroundResource(R.drawable.wealpasmurno);
        }
        else {
            couldImage.setImageResource(R.drawable.sunnyy);
            typeWeather.setText("Солнечно");
            weatherLayout.setBackgroundResource(R.drawable.wallpapsunny);
    }
}
    public void setLengthDay(WeatherModel model){
        mSys = model.getSys();
        long sunrise = mSys.getSunrise();
        long sunset = mSys.getSunset();
        long dayLength = sunrise-sunset;
        Date date = new java.util.Date(dayLength*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+3"));
        String formattedDate = sdf.format(date);
        lengthDay.setText(formattedDate);


    }

    public void setSunrise(WeatherModel model){
        mSys = model.getSys();
        long unixSeconds = mSys.getSunrise();
        Date date = new java.util.Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+6"));
        String formattedDate = sdf.format(date);
        sunrise_tv.setText(formattedDate);
    }

    public void setSunset(WeatherModel model) {
        mSys = model.getSys();
        long unixSeconds = mSys.getSunset();
        Date date = new java.util.Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+6"));
        String formattedDate = sdf.format(date);
       sunset_tv.setText(formattedDate);

    }

}
