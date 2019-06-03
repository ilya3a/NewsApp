package com.yoyo.newsapp.weather_data_models;

public class Location {
    private String localtime;

    private String country;

    private String localtime_epoch;

    private String name;

    private String lon;

    private String region;

    private String lat;

    private String tz_id;

    public String getLocaltime() {
        return localtime;
    }

    public void setLocaltime(String localtime) {
        this.localtime = localtime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocaltime_epoch() {
        return localtime_epoch;
    }

    public void setLocaltime_epoch(String localtime_epoch) {
        this.localtime_epoch = localtime_epoch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getTz_id() {
        return tz_id;
    }

    public void setTz_id(String tz_id) {
        this.tz_id = tz_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [localtime = " + localtime + ", country = " + country + ", localtime_epoch = " + localtime_epoch + ", name = " + name + ", lon = " + lon + ", region = " + region + ", lat = " + lat + ", tz_id = " + tz_id + "]";
    }
}