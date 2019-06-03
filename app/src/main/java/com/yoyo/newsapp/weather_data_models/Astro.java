package com.yoyo.newsapp.weather_data_models;

public class Astro {
    private String moonset;

    private String sunrise;

    private String sunset;

    private String moonrise;

    public String getMoonset() {
        return moonset;
    }

    public void setMoonset(String moonset) {
        this.moonset = moonset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(String moonrise) {
        this.moonrise = moonrise;
    }

    @Override
    public String toString() {
        return "ClassPojo [moonset = " + moonset + ", sunrise = " + sunrise + ", sunset = " + sunset + ", moonrise = " + moonrise + "]";
    }
}

