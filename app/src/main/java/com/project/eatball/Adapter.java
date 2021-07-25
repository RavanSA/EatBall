/* package com.example.durakgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter extends BaseAdapter {
    Context context;
    int icons[];
    LayoutInflater Inflater;


    public Adapter(Context context2, int[] icons){
        this.context = context2;
        this.icons = icons;
        Inflater = (LayoutInflater.from(context2));
    }

    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            /*convertView = Inflater.inflate(R.layout.activity_grid, null);
            ImageView icon = (ImageView) convertView.findViewById(R.id.img);

        icon.setImageResource(icons[position]); // set logo images

        return convertView;
        View v = convertView;
        // Inflate the layout for each list item
        LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (v == null) {
            v = _inflater.inflate(R.layout.activity_grid, null);
        }
        // Get the TextView and ImageView from CustomView for displaying item
        TextView txtview = (TextView) v.findViewById(R.id.img);
        ImageView imgview = (ImageView) v.findViewById(R.id.img);

        // Set the text and image for current item using data from map list
        txtview.setText(maplist.get(position).get("title").toString());
        imgview.setImageResource(maplist.get(position).get("icon"));
        return v;
    }
}*/

