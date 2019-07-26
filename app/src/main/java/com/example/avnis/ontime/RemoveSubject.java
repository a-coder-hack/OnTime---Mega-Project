package com.example.avnis.ontime;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by avnis on 7/6/2019.
 */
public class RemoveSubject extends DialogFragment {
    private static final String TAG= "RemoveSubject";
    private DialogInterface.OnDismissListener onDismissListener;
    EditText time,dur;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_remove_subject, container, false);
        final String day = getArguments().getString("day");
        final String subjectname = getArguments().getString("subject");
        final String timename = getArguments().getString("time");
        ImageButton close=(ImageButton)view.findViewById(R.id.imageButton3);
        TextView alert=(TextView) view.findViewById(R.id.textView);
//        Toast.makeText(getContext().getApplicationContext(), "Do you want to remove "+subjectname+" at time "+timename+"?", Toast.LENGTH_SHORT).show();
        alert.setText("Do you want to remove '"+subjectname+"' at time '"+timename+"'?");
        Button remove=(Button)view.findViewById(R.id.remove);
        Button cancel=(Button)view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase am = getActivity().openOrCreateDatabase("am", android.content.Context.MODE_PRIVATE, null);
                //TODO: Remove Notification
                int notify = 10;
                Cursor c = am.rawQuery("SELECT * FROM notificationid where subject='"+subjectname+"' and day='"+day+"' and time='"+timename+"'",null);
                if(c!=null) {
                    if  (c.moveToFirst()) {
                        do {
                            notify = c.getInt(0);
                            AlarmManager alarmManager;
                            alarmManager = (AlarmManager) getContext().getApplicationContext().getSystemService(getContext().getApplicationContext().ALARM_SERVICE);
                            Intent intent = new Intent(getContext().getApplicationContext(), AlarmReceiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext().getApplicationContext(),
                                    notify, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            NotificationManager notificationManager = (NotificationManager) getContext().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancel(notify);
                            alarmManager.cancel(pendingIntent);
                        }while (c.moveToNext());
                    }
                }

                Cursor c2 = am.rawQuery("SELECT * FROM notificationreminder where subject='"+subjectname+"' and day='"+day+"' and time='"+timename+"'",null);
                if(c2!=null) {
                    if  (c2.moveToFirst()) {
                        do {
                            notify = c2.getInt(0);
                            AlarmManager alarmManager;
                            alarmManager = (AlarmManager) getContext().getApplicationContext().getSystemService(getContext().getApplicationContext().ALARM_SERVICE);
                            Intent intent = new Intent(getContext().getApplicationContext(), AlarmReceiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext().getApplicationContext(),
                                    notify, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            NotificationManager notificationManager = (NotificationManager) getContext().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancel(notify);
                            alarmManager.cancel(pendingIntent);
                        }while (c2.moveToNext());
                    }
                }
                am.execSQL("delete from '"+day+"' where time='"+timename+"' and name='"+subjectname+"'");
                Toast.makeText(getContext().getApplicationContext(), "Successfully removed '"+subjectname+"' at time '"+timename+"'", Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        });
        return view;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }
}
