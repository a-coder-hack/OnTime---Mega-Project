package com.example.avnis.ontime;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NotificationHandler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_handler);

        int notificationId = 400;
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            notificationId = extras.getInt("notify");
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
        Intent i = new Intent(NotificationHandler.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
