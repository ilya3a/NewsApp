package com.yoyo.newsapp;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.yoyo.newsapp.weather_data_models.Current;
import com.yoyo.newsapp.weather_data_models.Day;
import com.yoyo.newsapp.weather_data_models.Forecast;
import com.yoyo.newsapp.weather_data_models.Forecastday;
import com.yoyo.newsapp.weather_data_models.Location;
import com.yoyo.newsapp.weather_data_models.MyPojo;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class WeatherFragmentMain extends Fragment {
    final String API_KEY = "e399aab8b40ef6175dd2ea5d01d3d8cc";
    final String WEATHER_URL = "https://api.apixu.com/v1/forecast.json?key=9197644eaa4241a59d9104525192304&q=";//ramla;

    TextView weatherLocationTV;
    TextView weatherDateTV;
    TextView weatherTodayTV;
    ImageView weatherIconIV;
    TextView weatherTempTV;
    TextView weatherConditionTV;
    SwipeRefreshLayout swipeRefreshLayout;
    WeatherFragmentMainListener listener;
    RecyclerView recyclerView;
    ArrayList<String> weekDays;
    int dayInTheWeek;
    FusedLocationProviderClient client;
    LocationCallback locationCallback;
    final String SHARED_DATA = "data";


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onPause() {
        super.onPause();
        if (client != null)
            stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        client.removeLocationUpdates(locationCallback);
    }



    public interface WeatherFragmentMainListener {
        void onConnectionErorrWeatherFragmentMain();
    }

    public static WeatherFragmentMain newInstance(String location, String date, String iconUrl, String temp) {
        WeatherFragmentMain weatherFragmentMain = new WeatherFragmentMain();
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
        try {
            listener = (WeatherFragmentMainListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement the interface : WeatherFragmentMainListener");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main_presenter_layout, container, false);

        weatherLocationTV = rootView.findViewById(R.id.weather_location_tv);
        weatherDateTV = rootView.findViewById(R.id.weather_date_tv);
        weatherTodayTV = rootView.findViewById(R.id.todayTV);
        weatherIconIV = rootView.findViewById(R.id.weather_image_iv);
        weatherTempTV = rootView.findViewById(R.id.weather_tmp_tv);
        weatherConditionTV = rootView.findViewById(R.id.weather_condition_tv);
        recyclerView = rootView.findViewById(R.id.recycler_view_forecast);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayout.HORIZONTAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(container.getContext(),
                DividerItemDecoration.HORIZONTAL));

//        swipeRefreshLayout = rootView.findViewById(R.id.refresh_weather);
//        swipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED);
//        swipeRefreshLayout.setRefreshing(true);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });

        weekDays = new ArrayList<>();
        weekDays.add("Sun");
        weekDays.add("Mon");
        weekDays.add("Tue");
        weekDays.add("Wed");
        weekDays.add("Thu");
        weekDays.add("Fri");
        weekDays.add("Sat");

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED) {
            client = new FusedLocationProviderClient(getContext());
            requestLocationUpdates();
        } else {
            callPermissions();
        }
        return rootView;
    }

    public void callPermissions() {
        Permissions.check(getContext(), Manifest.permission.ACCESS_FINE_LOCATION, "Location permissions are required to get the Weather", new PermissionHandler() {
            @Override
            public void onGranted() {
                client = new FusedLocationProviderClient(getContext());
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
                                String location = locationObject.getName() + ", " + locationObject.getCountry();
                                weatherLocationTV.setText("" + location);
                                Date c = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("E, dd MMM yyyy");
                                String formattedDate = df.format(c);
                                weatherDateTV.setText(formattedDate);
                                weatherTodayTV.setText("Today");
                                Current currentObject = myPojo.getCurrent();
                                String temp = currentObject.getTemp_c().substring(0, currentObject.getTemp_c().indexOf(".")) + "°";
                                weatherConditionTV.setText(currentObject.getCondition().getText());
                                weatherTempTV.setText(temp);
                                String iconUrl = currentObject.getCondition().getIcon();
                                Glide.with(getContext()).load("https://" + iconUrl.substring(2)).thumbnail(0.25f).into(weatherIconIV);


                                dayInTheWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;


                                ArrayList<Day> fiveDaysForecast = new ArrayList<>();

                                Forecast forecastObject = myPojo.getForecast();
                                Forecastday[] forecastArray = forecastObject.getForecastday();
                                for (Forecastday forecastday : forecastArray) {
                                    Day day = forecastday.getDay();
                                    day.setUv(weekDays.get((dayInTheWeek % 7)));
                                    fiveDaysForecast.add(day);
                                    dayInTheWeek++;
                                }
                                WeatherStatusRecyclerViewAdapter adapter = new WeatherStatusRecyclerViewAdapter(fiveDaysForecast,getContext());
                                recyclerView.setAdapter(adapter);
//                                swipeRefreshLayout.setRefreshing(false);

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
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            callPermissions();
        }
        client.requestLocationUpdates(locationRequest, locationCallback, null);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return CubeAnimation.create(CubeAnimation.DOWN, enter, 1000);
        } else {
            return CubeAnimation.create(CubeAnimation.UP, enter, 1000);

        }
    }
}
