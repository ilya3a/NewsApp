package com.yoyo.newsapp.weather_data_models;

public class Forecastday {
    private String date;

    private Astro astro;

    private String date_epoch;

    private Day day;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Astro getAstro() {
        return astro;
    }

    public void setAstro(Astro astro) {
        this.astro = astro;
    }

    public String getDate_epoch() {
        return date_epoch;
    }

    public void setDate_epoch(String date_epoch) {
        this.date_epoch = date_epoch;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "ClassPojo [date = " + date + ", astro = " + astro + ", date_epoch = " + date_epoch + ", day = " + day + "]";
    }
}