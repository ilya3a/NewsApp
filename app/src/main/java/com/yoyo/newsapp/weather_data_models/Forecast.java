package com.yoyo.newsapp.weather_data_models;

public class Forecast {
    private Forecastday[] forecastday;

    public Forecastday[] getForecastday() {
        return forecastday;
    }

    public void setForecastday(Forecastday[] forecastday) {
        this.forecastday = forecastday;
    }

    @Override
    public String toString() {
        return "ClassPojo [forecastday = " + forecastday + "]";
    }
}
