package com.example.gibo.assignment6;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.zip.Inflater;

/**
 * Created by gi bo on 2017-04-13.
 */

public class DataAdapter extends BaseAdapter {

    ArrayList<RestData> data;
    Context context;

    public DataAdapter(Context context,ArrayList<RestData> data){
        this.context = context;
        this.data = data;
    }



    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.customlistview, null);
        }
        TextView tv1= (TextView)convertView.findViewById(R.id.tv1);
        TextView tv2= (TextView)convertView.findViewById(R.id.tv2);
        ImageView img = (ImageView)convertView.findViewById(R.id.img);

        RestData info = data.get(position);
        tv1.setText(info.getname());
        tv2.setText(info.getPhone_num());
        if(info.getCate_num().equals("1")){
            img.setImageResource(R.drawable.chicken);
        }
        if(info.getCate_num().equals("2")){
            img.setImageResource(R.drawable.pizza);
        }
        if(info.getCate_num().equals("3")){
            img.setImageResource(R.drawable.hamburger);
        }

        return convertView;
    }

    Comparator<RestData> nameAsc = new Comparator<RestData>() {
        @Override
        public int compare(RestData o1, RestData o2) {
            return o1.getname().compareTo(o2.getname());
        }
    };

    public void setNameAsc(){
        Collections.sort(data,nameAsc);
        this.notifyDataSetChanged();
    }

    Comparator<RestData> typesort = new Comparator<RestData>() {
        @Override
        public int compare(RestData o1, RestData o2) {
            return o1.getCate_num().compareTo(o2.getCate_num());
        }
    };

    public void setType(){
        Collections.sort(data,typesort);
        this.notifyDataSetChanged();
    }
}
