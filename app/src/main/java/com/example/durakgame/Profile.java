package com.example.durakgame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    GridView gridView;

    DatabaseReference databaseUsers;
    private FirebaseAuth mAuth;
    private String fullname;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    int icons[] = {
            R.drawable.friends,
            R.drawable.news,
            R.drawable.rules,
            R.drawable.settings,
            R.drawable.leaderboard,
            R.drawable.share,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseUsers = database.getReference("Users");
        String id = mAuth.getCurrentUser().getUid();
        DatabaseReference username = databaseUsers.child(id).child("fullName");

        username.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fullname = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        RelativeLayout view = findViewById(R.id.myRectangleView);
        TextView textView = new TextView(this);
        textView.setText("Name: "+fullname);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        textView.setLayoutParams(params);
        view.addView(textView);



        gridView = (GridView) findViewById(R.id.gridView);
        Adapter adapter = new Adapter( getApplicationContext(), icons );

        gridView.setAdapter(adapter);


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
}