package com.yoyo.newsapp;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yoyo.newsapp.news_data_models.Article;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.RecyclerCallBack, WeatherFragmentMain.WeatherFragmentMainListener {
    final String WEATHER_FRAGMENT_TAG = "weather_fragment_main";
    final String NEWS_FRAGMENT_TAG = "news_fragment";
    final String FULL_NEWS_FRAGMENT_TAG = "full_news_fragment";
    final String WEATHER_SECOND_FRAGMENT_TAG = "second_weather_fragment";
    final String NOTIF_REPETITION_ID = "notif_rep_id";
    final String NOTIF_REPETITION = "notif_rep";

    final String SUBSCRIBE_DATA = "subscribe_data";
    final String SHARED_DATA = "data";


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Handler notifHandler = new Handler();
    FragmentManager fragmentManager;
    AlarmManager alarmManager;
    RelativeLayout weatherLayout;
    RelativeLayout coordinatorLayout;
    boolean mainWeatherFragmentShowing = true;
    FloatingActionMenu actionMenu;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notifHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        long interval = sharedPreferences.getLong(NOTIF_REPETITION,0);
        notifHandler.removeCallbacksAndMessages(null);
        if(interval!=0){
            setIntervalNotif(interval,true);
        }
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
        long interval = sharedPreferences.getLong(NOTIF_REPETITION,0);

        if(interval==0){
            notifHandler.removeCallbacksAndMessages(null);
        }else {
            setIntervalNotif(interval,false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = findViewById(R.id.testconstr);


//        weatherLayout = findViewById(R.id.weather_container);
//        weatherLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                actionMenu.close(true);
//                WeatherFragmentMain weatherFragmentMain = (WeatherFragmentMain) fragmentManager.findFragmentByTag(WEATHER_FRAGMENT_TAG);
//                WeatherFragmentSecond weatherFragmentSecond = (WeatherFragmentSecond) fragmentManager.findFragmentByTag(WEATHER_SECOND_FRAGMENT_TAG);
//                if (mainWeatherFragmentShowing) {
//                    fragmentManager.beginTransaction().hide(weatherFragmentMain).show(weatherFragmentSecond).commit();
//                    mainWeatherFragmentShowing = false;
//                } else {
//                    fragmentManager.beginTransaction().hide(weatherFragmentSecond).show(weatherFragmentMain).commit();
//                    mainWeatherFragmentShowing = true;
//                }
//            }
//        });

        sharedPreferences = getSharedPreferences(SHARED_DATA, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ArrayList<String> subscriptionList;
        String savedSubscriptions = sharedPreferences.getString(SUBSCRIBE_DATA, "build new array");

        if ("build new array".equals(savedSubscriptions)) {
            subscriptionList = new ArrayList<>();
            subscriptionList.add("sport");
            subscriptionList.add("world");
            subscriptionList.add("economic");
            subscriptionList.add("politic");
            editor.putString(SUBSCRIBE_DATA, new Gson().toJson(subscriptionList)).apply();
        } else {
            subscriptionList = new Gson().fromJson(savedSubscriptions, new TypeToken<ArrayList<String>>() {
            }.getType());
        }

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        fragmentManager = getSupportFragmentManager();
        NewsListFragment newsFragment = NewsListFragment.newInstance(subscriptionList);
        WeatherFragmentMain weatherFragmentMain = new WeatherFragmentMain();
        WeatherFragmentSecond weatherFragmentSecond = new WeatherFragmentSecond();
        fragmentManager.beginTransaction().add(R.id.news_container, newsFragment, NEWS_FRAGMENT_TAG).add(R.id.weather_container, weatherFragmentMain, WEATHER_FRAGMENT_TAG).commit();

        actionMenu = findViewById(R.id.menu);

        FloatingActionButton subscribeActionButton = findViewById(R.id.menu_item2);
        subscribeActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionMenu.close(true);
                final Dialog subscribeDialog = new Dialog(MainActivity.this);
                final View subscribeDialogView = getLayoutInflater().inflate(R.layout.subscribtion_list_dialog_layout, null);
                subscribeDialog.setContentView(subscribeDialogView);
                subscribeDialog.setCanceledOnTouchOutside(false);
                subscribeDialog.show();

                CheckBox sportCB = subscribeDialog.findViewById(R.id.sport_news);
                CheckBox worldCB = subscribeDialog.findViewById(R.id.world_news);
                CheckBox economicsCB = subscribeDialog.findViewById(R.id.economics_news);
                CheckBox politicsCB = subscribeDialog.findViewById(R.id.politics_news);


                final ArrayList<CheckBox> checkBoxes = new ArrayList<>();
                checkBoxes.add(sportCB);
                checkBoxes.add(worldCB);
                checkBoxes.add(economicsCB);
                checkBoxes.add(politicsCB);

                ArrayList<String> subscriptionList = (new Gson().fromJson(sharedPreferences.getString(SUBSCRIBE_DATA, ""), new TypeToken<ArrayList<String>>() {
                }.getType()));

                for (CheckBox checkBox : checkBoxes) {
                    String string = (String) checkBox.getTag();
                    if (subscriptionList.contains(string)) {
                        checkBox.setChecked(true);
                    } else {
                        checkBox.setChecked(false);
                    }

                }

                TextView setBtn = subscribeDialogView.findViewById(R.id.set_btn);
                setBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ArrayList<String> temp = new ArrayList<>();

                        for (CheckBox checkBox : checkBoxes) {

                            String str = (String) checkBox.getTag();
                            if (checkBox.isChecked()) {
                                temp.add(str);
                            }
                        }
                        //save in pref|update adapter
                        editor.putString(SUBSCRIBE_DATA, new Gson().toJson(temp)).commit();
                        NewsListFragment fragmentToremove = (NewsListFragment) fragmentManager.findFragmentByTag(NEWS_FRAGMENT_TAG);
                        NewsListFragment newsListFragment = NewsListFragment.newInstance(temp);
                        ShowNewsFragment showNewsFragment = (ShowNewsFragment) fragmentManager.findFragmentByTag(FULL_NEWS_FRAGMENT_TAG);
                        if (fragmentToremove!=null){
                        fragmentManager.beginTransaction().remove(fragmentToremove).commit();}
                        if (showNewsFragment!=null){
                            fragmentManager.beginTransaction().remove(showNewsFragment).commit();}
                        fragmentManager.beginTransaction().add(R.id.news_container, newsListFragment, NEWS_FRAGMENT_TAG).commit();

                        subscribeDialog.dismiss();

                    }
                });


            }
        });

        FloatingActionButton notificationActionButton = findViewById(R.id.menu_item1);
        notificationActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionMenu.close(true);
                final Dialog notifDialog = new Dialog(MainActivity.this);
                final View notifDialogView = getLayoutInflater().inflate(R.layout.notification_dialog_layout, null);
                notifDialog.setContentView(notifDialogView);
                notifDialog.show();
                RadioGroup radioGroup = notifDialogView.findViewById(R.id.radio_group);

                radioGroup.check(sharedPreferences.getInt(NOTIF_REPETITION_ID, R.id.none));

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.none:
                                editor.putLong(NOTIF_REPETITION, 0).commit();
                                editor.putInt(NOTIF_REPETITION_ID, R.id.none).commit();
                                Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                                alarmManager.cancel(pendingIntent);
                                notifHandler.removeCallbacksAndMessages(null);
                                Snackbar snackbar = Snackbar.make(coordinatorLayout,"Notifications service off",Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                break;

                            case R.id.one_min:
                                long minMillis = 1000 * 60;
                                editor.putLong(NOTIF_REPETITION, minMillis).commit();
                                editor.putInt(NOTIF_REPETITION_ID, R.id.one_min).commit();
                                setIntervalNotif(minMillis,false);
                                snackbar = Snackbar.make(coordinatorLayout,"Notification every minute",Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                break;

                            case R.id.one_day:
                                long dayMillis = 1000 * 60 * 60 * 24;
                                editor.putLong(NOTIF_REPETITION, dayMillis).commit();
                                editor.putInt(NOTIF_REPETITION_ID, R.id.one_day).commit();
                                setIntervalNotif(dayMillis,false);
                                snackbar = Snackbar.make(coordinatorLayout,"Notifications once a day",Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                break;

                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                notifDialog.dismiss();
                            }
                        }, 400);

                    }
                });
            }
        });


    }

    @Override
    public void onItemClicked(Article article) {
        actionMenu.close(true);
        String stringArticle = new Gson().toJson(article);
        NewsListFragment newsListFragment = (NewsListFragment) fragmentManager.findFragmentByTag(NEWS_FRAGMENT_TAG);
        ShowNewsFragment showNewsFragment = ShowNewsFragment.newInstance(stringArticle);
        fragmentManager.beginTransaction().hide(newsListFragment).add(R.id.news_container, showNewsFragment, FULL_NEWS_FRAGMENT_TAG).addToBackStack(null).commit();
    }


    private void setIntervalNotif(final long interval, boolean iActivityStop) {
        if (iActivityStop) {
            Intent intent = new Intent(this, AlarmReceiver.class);
            intent.putExtra("interval", interval);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.cancel(pendingIntent);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, pendingIntent);
        } else {
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Breaking News Checkout for updates", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    notifHandler.postDelayed(this,interval);
                }
            };
            notifHandler.postDelayed(runnable, interval);
        }
    }

    @Override
    public void onConnectionErorrWeatherFragmentMain() {
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, "No internet for weather widget", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherFragmentMain weatherFragmentMain = (WeatherFragmentMain) fragmentManager.findFragmentByTag(WEATHER_FRAGMENT_TAG);
                weatherFragmentMain.requestLocationUpdates();
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }
}
