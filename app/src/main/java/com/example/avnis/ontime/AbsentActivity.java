package com.example.avnis.ontime;

import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class AbsentActivity extends AppCompatActivity {

    private String database;
    private String date;
    private String time;
    private String durs;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDurs() {
        return durs;
    }

    public void setDurs(String durs) {
        this.durs = durs;
    }

    private Button button;
    int notify = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            notify = extras.getInt("notify");
            database = extras.getString("subject");
            time = extras.getString("times");
            durs = extras.getString("durs");
            date = extras.getString("date");
        }
        SQLiteDatabase am=openOrCreateDatabase("am",android.content.Context.MODE_PRIVATE,null);
        String query="select * from '"+database+"' where date=='"+date+"' and time=='"+time+"'";
        Cursor cursor=am.rawQuery(query,null);
        if(cursor.getCount()>0)
        {
            cursor.moveToNext();
            String stat=cursor.getString(2);
            am.execSQL("update '"+database+"' set status='0' where date=='"+date+"' and time=='"+time+"'");
            if(stat.equals("1"))
            {
                String query1="select * from subjects where name=='"+database+"'";
                Cursor cursor1=am.rawQuery(query1,null);
                if(cursor1.getCount()>0)
                {
                    cursor1.moveToNext();
                    String ac = cursor1.getString(2);
                    String tc = cursor1.getString(1);
                    Integer aci=Integer.parseInt(ac);
                    Integer tci=Integer.parseInt(tc);
                    aci--;
                    Integer per=aci*100;
                    per=(Integer)per/tci;
                    String pers=per.toString();
                    String acs=aci.toString();
                    am.execSQL("update subjects set ac='"+acs+"' where name=='"+database+"'");
                    am.execSQL("update subjects set per='"+pers+"' where name=='"+database+"'");
                }
            }
            else if(stat.equals("2"))
            {
                String query1="select * from subjects where name=='"+database+"'";
                Cursor cursor1=am.rawQuery(query1,null);
                if(cursor1.getCount()>0)
                {
                    cursor1.moveToNext();
                    String ac = cursor1.getString(2);
                    String tc = cursor1.getString(1);
                    Integer aci=Integer.parseInt(ac);
                    Integer tci=Integer.parseInt(tc);
                    tci++;
                    String acs=aci.toString();
                    String tcs=tci.toString();
                    Integer per=aci*100;
                    per=(Integer)per/tci;
                    String pers=per.toString();
                    am.execSQL("update subjects set ac='"+acs+"' where name=='"+database+"'");
                    am.execSQL("update subjects set tc='"+tcs+"' where name=='"+database+"'");
                    am.execSQL("update subjects set per='"+pers+"' where name=='"+database+"'");
                }
            }
        }

        else
        {
            am.execSQL("insert into '"+database+"' values('"+date+"','"+time+"','0')");
            String query1="select * from subjects where name=='"+database+"'";
            Cursor cursor1=am.rawQuery(query1,null);
            if(cursor1.getCount()>0)
            {
                cursor1.moveToNext();
                String ac = cursor1.getString(2);
                String tc = cursor1.getString(1);
                Integer aci=Integer.parseInt(ac);
                Integer tci=Integer.parseInt(tc);
                tci++;
                String acs=aci.toString();
                String tcs=tci.toString();
                Integer per=aci*100;
                per=(Integer)per/tci;
                String pers=per.toString();
                am.execSQL("update subjects set ac='"+acs+"' where name=='"+database+"'");
                am.execSQL("update subjects set tc='"+tcs+"' where name=='"+database+"'");
                am.execSQL("update subjects set per='"+pers+"' where name=='"+database+"'");
            }
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //TODO: CHange notification id
        notificationManager.cancel(notify);
        Intent i = new Intent(AbsentActivity.this,MainActivity.class);
        startActivity(i);
    }
}
