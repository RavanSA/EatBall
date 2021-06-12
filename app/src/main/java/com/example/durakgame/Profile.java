package com.example.durakgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class Profile extends AppCompatActivity {
    GridView gridView;
    private TextView fullname, email;
    DatabaseReference reference;
    ProgressBar progressBarname,progressBaremail,progressBarpic;
    DatabaseReference databaseUsers;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private BottomNavigationView bottomNavBar;
    public ArrayList<HashMap<String, Object>> maplist = new ArrayList<>();
    private  ImageView profilepic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();//hiding top bar
        progressBarname = (ProgressBar)findViewById(R.id.progressname);
        progressBaremail = (ProgressBar)findViewById(R.id.progressemail);
        progressBarpic = (ProgressBar)findViewById(R.id.progresspic);
        progressBarname.setVisibility(View.VISIBLE);
        progressBaremail.setVisibility(View.VISIBLE);
        progressBarpic.setVisibility(View.VISIBLE);
        bottomNavBar = findViewById(R.id.bottomNavigation);
        fullname = (TextView)findViewById(R.id.fullname);
        profilepic = findViewById(R.id.profile_pic);

        email = (TextView) findViewById(R.id.email);
        reference = FirebaseDatabase.getInstance().
                getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.child("profilepic").getValue(String.class);
                Picasso.get().load(link).into(profilepic);

                String strfullname = snapshot.child("fullName").getValue(String.class);
                String stremail = snapshot.child("email").getValue(String.class);
                Log.d("My Activity", "Name: " + strfullname);
                    Log.d("My Activity", "Email: " + stremail);
                progressBarname.setVisibility(View.GONE);
                fullname.setText(strfullname);
                progressBaremail.setVisibility(View.GONE);
                email.setText(stremail);
                progressBarpic.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        fullname.setTextColor(Color.parseColor("#FFFFFF"));
        bottomNavBar.setSelectedItemId(R.id.home);

        bottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.newgame:
                        startActivity(new Intent(getApplicationContext()
                                , NewGame.class ));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                }
                return false;
            }
        });
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        databaseUsers = database.getReference("Users");
//        String id = mAuth.getCurrentUser().getUid();
//        DatabaseReference username = databaseUsers.child(id).child("fullName");
//
//        username.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                fullname = dataSnapshot.getValue().toString();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        RelativeLayout view = findViewById(R.id.myRectangleView);
//        TextView textView = new TextView(this);
//        textView.setText("Name: ");
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.MATCH_PARENT
//        );
//        textView.setLayoutParams(params);
//        view.addView(textView);

        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("title", "Chat Room");
            _item.put("icon", R.drawable.ic_baseline_chat_24);
            maplist.add(_item);
        }
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("title", "News");
            _item.put("icon",  R.drawable.newsicon);
            maplist.add(_item);
        }
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("title", "Rules");
            _item.put("icon",  R.drawable.icrules);
            maplist.add(_item);
        }
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("title", "Settings");
            _item.put("icon",  R.drawable.icsettings);
            maplist.add(_item);
        }
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("title", "Leaderboard");
            _item.put("icon",  R.drawable.ic_baseline_leaderboard_24);
            maplist.add(_item);
        }
        {
            HashMap<String, Object> _item = new HashMap<>();
            _item.put("title", "Share");
            _item.put("icon",  R.drawable.ic_baseline_share_24);
            maplist.add(_item);
        }



        gridView = (GridView) findViewById(R.id.gridView);
        Adapter adapter = new Adapter( getApplicationContext(), maplist );

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    startActivity(new Intent(getApplicationContext(), Chat.class));
                }
                if(position == 1){
                    startActivity(new Intent(getApplicationContext(), News.class));
                }
                if(position == 2){
                    startActivity(new Intent(getApplicationContext(), Rules.class));
                }
                if(position == 3){
                    startActivity(new Intent(getApplicationContext(), UpdateProfile.class));
                }
                if(position == 4){
                    startActivity(new Intent(getApplicationContext(), LeaderBoard.class));
                }
                if(position == 5){
                    startActivity(new Intent(getApplicationContext(), Share.class));
                }
            }
        });

//        logout = (Button) findViewById(R.id.logout);
//
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(Profile.this, MainActivity.class));
//            }
//        });
    }

    public class Adapter extends BaseAdapter {
        Context context;
        public ArrayList<HashMap<String, Object>> maplist;
        LayoutInflater Inflater;


        public Adapter(Context context2, ArrayList<HashMap<String, Object>> maplist){
            this.context = context2;
            this.maplist = maplist;
            Inflater = (LayoutInflater.from(context2));
        }

        @Override
        public int getCount() {
            return maplist.size();
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

        return convertView;*/
            View v = convertView;
            // Inflate the layout for each list item
            LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (v == null) {
                v = _inflater.inflate(R.layout.activity_grid, null);
            }
            // Get the TextView and ImageView from CustomView for displaying item
            TextView txtview = (TextView) v.findViewById(R.id.gridtext);
            ImageView imgview = (ImageView) v.findViewById(R.id.img);

            // Set the text and image for current item using data from map list
            txtview.setText(maplist.get(position).get("title").toString());
            imgview.setImageResource((Integer) maplist.get(position).get("icon"));

            return v;
        }
    }
}

