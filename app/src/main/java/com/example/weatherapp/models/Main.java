
package com.example.weatherapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    @Expose
    private Double temp;
    @SerializedName("feels_like")
    @Expose
    private Double feels_like;
    @SerializedName("temp_min")
    @Expose
    private Double temp_min;
    @SerializedName("temp_max")
    @Expose
    private Double temp_max;
    @SerializedName("pressure")
    @Expose
    private Integer pressure;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(Double feels_like) {
        this.feels_like = feels_like;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

}
