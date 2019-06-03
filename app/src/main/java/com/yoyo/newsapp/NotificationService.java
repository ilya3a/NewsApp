package com.yoyo.newsapp;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;

public class NotificationService extends Service {
    BroadcastReceiver br;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //        need to register the reciver on android Oreo and up

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            br = new AlarmReceiver();
            IntentFilter filter = new IntentFilter("NOTIF_ALARM");
            this.registerReceiver(br, filter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //        need to register the reciver on android Oreo and up
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            BroadcastReceiver br = new AlarmReceiver();
            this.unregisterReceiver(br);
        }
    }
}
