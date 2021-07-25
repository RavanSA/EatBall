package com.project.eatball;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewGame extends AppCompatActivity {
    private BottomNavigationView bottomNavBar;
    DatabaseReference rfr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        getSupportActionBar().hide();//hiding top bar
        TextView coinLabel = (TextView) findViewById(R.id.coinLabel);
        TextView levelLabel = (TextView) findViewById(R.id.levelLabel);
       // TextView highScoreLabel = (TextView) findViewById(R.id.highScoreLabel);
        rfr = FirebaseDatabase.getInstance().
                getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        rfr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int coin = snapshot.child("coin").getValue(Integer.class);
                int level = snapshot.child("level").getValue(Integer.class);
             //   int highscore = snapshot.child("highscore").getValue(Integer.class);
                coinLabel.setText(getString(R.string.coin, coin));
                levelLabel.setText(getString(R.string.level, level));
             //   highScoreLabel.setText(getString(R.string.highscorestart, highscore));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bottomNavBar = findViewById(R.id.bottomNavigation);

        bottomNavBar.setSelectedItemId(R.id.newgame);

        bottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.newgame:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , Profile.class ));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }


    public void startGame(View view) {
        startActivity(new Intent(getApplicationContext(), EatBall.class));
    }
}