package com.example.durakgame;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.durakgame.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<DataModal> {

    // constructor for our list view adapter.
    public NewsAdapter(@NonNull Context context, ArrayList<DataModal> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.single_news_list, parent, false);
        }

        DataModal dataModal = getItem(position);

        TextView nameTV = listitemView.findViewById(R.id.newstitle);
        TextView text= listitemView.findViewById(R.id.newstext);
        ImageView courseIV = listitemView.findViewById(R.id.idIVimage);

        nameTV.setText(dataModal.getName());
        text.setText(dataModal.getText());

        Picasso.get().load(dataModal.getImgUrl()).into(courseIV);

        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return listitemView;
    }
}
