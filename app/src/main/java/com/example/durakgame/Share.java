package com.example.durakgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Share extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<ShareUtils> personUtilsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        getSupportActionBar().hide();
        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        personUtilsList = new ArrayList<>();

        //Adding Data into ArrayList
        personUtilsList.add(new ShareUtils("Facebook",R.drawable.facebook));
        personUtilsList.add(new ShareUtils("Instagram",R.drawable.insta));
        personUtilsList.add(new ShareUtils("Twitter",R.drawable.twit));
        personUtilsList.add(new ShareUtils("Messenger",R.drawable.messenger));
        personUtilsList.add(new ShareUtils("Linkedin",R.drawable.linkedin));
        personUtilsList.add(new ShareUtils("Pinterest",R.drawable.pinterest));

        mAdapter = new CustomRecyclerAdapter(this, personUtilsList);

        recyclerView.setAdapter(mAdapter);

    }
}