package com.example.avnis.ontime;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by avnis on 7/25/2019.
 */
public class NotificationAlarmReceiver extends BroadcastReceiver {

    public static final String CHANNEL_ID = "ontime.notification.reminder";

    @Override
    public void onReceive(Context context, Intent intent) {
        int NOTIFICATION_ID = intent.getIntExtra("NOTIFICATION_ID", 400);
        String subject = intent.getStringExtra("subject");
        //String date = intent.getStringExtra("date");
        String times = intent.getStringExtra("times");
        String date = intent.getStringExtra("date");
        String mess = intent.getStringExtra("mess");
        String attendance = intent.getStringExtra("attendance");
        String percentage = intent.getStringExtra("percentage");

        Intent notificationIntent = new Intent(context, NotificationHandler.class);

        notificationIntent.putExtra("notify",NOTIFICATION_ID);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);

        Notification notification = builder.setContentTitle(subject + " " + times)
                .setContentText(attendance + " " +mess)
                .setTicker("Ontime Reminder")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "OntimeNotificationReminder",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
