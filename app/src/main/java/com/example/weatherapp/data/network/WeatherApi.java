package com.example.weatherapp.data.network;

import android.util.Log;

import com.example.weatherapp.models.Weather;
import com.example.weatherapp.models.WeatherModel;
import com.example.weatherapp.models.dayli.WeatherDayliModel;
import com.example.weatherapp.utils.Config;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherApi {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    WeatherService service = retrofit.create(WeatherService.class);

    public void getWeatherDaily(WeatherDailyCallback callback) {
        Call<WeatherDayliModel> call = service.getDayliWeather(42.87, 74.57,"minutely", Config.API_KEY);
        call.enqueue(new Callback<WeatherDayliModel>() {
            @Override
            public void onResponse(Call<WeatherDayliModel> call, Response<WeatherDayliModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("pop", "Week :" + response.body());
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<WeatherDayliModel> call, Throwable t) {
                Log.e("pop", "Week error:" + t.getMessage());
            }
        });
    }

    public void getWeather(WeatherCallback callback) {
        Call<WeatherModel> call = service.getWeather("Bishkek", Config.API_KEY, "RU");
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("pop", "Bishkek" + response.body());
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Log.d("pop", "Error" + t.getMessage());

            }
        });
    }

    public interface WeatherCallback {
        void onSuccess(WeatherModel model);

        void onFailure(Exception ex);
    }

    public interface WeatherDailyCallback {
        void onSuccess(WeatherDayliModel model);

        void onFailure(Exception ex);
    }

    public interface WeatherService {
        @GET("data/2.5/weather")
        Call<WeatherModel> getWeather
                (@Query("q") String cityName,
                 @Query("appid") String appKey,
                 @Query("lang") String lang);


        @GET("data/2.5/onecall")
        Call<WeatherDayliModel> getDayliWeather(
                @Query("lat") double lat,
                @Query("lon") double lon,
                @Query("exclude") String exclude,
                @Query("appid") String apiKey
        );
    }
}
