package com.example.durakgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Rooms extends AppCompatActivity {

    private BottomNavigationView bottomNavBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        getSupportActionBar().hide();//hiding top bar

/*
        bottomNavBar = findViewById(R.id.bottomNavigation);


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
                        startActivity(new Intent(getApplicationContext()
                                , Profile.class ));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });*/
    }
}