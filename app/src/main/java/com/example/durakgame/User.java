package com.example.durakgame;

import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

public class User {
    public String fullName, email, phoneNO,gender;
    public String DOB;
    public String profilepic;
    public int level, coin, xp;
    public User(String fullName, String email, String phoneNO, String gender,
                String DOB,String profilepic, int level, int coin, int xp){
        this.phoneNO = phoneNO;
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.DOB = DOB;
        this.profilepic = profilepic;
        this.level = level;
        this.coin = coin;
        this.xp = xp;
    }
}
