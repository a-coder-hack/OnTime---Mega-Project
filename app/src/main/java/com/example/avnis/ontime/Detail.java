package com.example.avnis.ontime;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Detail extends AppCompatActivity {
    ListView list;
    ArrayList<DetailType> listItem;
    String subjectname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            subjectname = b.getString("subject");
        }
        TextView t = (TextView) findViewById(R.id.textView8);
        t.setText(subjectname);
        list = (ListView) findViewById(R.id.listView2);
        listItem = new ArrayList<>();
        viewData();
    }

    public void viewData() {
        listItem.clear();
        SQLiteDatabase am = openOrCreateDatabase("am", android.content.Context.MODE_PRIVATE, null);
        String query = "select * from '" + subjectname + "'";
        Cursor cursor = am.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String date = cursor.getString(0);
                String time = cursor.getString(1);
                String stat = cursor.getString(2);
                String status = "";
                if (stat.equals("1")) {
                    status = "PRESENT";
                } else if (stat.equals("0")) {
                    status = "ABSENT";
                } else {
                    status = "CANCELLED";
                }
//            UpdateListType a=new UpdateListType("8:30","MATH","Attendance 0/0","You need to attend this class","75%","Present");
                DetailType a = new DetailType(date, time, status);
                listItem.add(a);
            }
        }
//        UpdateListType b=new UpdateListType("9:30","MATH","Attendance 0/0","You need to attend this class","75%","Present");
//        listItem.add(b);
//        UpdateListType c=new UpdateListType("7:30","MATH","Attendance 0/0","You need to attend this class","75%","Present");
//        listItem.add(c);
//        UpdateListType d=new UpdateListType("10:00","MATH","Attendance 0/0","You need to attend this class","75%","Present");
//        listItem.add(d);
//        UpdateListType e=new UpdateListType("1:30","MATH","Attendance 0/0","You need to attend this class","75%","Present");
//        listItem.add(e);
        DetailAdapter adapter = new DetailAdapter(this, R.layout.detail_layout, listItem);
        list.setAdapter(adapter);
    }
}
