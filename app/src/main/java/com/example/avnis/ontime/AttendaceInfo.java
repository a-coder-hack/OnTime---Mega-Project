package com.example.avnis.ontime;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AttendaceInfo extends AppCompatActivity {
    ListView list;
    ArrayList<InfoType> listItem;
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendace_info);

        list = (ListView) findViewById(R.id.listView2);
        add = (FloatingActionButton) findViewById(R.id.fab);
        listItem = new ArrayList<>();
        viewData();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i1= new Intent(AttendaceInfo.this,Detail.class);
                InfoType listname= (InfoType) list.getItemAtPosition(position);
                String subjectname=listname.getName();
                Bundle b1= new Bundle();
                b1.putString("subject", subjectname);
                i1.putExtras(b1);
                startActivity(i1);
            }
        });

    }


    public void viewData() {
        listItem.clear();
        SQLiteDatabase am = openOrCreateDatabase("am", android.content.Context.MODE_PRIVATE, null);
        String query = "select * from subjects";
        Cursor cursor = am.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String names, attes, mess, pers;
            names = cursor.getString(0);
            String query3 = "select * from subjects where name=='" + names + "'";
            Cursor cursor3 = am.rawQuery(query3, null);
                cursor3.moveToNext();
                String tc = cursor3.getString(1);
                String ac = cursor3.getString(2);
                String percentage = cursor3.getString(3);
                attes = "Attendance " + ac + "/" + tc;
                Integer tci = Integer.parseInt(tc);
                Integer aci = Integer.parseInt(ac);
                Integer peri = Integer.parseInt(percentage);
                tci++;
                Integer pper;
                pper = aci * 100;
                pper = (Integer) pper / tci;
                tci--;
                Double classes = tci * (0.75);
                Log.e("TAG",classes+" "+names);
                if (peri > 75) {
                    double req = aci - classes;
                    int reqr=(int)req;
                    if(reqr==0 || reqr==1)
                    mess = "You may leave " + reqr + " class";
                    else
                    mess = "You may leave " + reqr + " classes";
                }
                else if(peri==75)
                {
                    mess="You are right on the track";
                }
                else {
                    double req = classes - aci;
                    int reqi= (int) Math.ceil(req);

                    if(reqi==0 || reqi==1)
                        mess = "You need to attend " + reqi + " class";
                    else
                        mess = "You need to attend " + reqi + " classes";

                }
                pers = cursor3.getString(3) + "%";
//            UpdateListType a=new UpdateListType("8:30","MATH","Attendance 0/0","You need to attend this class","75%","Present");
            InfoType a = new InfoType(names, attes, mess, pers);
            listItem.add(a);
        }
//        UpdateListType b=new UpdateListType("9:30","MATH","Attendance 0/0","You need to attend this class","75%","Present");
//        listItem.add(b);
//        UpdateListType c=new UpdateListType("7:30","MATH","Attendance 0/0","You need to attend this class","75%","Present");
//        listItem.add(c);
//        UpdateListType d=new UpdateListType("10:00","MATH","Attendance 0/0","You need to attend this class","75%","Present");
//        listItem.add(d);
//        UpdateListType e=new UpdateListType("1:30","MATH","Attendance 0/0","You need to attend this class","75%","Present");
//        listItem.add(e);
        InfoAdapter adapter = new InfoAdapter(this, R.layout.info, listItem);
        list.setAdapter(adapter);
    }

}