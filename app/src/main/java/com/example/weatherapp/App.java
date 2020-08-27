package com.example.weatherapp;

import android.app.Application;

import com.example.weatherapp.data.network.WeatherApi;

public class App extends Application {
    public static WeatherApi weatherApi;
    @Override
    public void onCreate() {
        super.onCreate();
        weatherApi = new WeatherApi();
    }
}
