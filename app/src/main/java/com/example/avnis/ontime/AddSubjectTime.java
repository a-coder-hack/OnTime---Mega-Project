package com.example.avnis.ontime;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by avnis on 7/6/2019.
 */
public class AddSubjectTime extends DialogFragment {
    private static final String TAG= "AddSubjectTime";
    public static final int NotificationId = 10;
    public static final long INTERVAL_DAY = 86400000L;
    private DialogInterface.OnDismissListener onDismissListener;
    Spinner sp;
    EditText time,dur;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addsubjecttime, container, false);
        final String myStr = getArguments().getString("day");
        sp = (Spinner) view.findViewById(R.id.spinner);
        time = (EditText) view.findViewById(R.id.editText);
        dur = (EditText) view.findViewById(R.id.editText2);
        Button add=(Button)view.findViewById(R.id.addtable);
        ImageButton close=(ImageButton)view.findViewById(R.id.imageButton3);
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase am = getActivity().openOrCreateDatabase("am", android.content.Context.MODE_PRIVATE, null);
        String query = "select * from subjects";
        Cursor cursor = am.rawQuery(query, null);
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext().getApplicationContext(), R.layout.spinner_layout, R.id.txt, list);
        sp.setAdapter(adapter);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = sp.getSelectedItem().toString();
                String times= time.getText().toString();
                String durs= dur.getText().toString();
                if(subject.equals("") || times.equals("") || durs.equals(""))
                {
                    Toast.makeText(getContext().getApplicationContext(), "Fill Required", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int d=1;
                    try{
                        Integer f=Integer.parseInt(durs);
                        if(f>=24 || f<=0)
                        {d=0;}
                    }catch (NumberFormatException e){
                        d=8;
                        Toast.makeText(getContext().getApplicationContext(), "Write Duration in Correct Format(Hr)", Toast.LENGTH_SHORT).show();
                    }
                    String[] arrOfStr = times.split(":");
                    int b=0;
                    for(String ab :arrOfStr)
                    {
                            b=b+1;
                    }
                    if(b==2)
                    {

                        try {
                            Integer x = Integer.parseInt(arrOfStr[0]);
                            if (x >= 24) {
                                b = 100;
                            }
                        }catch(NumberFormatException e){
                            b=100;
                            Toast.makeText(getContext().getApplicationContext(), "Write Time in Given Format", Toast.LENGTH_SHORT).show();}

                        try {
                            Integer x= Integer.parseInt(arrOfStr[1]);
                            if (x >= 60) {
                                b = 100;
                            }
                        }catch(NumberFormatException e){
                            b=100;
                            Toast.makeText(getContext().getApplicationContext(), "Write Time in Given Format", Toast.LENGTH_SHORT).show();}
                    }
                    if(b!=2)
                    {
                        Toast.makeText(getContext().getApplicationContext(), "Write Time in Given Format", Toast.LENGTH_SHORT).show();
                    }
                    else if(d!=1)
                    {
                        Toast.makeText(getContext().getApplicationContext(), "Write Duration in Correct Format", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SQLiteDatabase am = getActivity().openOrCreateDatabase("am", android.content.Context.MODE_PRIVATE, null);
                        String query = "select * from '"+myStr+"' where time='" + times + "' and name='" + subject + "'";
                        Cursor cursor = am.rawQuery(query, null);
                        if (cursor.getCount() > 0) {
                            Toast.makeText(getContext().getApplicationContext(), "Subject Already In the Timetable", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            am.execSQL("insert into '"+myStr+"' values('"+times+"','"+subject+"','"+durs+"')");
                            Toast.makeText(getContext().getApplicationContext(),"Subject Added", Toast.LENGTH_SHORT).show();
//                            ((Monday) getParentFragment()).viewData();

                            AlarmManager alarmManager,alarmManager1;
                            List<String> days = new ArrayList<>();
                            days.add("sunday");days.add("monday");days.add("tuesday");days.add("wednesday");days.add("thursday");days.add("friday");days.add("saturday");

                            alarmManager = (AlarmManager) getContext().getApplicationContext().getSystemService(getContext().getApplicationContext().ALARM_SERVICE);
                            alarmManager1 = (AlarmManager)getContext().getApplicationContext().getSystemService(getContext().getApplicationContext().ALARM_SERVICE);
                            Intent notificationIntent = new Intent(getContext().getApplicationContext(), AlarmReceiver.class);
                            Intent reminderIntent = new Intent(getContext().getApplicationContext(), NotificationAlarmReceiver.class);
                            //TODO: Change notification id
                            am.execSQL("create table if not exists notificationid(notifyid int, subject varchar, day varchar, time varchar)");
                            am.execSQL("create table if not exists notificationreminder(notifyid int,subject varchar,day varchar, time varchar)");
                            int notify = 10,notify2 = 400;
                            Cursor c = am.rawQuery("select * from notificationid",null);
                            if(c.getCount()>0) {
                                while(c.moveToNext()) {
                                    notify = c.getInt(0);
                                    //Log.e("notify","notify "+notify+"");
                                }
                            }
                            Cursor c2 = am.rawQuery("select * from notificationreminder",null);
                            if(c2.getCount()>0) {
                                while(c2.moveToNext()) {
                                    notify2 = c2.getInt(0);
                                    //Log.e("notify","notify "+notify+"");
                                }
                            }
                            //Toast.makeText(getContext().getApplicationContext(),notify + " " + c.getCount(),Toast.LENGTH_LONG).show();
                            notify++;
                            notify2++;
                            String attes,mess,pers;
                            //TODO: Notification Reminder

                            String query3="select * from subjects where name=='"+subject+"'";
                            Cursor cursor3=am.rawQuery(query3,null);
                            if (cursor3.getCount()>0) {
                                cursor3.moveToNext();
                                String tc = cursor3.getString(1);
                                String ac = cursor3.getString(2);
                                attes = "Attendance " + ac + "/" + tc;
                                Integer tci=Integer.parseInt(tc);
                                Integer aci=Integer.parseInt(ac);
                                tci++;
                                Integer pper;
                                pper=aci*100;
                                pper=(Integer)pper/tci;
                                if(pper<75)
                                    mess = "You need to attend this class";
                                else
                                    mess = "You may leave this class today";
                                pers = cursor3.getString(3)+"%";
                            }
                            else
                            {
                                attes = "Attendance " + 0 + "/" + 0;
                                mess = "You need to attend this class";
                                pers = "0%";
                            }

                            Date todayDate = Calendar.getInstance().getTime();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            String df = formatter.format(todayDate);

                            reminderIntent.putExtra("mess", mess);
                            reminderIntent.putExtra("attendance", attes);
                            reminderIntent.putExtra("percentage", pers);
                            reminderIntent.putExtra("date", df);
                            reminderIntent.putExtra("times", times);
                            reminderIntent.putExtra("NOTIFICATION_ID",notify2);
                            reminderIntent.putExtra("subject", subject);
                            PendingIntent broadcastReminder = PendingIntent.getBroadcast(getContext().getApplicationContext(), notify2, reminderIntent, 0);
                            //Notification Reminder data end

                            am.execSQL("insert into notificationid values('"+notify+"','"+subject+"','"+myStr+"', '"+times+"')");
                            am.execSQL("insert into notificationreminder values('"+notify2+"','"+subject+"','"+myStr+"', '"+times+"')");
                            notificationIntent.putExtra("NOTIFICATION_ID",notify);
                            notificationIntent.putExtra("subject",subject);
                            notificationIntent.putExtra("times",times);
                            //notificationIntent.putExtra("date",20);
                            notificationIntent.putExtra("durs",durs);
                            notificationIntent.putExtra("date", df);

                            PendingIntent broadcast = PendingIntent.getBroadcast(getContext().getApplicationContext(), notify, notificationIntent, 0);
                            int index = days.indexOf(myStr)+1;
                            //if(index!=7) index++;
                            Calendar cal = Calendar.getInstance();
                            int daysUntilNextClass = (((int) index - (int) cal.get(Calendar.DAY_OF_WEEK) + 7) % 7);
                            //TODO: Calendar not working properly, use different one
                            cal.add(Calendar.DATE,daysUntilNextClass);
                            Integer minutes= Integer.parseInt(arrOfStr[1]);
                            cal.set(Calendar.MINUTE, minutes);
                            Integer x = Integer.parseInt(arrOfStr[0]);
                            cal.set(Calendar.HOUR_OF_DAY,x);

                            //Just to test the notification
                            Calendar test = Calendar.getInstance();
                            test.add(Calendar.SECOND,1);
                            long timeBeforeClass = cal.getTimeInMillis();
                            long timeAfterClass = cal.getTimeInMillis();
                            Toast.makeText(getContext().getApplicationContext(), days.get(index)+" Reminder set for " + cal.getTime(), Toast.LENGTH_SHORT).show();
                            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeAfterClass,INTERVAL_DAY * 7, broadcast);
                            if(timeBeforeClass > (15*60*1000)){
                                timeBeforeClass-=(15*60*1000);
                            }
                            alarmManager1.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeBeforeClass,INTERVAL_DAY * 7, broadcastReminder);
                            //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,  cal.getTimeInMillis(),INTERVAL_DAY * 7, broadcast);
                            //alarmManager1.setExact(AlarmManager.RTC_WAKEUP,test.getTimeInMillis(),broadcastReminder);
                            getDialog().dismiss();
                        }
                    }
                }
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
