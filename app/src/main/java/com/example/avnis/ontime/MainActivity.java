package com.example.avnis.ontime;

import android.content.Intent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG= "MainActivity";
    CardView c1,c2,c3,c4,c5,c6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase am=openOrCreateDatabase("am",MODE_PRIVATE,null);
        am.execSQL("create table if not exists monday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists tuesday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists wednesday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists friday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists thursday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists saturday(time varchar,name varchar,dur varchar)");
        am.execSQL("create table if not exists sunday(time varchar,name varchar,dur varchar)");
        //am.execSQL("create table if not exists sync()");
        am.execSQL("create table if not exists subjects(name varchar,tc int,ac int,per int)");
//        am.execSQL("insert into monday values('4:20','name','2')");
        c1=(CardView)findViewById(R.id.card_view1);
        c2=(CardView)findViewById(R.id.card_view2);
        c3=(CardView)findViewById(R.id.card_view3);
        c4=(CardView)findViewById(R.id.card_view4);
        c5=(CardView)findViewById(R.id.card_view5);
        c6=(CardView)findViewById(R.id.card_view6);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                Integer Today = calendar.get(Calendar.DAY_OF_WEEK);
//                Today=Calendar.SUNDAY;

                String day=Integer.toString(calendar.DAY_OF_MONTH);
                Date todayDate = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String todayString = formatter.format(todayDate);
                Log.e("MainActivity",todayString);
                switch (Today) {
                    case Calendar.SUNDAY:
                        Intent i1= new Intent(MainActivity.this,SundayUpdate.class);
                        Bundle b1= new Bundle();
                        b1.putString("date", todayString);
                        i1.putExtras(b1);
                        startActivity(i1);
                        break;
                    case Calendar.MONDAY:
                        Intent i2= new Intent(MainActivity.this,MondayUpdate.class);
                        Bundle b2 = new Bundle();
                        b2.putString("date", todayString);
                        i2.putExtras(b2);
                        startActivity(i2);
                        break;
                    case Calendar.TUESDAY:
                        Intent i3= new Intent(MainActivity.this,TuesdayUpdate.class);
                        Bundle b3 = new Bundle();
                        b3.putString("date", todayString);
                        i3.putExtras(b3);
                        startActivity(i3);
                        break;
                    case Calendar.WEDNESDAY:
                        Intent i4= new Intent(MainActivity.this,WednesdayUpdate.class);
                        Bundle b4 = new Bundle();
                        b4.putString("date", todayString);
                        i4.putExtras(b4);
                        startActivity(i4);
                        break;
                    case Calendar.THURSDAY:
                        Intent i5= new Intent(MainActivity.this,ThursdayUpdate.class);
                        Bundle b5 = new Bundle();
                        b5.putString("date", todayString);
                        i5.putExtras(b5);
                        startActivity(i5);
                        break;
                    case Calendar.FRIDAY:
                        Intent i6= new Intent(MainActivity.this,FridayUpdate.class);
                        Bundle b6 = new Bundle();
                        b6.putString("date", todayString);
                        i6.putExtras(b6);
                        startActivity(i6);
                        break;
                    case Calendar.SATURDAY:
                        Intent i7= new Intent(MainActivity.this,SaturdayUpdate.class);
                        Bundle b7 = new Bundle();
                        b7.putString("date", todayString);
                        i7.putExtras(b7);
                        startActivity(i7);
                        break;
                }
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,AttendaceInfo.class);
                startActivity(i);
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Subject.class);
                startActivity(i);
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,TimeTable.class);
                startActivity(i);
            }
        });

        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,PrevEntry.class);
                startActivity(i);
            }
        });

        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,AboutUs.class);
                startActivity(i);

            }
        });
        c1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    c1.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 2, getApplicationContext().getResources().getDisplayMetrics()));
                }
                else
                {
                    c1.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 8, getApplicationContext().getResources().getDisplayMetrics()));
                }
                return false;
            }
        });
        c2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    c2.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 2, getApplicationContext().getResources().getDisplayMetrics()));
                }
                else
                {
                    c2.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 8, getApplicationContext().getResources().getDisplayMetrics()));
                }
                return false;
            }
        });
        c3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    c3.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 2, getApplicationContext().getResources().getDisplayMetrics()));
                }
                else
                {
                    c3.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 8, getApplicationContext().getResources().getDisplayMetrics()));
                }
                return false;
            }
        });
        c4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    c4.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 2, getApplicationContext().getResources().getDisplayMetrics()));
                }
                else
                {
                    c4.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 8, getApplicationContext().getResources().getDisplayMetrics()));
                }
                return false;
            }
        });
        c5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    c5.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 2, getApplicationContext().getResources().getDisplayMetrics()));
                }
                else
                {
                    c5.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 8, getApplicationContext().getResources().getDisplayMetrics()));
                }
                return false;
            }
        });
        c6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    c6.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 2, getApplicationContext().getResources().getDisplayMetrics()));
                }
                else
                {
                    c6.setCardElevation(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 8, getApplicationContext().getResources().getDisplayMetrics()));
                }
                return false;
            }
        });
    }
}
