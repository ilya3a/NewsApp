package com.yoyo.newsapp.news_data_models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.yoyo.newsapp.weather_data_models.Forecastday;

public class Forecast implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@SerializedName("forecastday")
	public ArrayList<Forecastday> forecastday = new ArrayList<>();
	
	public ArrayList<Forecastday> getForecastday()
    {
    	return forecastday;
    }
    public void setForecastday(ArrayList<Forecastday> mForecastday)
    {
    	this.forecastday = mForecastday;
    }
	
	
}
