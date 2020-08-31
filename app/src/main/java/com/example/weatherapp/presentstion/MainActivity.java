package com.example.weatherapp.presentstion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weatherapp.App;
import com.example.weatherapp.R;
import com.example.weatherapp.adapters.WeekDaysAdapter;
import com.example.weatherapp.data.network.WeatherApi;
import com.example.weatherapp.models.Coord;
import com.example.weatherapp.models.Days;
import com.example.weatherapp.models.Main;
import com.example.weatherapp.models.Sys;
import com.example.weatherapp.models.Weather;
import com.example.weatherapp.models.WeatherModel;
import com.example.weatherapp.models.Wind;
import com.example.weatherapp.models.dayli.WeatherDayliModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WeatherApi.WeatherCallback, View.OnClickListener, WeatherApi.WeatherDailyCallback {
    private RecyclerView recyclerView;
    private WeekDaysAdapter adapter;
    private List<WeatherDayliModel> list;
    private ConstraintLayout weatherLayout;
    private ImageView couldImage;
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
    private Main mMain;
    private Wind mWind;
    private Weather mWeather;
    private Sys mSys;
    private Coord mCoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.weatherApi.getWeather(this);
        App.weatherApi.getWeatherDaily(this);
        weatherLayout = findViewById(R.id.general_layout);
        couldImage = findViewById(R.id.could_iv);
        time = findViewById(R.id.data_tv);
        temperature = findViewById(R.id.temperatyre);
        maxTemperature = findViewById(R.id.max_temp_tv);
        minTemperature = findViewById(R.id.min_temp_tv);
        humidity = findViewById(R.id.humidity);
        pressure = findViewById(R.id.pressure);
        wind = findViewById(R.id.wind);
        sunrise_tv = findViewById(R.id.sunrise);
        sunset_tv = findViewById(R.id.sunset);
        cityName = findViewById(R.id.city_name_tv);
        cityName.setOnClickListener(this);
        lengthDay = findViewById(R.id.day_length_tv);
        typeWeather = findViewById(R.id.weathe_type_tv);
        recyclerView = findViewById(R.id.days_recyclerview);

        setTime();


    }

    public void setTime() {
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
        mCoord = model.getCoord();
        cityName.setText(model.getName());
        temperature.setText(String.valueOf(mMain.getTemp()));
        maxTemperature.setText(String.valueOf(mMain.getTemp_max() - 273.15));
        minTemperature.setText(String.valueOf(mMain.getTemp_min() - 273.15));
        humidity.setText(String.valueOf(mMain.getHumidity()) + "%");
        pressure.setText(String.valueOf(mMain.getPressure()));
        wind.setText(String.valueOf(mWind.getSpeed()));

    }

    @Override
    public void onSuccess(WeatherDayliModel model) {

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        list = new ArrayList<>();
        list.add(model);
        adapter = new WeekDaysAdapter(list);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onFailure(Exception ex) {
        Toast.makeText(this, "Посмотри в окно))", Toast.LENGTH_SHORT).show();
    }

    public void setWeatherType(WeatherModel model) {
        mMain = model.getMain();
        mWeather = model.getWeather().get(0);
        String iconID = "http://openweathermap.org/img/wn/" + mWeather.getIcon() + "@2x.png";
        Glide.with(this).load(iconID).centerCrop().into(couldImage);
        typeWeather.setText(mWeather.getDescription());
    }

    public void setLengthDay(WeatherModel model) {
        mSys = model.getSys();
        long sunrise = mSys.getSunrise();
        long sunset = mSys.getSunset();
        long dayLength = sunrise - sunset;
        Date date = new java.util.Date(dayLength * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+3"));
        String formattedDate = sdf.format(date);
        lengthDay.setText(formattedDate);


    }

    public void setSunrise(WeatherModel model) {
        mSys = model.getSys();
        long unixSeconds = mSys.getSunrise();
        Date date = new java.util.Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+6"));
        String formattedDate = sdf.format(date);
        sunrise_tv.setText(formattedDate);
    }

    public void setSunset(WeatherModel model) {
        mSys = model.getSys();
        long unixSeconds = mSys.getSunset();
        Date date = new java.util.Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+6"));
        String formattedDate = sdf.format(date);
        sunset_tv.setText(formattedDate);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}
