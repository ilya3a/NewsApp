package com.yoyo.newsapp.weather_data_models;

public class Condition {
    private String code;

    private String icon;

    private String text;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ClassPojo [code = " + code + ", icon = " + icon + ", text = " + text + "]";
    }
}