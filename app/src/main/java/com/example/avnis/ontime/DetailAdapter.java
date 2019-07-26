package com.example.avnis.ontime;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by avnis on 7/26/2019.
 */
public class DetailAdapter extends ArrayAdapter<DetailType> {

    private LayoutInflater mInflater;
    private ArrayList<DetailType> list;
    private Context mContext;
    private int mResource;

    public DetailAdapter(Context context, int resource, ArrayList<DetailType> list) {
        super(context, resource, list);
        this.list=list;

        mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }
    @Override

    public View getView(int position, View view, ViewGroup parent) {

        view = mInflater.inflate(R.layout.detail_layout, null);
        DetailType listset = (DetailType) list.get(position);
        LinearLayout l = (LinearLayout) view.findViewById(R.id.linearlayout);
        TextView timev = (TextView) view.findViewById(R.id.list_content7);
        TextView datev = (TextView) view.findViewById(R.id.list_content8);
        TextView statusv = (TextView) view.findViewById(R.id.list_content12);
        timev.setText(listset.getTime());
        datev.setText(listset.getDate());
        statusv.setText(listset.getStatus());
        if(listset.getStatus().equals("PRESENT"))
        {
            statusv.setTextColor(Color.GREEN);
        }
        else if(listset.getStatus().equals("ABSENT"))
        {
            statusv.setTextColor(Color.RED);
        }
        else if(listset.getStatus().equals("CANCELLED"))
        {
            statusv.setTextColor(Color.YELLOW);
        }
        return view;
    }

}
