package com.example.durakgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class Score extends AppCompatActivity {
    private BottomNavigationView bottomNavBar;
    DatabaseReference rfr;
    public int level;
    public ProgressBar progressBar;

    public int score;
    private DatabaseReference reference;
    public int setTxtCoin;
    public TextView coinLabel, levelLabel, xpLabel;
    public int highScore;
    @SuppressLint("StringFormatInvalid")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//hiding top bar
        setContentView(R.layout.activity_score);



        bottomNavBar = findViewById(R.id.bottomNavigation);

        bottomNavBar.setSelectedItemId(R.id.newgame);

        bottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.newgame:
                        startActivity(new Intent(getApplicationContext()
                                , NewGame.class ));
                        overridePendingTransition(1,2);
                        case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , Profile.class ));
                        overridePendingTransition(1,3);
                        return true;
                }
                return false;
            }
        });

        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScoreLabel = findViewById(R.id.highScoreLabel);
        coinLabel = findViewById(R.id.coinLabel);
        levelLabel = findViewById(R.id.levelLabel);
        progressBar = (ProgressBar) findViewById(R.id.horizontal_progress_bar);
        score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(getString(R.string.result_score, score));


      //  SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
      //  highScore = sharedPreferences.getInt("HIGH_SCORE", 0);


        setGMLevel();
        setGMCoin(score);
        setGMXP(score);
        setTxtCoin = score / 20;
        coinLabel.setText(getString(R.string.coin, setTxtCoin));

    }


    private void setGMCoin(int score) {

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference gameRef= userRef.child("coin");

        gameRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                int coin = mutableData.getValue(Integer.class);

                Log.i("TAG", "Old coin "+ coin);
                coin += score / 20;
                mutableData.setValue(coin);
                coin = mutableData.getValue(Integer.class);

                Log.i("TAG", "Updated value in function incrementAttendanceByOne "+Long.toString(coin));

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

    private int setGMXP(int scored) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference gameRef= userRef.child("xp");

        gameRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
               int xp = mutableData.getValue(Integer.class);
                xp += scored / 10;
                progressBar.setProgress(xp);
                mutableData.setValue(xp);
                xp = mutableData.getValue(Integer.class);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
        return scored;
    }


    private void setGMLevel() {

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference gameRef= userRef.child("level");

        gameRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                DatabaseReference levelRef = FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("xp");

                levelRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int level = mutableData.getValue(Integer.class);
                        int xp = dataSnapshot.getValue(Integer.class);
                        if(xp >= 100){
                            level += 1;
                            Log.i("TAG","LEVEL: "+ level);
                            mutableData.setValue(level);
                            reference = FirebaseDatabase.getInstance().getReference("Users");
                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("xp").setValue(0);
                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("level").setValue(level);
                           // reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("highscore").setValue(highScore);
                        }
                        levelLabel.setText(getString(R.string.level, level));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

    public void tryAgain(View view) {
        startActivity(new Intent(getApplicationContext(), EatBall.class));
    }

    @Override
    public void onBackPressed() {}
}