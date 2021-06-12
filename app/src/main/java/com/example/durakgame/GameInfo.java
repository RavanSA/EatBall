package com.example.durakgame;

public class GameInfo {

    private int level;
    private int highScore;
    private int coin;
    private int xp;
    public String userName;

    public GameInfo(String userName, int level, int highScore, int coin, int xp){
        this.level = level;
        this.highScore = highScore;
        this.coin = coin;
        this.xp = xp;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }



    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

}
