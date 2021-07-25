package com.project.eatball;

import java.io.Serializable;

public class LeaderInfo implements Serializable {
    private String userName;
    private String level;

    private String imglink;


    public String getImglink() {
        return imglink;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }


    public LeaderInfo(String userName, String level, String imglink) {
        this.userName = userName;
        this.level = level;
        this.imglink = imglink;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
