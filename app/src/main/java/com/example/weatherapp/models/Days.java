package com.example.weatherapp.models;

public class Days  {
    private String weekDay;
    private int dayImage;

    public Days(String weekDay, int dayImage) {
        this.weekDay = weekDay;
        this.dayImage = dayImage;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public int getDayImage() {
        return dayImage;
    }

    public void setDayImage(int dayImage) {
        this.dayImage = dayImage;
    }
}
