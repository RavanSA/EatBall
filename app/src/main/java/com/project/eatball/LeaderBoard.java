package com.project.eatball;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class LeaderBoard extends AppCompatActivity {

        private DatabaseReference mleaderDatabase;
        Query mleaderDatabaseQuery;
        private final ArrayList<LeaderInfo> mRecycler = new ArrayList<>();
        public LeaderAdapter leaderadap;
        ProgressDialog progressDialog;
        String username = null,level = null,profilepic=null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_leader_board);
            Objects.requireNonNull(getSupportActionBar()).hide();//hiding top bar


            mleaderDatabase = FirebaseDatabase.getInstance().getReference();
            mleaderDatabaseQuery = mleaderDatabase.child("Users");
            RecyclerView recyclerView = findViewById(R.id.leaderrecycler);
            leaderadap = new LeaderAdapter(mRecycler,getApplicationContext());

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(leaderadap);
            recyclerView.setHasFixedSize(true);
            recyclerData();

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.show();

        }

        private void recyclerData(){
            mRecycler.clear();
            mleaderDatabaseQuery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                    allListData(dataSnapshot);
                    leaderadap.notifyDataSetChanged();
                    Log.i("TAG",""+dataSnapshot);
                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) { }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) { }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            });

        }

        @SuppressLint("NewApi")
        public void allListData(final DataSnapshot dataSnapshot){
            if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0) {
                HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                if (map.get("fullName") != null) {
                    username = Objects.requireNonNull(map.get("fullName")).toString();
                }
                if (map.get("level") != null) {
                    level = Objects.requireNonNull(map.get("level")).toString();
                    Log.i("TAG","LEVEL"+map.get("level"));

                }
                if (map.get("profilepic") != null){
                    profilepic = Objects.requireNonNull(map.get("profilepic")).toString();
                }

            }

            progressDialog.dismiss();

            mRecycler.add(new LeaderInfo(username,level,profilepic));
        }


    }
