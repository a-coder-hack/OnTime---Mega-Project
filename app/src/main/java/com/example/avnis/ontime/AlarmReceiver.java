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
import android.os.Build;
import android.widget.Toast;

/**
 * Created by avnis on 7/17/2019.
 */
public class AlarmReceiver extends BroadcastReceiver{
    public static final String CHANNEL_ID = "ontime.notification";
    @Override
    public void onReceive(Context context, Intent intent) {
        int NOTIFICATION_ID = intent.getIntExtra("NOTIFICATION_ID", 10);
        String subject = intent.getStringExtra("subject");
        //String date = intent.getStringExtra("date");
        String durs = intent.getStringExtra("durs");
        String times = intent.getStringExtra("times");
        String date = intent.getStringExtra("date");
//        String query="select * from subject where date=='"+date+"' and time=='"+times+"'";
        Intent notificationIntent = new Intent(context, NotificationHandler.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent presentIntent = new Intent(context,PresentActivity.class);
        presentIntent.putExtra("notify",NOTIFICATION_ID);
        presentIntent.putExtra("subject",subject);
        presentIntent.putExtra("durs",durs);
        presentIntent.putExtra("times",times);
        presentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        presentIntent.putExtra("date",date);
        PendingIntent presentPendingIntent = PendingIntent.getActivity(context, 0 , presentIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent absentIntent = new Intent(context,AbsentActivity.class);
        absentIntent.putExtra("notify",NOTIFICATION_ID);
        absentIntent.putExtra("subject",subject);
        absentIntent.putExtra("durs",durs);
        absentIntent.putExtra("times",times);
        absentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        absentIntent.putExtra("date",date);
        PendingIntent absentPendingIntent = PendingIntent.getActivity(context, 1 , absentIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent cancelIntent = new Intent(context,CancelActivity.class);
        cancelIntent.putExtra("notify",NOTIFICATION_ID);
        cancelIntent.putExtra("subject",subject);
        cancelIntent.putExtra("durs",durs);
        cancelIntent.putExtra("times",times);
        cancelIntent.putExtra("date",date);
        cancelIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent cancelPendingIntent = PendingIntent.getActivity(context, 2 , cancelIntent, PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder builder = new Notification.Builder(context);

        Notification notification = builder.setContentTitle(subject)
                .setContentText(times )
                .setTicker("Attendance update")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_launcher,"Present",presentPendingIntent)
                .addAction(R.mipmap.ic_launcher,"Absent",absentPendingIntent)
                .addAction(R.mipmap.ic_launcher,"Cancel",cancelPendingIntent)
                .build();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "OntimeNotification",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
