package com.yoyo.newsapp;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.gson.Gson;
import com.labo.kaji.fragmentanimations.CubeAnimation;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.yoyo.newsapp.weather_data_models.Day;
import com.yoyo.newsapp.weather_data_models.Forecast;
import com.yoyo.newsapp.weather_data_models.Forecastday;
import com.yoyo.newsapp.weather_data_models.Location;
import com.yoyo.newsapp.weather_data_models.MyPojo;

import java.util.ArrayList;
import java.util.Calendar;


public class WeatherFragmentSecond extends Fragment {
    final String API_KEY = "e399aab8b40ef6175dd2ea5d01d3d8cc";
    final String WEATHER_URL = "https://api.apixu.com/v1/forecast.json?key=9197644eaa4241a59d9104525192304&q=";//ramla;


    FusedLocationProviderClient client;
    LocationCallback locationCallback;
    TextView weatherLocation;
    ImageView weatherIcon1;
    ImageView weatherIcon2;
    ImageView weatherIcon3;
    ImageView weatherIcon4;
    ImageView weatherIcon5;
    TextView weatherTemp1;
    TextView weatherTemp2;
    TextView weatherTemp3;
    TextView weatherTemp4;
    TextView weatherTemp5;
    TextView weatherDayOfTheWeedk1;
    TextView weatherDayOfTheWeedk2;
    TextView weatherDayOfTheWeedk3;
    TextView weatherDayOfTheWeedk4;
    TextView weatherDayOfTheWeedk5;
    ArrayList<TextView> textViewArrayList;
    ArrayList<ImageView> imageViewArrayList;
    ArrayList<String> weekDays;
    ArrayList<TextView> daysOfTheWeekTV;


    public static WeatherFragmentSecond newInstance(String location, String date, String iconUrl, String temp) {
        WeatherFragmentSecond weatherFragmentMain = new WeatherFragmentSecond();
        Bundle bundle = new Bundle();
        bundle.putString("location", location);
        bundle.putString("date", date);
        bundle.putString("iconUrl", iconUrl);
        bundle.putString("temp", temp);
        weatherFragmentMain.setArguments(bundle);
        return weatherFragmentMain;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.weather_fragment_second, container, false);


        weatherIcon1 = rootView.findViewById(R.id.weather_day1_image_iv);
        weatherIcon2 = rootView.findViewById(R.id.weather_day2_image_iv);
        weatherIcon3 = rootView.findViewById(R.id.weather_day3_image_iv);
        weatherIcon4 = rootView.findViewById(R.id.weather_day4_image_iv);
        weatherIcon5 = rootView.findViewById(R.id.weather_day5_image_iv);
        weatherTemp1 = rootView.findViewById(R.id.weather_day1TV);
        weatherTemp2 = rootView.findViewById(R.id.weather_day2TV);
        weatherTemp3 = rootView.findViewById(R.id.weather_day3TV);
        weatherTemp4 = rootView.findViewById(R.id.weather_day4TV);
        weatherTemp5 = rootView.findViewById(R.id.weather_day5TV);
//        weatherLocationTV = rootView.findViewById(R.id.weather_location_tv);

        textViewArrayList = new ArrayList<>();
        textViewArrayList.add(weatherTemp1);
        textViewArrayList.add(weatherTemp2);
        textViewArrayList.add(weatherTemp3);
        textViewArrayList.add(weatherTemp4);
        textViewArrayList.add(weatherTemp5);

        imageViewArrayList = new ArrayList<>();
        imageViewArrayList.add(weatherIcon1);
        imageViewArrayList.add(weatherIcon2);
        imageViewArrayList.add(weatherIcon3);
        imageViewArrayList.add(weatherIcon4);
        imageViewArrayList.add(weatherIcon5);

        daysOfTheWeekTV = new ArrayList<>();
        weatherDayOfTheWeedk1 = rootView.findViewById(R.id.weather_day1_day_TV);
        weatherDayOfTheWeedk2 = rootView.findViewById(R.id.weather_day2_day_TV);
        weatherDayOfTheWeedk3 = rootView.findViewById(R.id.weather_day3_day_TV);
        weatherDayOfTheWeedk4 = rootView.findViewById(R.id.weather_day4_day_TV);
        weatherDayOfTheWeedk5 = rootView.findViewById(R.id.weather_day5_day_TV);

        daysOfTheWeekTV.add(weatherDayOfTheWeedk1);
        daysOfTheWeekTV.add(weatherDayOfTheWeedk2);
        daysOfTheWeekTV.add(weatherDayOfTheWeedk3);
        daysOfTheWeekTV.add(weatherDayOfTheWeedk4);
        daysOfTheWeekTV.add(weatherDayOfTheWeedk5);

        weekDays = new ArrayList<>();
        weekDays.add("Sun");
        weekDays.add("Mon");
        weekDays.add("Tue");
        weekDays.add("Wed");
        weekDays.add("Thu");
        weekDays.add("Fri");
        weekDays.add("Sat");


        requestLocationUpdates();

        return rootView;
    }

    public void callPermissions() {
        Permissions.check(getContext(), Manifest.permission.ACCESS_FINE_LOCATION, "Location permissions are required to get the Weather", new PermissionHandler() {
            @Override
            public void onGranted() {
                requestLocationUpdates();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                callPermissions();
            }

        });
    }

    public void requestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED) {
            client = new FusedLocationProviderClient(getContext());
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY).setFastestInterval(1000 * 60)
                    .setInterval(1000 * 60 * 60);

            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(final LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    if (locationResult == null)
                        return;
                    final RequestQueue queue = Volley.newRequestQueue(getContext());
                    StringRequest request = new StringRequest(WEATHER_URL + locationResult.getLastLocation().getLatitude() + "," + locationResult.getLastLocation().getLongitude() + "&days=5",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                                    Location locationObject = myPojo.getLocation();
                                    int dayInTheWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1;


                                    Forecast forecastObject = myPojo.getForecast();
                                    Forecastday[] forecastArray = forecastObject.getForecastday();
                                    int i = 0;
                                    for (Forecastday forecastday : forecastArray) {
                                        daysOfTheWeekTV.get(i).setText(weekDays.get(((dayInTheWeek) % 7)));
                                        Day day = forecastday.getDay();
                                        textViewArrayList.get(i).setText(day.getMaxtemp_c().substring(0,day.getMaxtemp_c().indexOf(".")) + "°" + "\n" + day.getMintemp_c().substring(0,day.getMintemp_c().indexOf(".")) + "°");
                                        String iconUrl1 = day.getCondition().getIcon();
                                        Glide.with(getContext()).load("https:" + iconUrl1).into(imageViewArrayList.get(i));
                                        i++;
                                        dayInTheWeek++;
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (getView() != null) {
                                final Snackbar snackbar = Snackbar.make(getView(), "No internet for weather widget", Snackbar.LENGTH_INDEFINITE);
                                snackbar.setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        requestLocationUpdates();
                                        snackbar.dismiss();
                                    }
                                });
                                snackbar.show();
                            }
                        }
                    });
                    queue.add(request);
//                    queue.start();
                }
            };
            client.requestLocationUpdates(locationRequest, locationCallback, null);
        } else {
            callPermissions();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (client != null)
            stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        client.removeLocationUpdates(locationCallback);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return CubeAnimation.create(CubeAnimation.UP, enter, 1000);
        } else {
            return CubeAnimation.create(CubeAnimation.DOWN, enter, 1000);

        }
    }
}
