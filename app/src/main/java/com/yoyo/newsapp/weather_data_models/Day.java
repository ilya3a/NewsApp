package com.yoyo.newsapp.weather_data_models;

public class Day {
    private String avgvis_km;

    private String uv;

    private String avgtemp_f;

    private String avgtemp_c;

    private String maxtemp_c;

    private String maxtemp_f;

    private String mintemp_c;

    private String avgvis_miles;

    private String mintemp_f;

    private String totalprecip_in;

    private String avghumidity;

    private Condition condition;

    private String maxwind_kph;

    private String maxwind_mph;

    private String totalprecip_mm;

    public String getAvgvis_km() {
        return avgvis_km;
    }

    public void setAvgvis_km(String avgvis_km) {
        this.avgvis_km = avgvis_km;
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv) {
        this.uv = uv;
    }

    public String getAvgtemp_f() {
        return avgtemp_f;
    }

    public void setAvgtemp_f(String avgtemp_f) {
        this.avgtemp_f = avgtemp_f;
    }

    public String getAvgtemp_c() {
        return avgtemp_c;
    }

    public void setAvgtemp_c(String avgtemp_c) {
        this.avgtemp_c = avgtemp_c;
    }

    public String getMaxtemp_c() {
        return maxtemp_c;
    }

    public void setMaxtemp_c(String maxtemp_c) {
        this.maxtemp_c = maxtemp_c;
    }

    public String getMaxtemp_f() {
        return maxtemp_f;
    }

    public void setMaxtemp_f(String maxtemp_f) {
        this.maxtemp_f = maxtemp_f;
    }

    public String getMintemp_c() {
        return mintemp_c;
    }

    public void setMintemp_c(String mintemp_c) {
        this.mintemp_c = mintemp_c;
    }

    public String getAvgvis_miles() {
        return avgvis_miles;
    }

    public void setAvgvis_miles(String avgvis_miles) {
        this.avgvis_miles = avgvis_miles;
    }

    public String getMintemp_f() {
        return mintemp_f;
    }

    public void setMintemp_f(String mintemp_f) {
        this.mintemp_f = mintemp_f;
    }

    public String getTotalprecip_in() {
        return totalprecip_in;
    }

    public void setTotalprecip_in(String totalprecip_in) {
        this.totalprecip_in = totalprecip_in;
    }

    public String getAvghumidity() {
        return avghumidity;
    }

    public void setAvghumidity(String avghumidity) {
        this.avghumidity = avghumidity;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getMaxwind_kph() {
        return maxwind_kph;
    }

    public void setMaxwind_kph(String maxwind_kph) {
        this.maxwind_kph = maxwind_kph;
    }

    public String getMaxwind_mph() {
        return maxwind_mph;
    }

    public void setMaxwind_mph(String maxwind_mph) {
        this.maxwind_mph = maxwind_mph;
    }

    public String getTotalprecip_mm() {
        return totalprecip_mm;
    }

    public void setTotalprecip_mm(String totalprecip_mm) {
        this.totalprecip_mm = totalprecip_mm;
    }

    @Override
    public String toString() {
        return "ClassPojo [avgvis_km = " + avgvis_km + ", uv = " + uv + ", avgtemp_f = " + avgtemp_f + ", avgtemp_c = " + avgtemp_c + ", maxtemp_c = " + maxtemp_c + ", maxtemp_f = " + maxtemp_f + ", mintemp_c = " + mintemp_c + ", avgvis_miles = " + avgvis_miles + ", mintemp_f = " + mintemp_f + ", totalprecip_in = " + totalprecip_in + ", avghumidity = " + avghumidity + ", condition = " + condition + ", maxwind_kph = " + maxwind_kph + ", maxwind_mph = " + maxwind_mph + ", totalprecip_mm = " + totalprecip_mm + "]";
    }
}