package com.example.itiswhereitis;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class EmptyDataService extends IntentService {

    private static final String TAG = "com.example.itiswhereitis";
    public static final String EXTRA_MESSAGE = "no data";
    public static final int NOTIFICATION_ID = 35;


    public EmptyDataService() {
        super("EmptyDataService");
    }


    @SuppressLint("LongLogTag")
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "SERVICE STARTED");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                .setSmallIcon(R.mipmap.ic_app_icon)
                .setContentTitle(getResources().getString(R.string.notification_title))
                .setContentText(getResources().getString(R.string.notification_text))
                .setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(35, builder.build());


    }


}
