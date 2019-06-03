package com.yoyo.newsapp.weather_data_models;

public class MyPojo {
    private Current current;

    private Location location;

    private Forecast forecast;

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    @Override
    public String toString() {
        return "ClassPojo [current = " + current + ", location = " + location + ", forecast = " + forecast + "]";
    }
}
