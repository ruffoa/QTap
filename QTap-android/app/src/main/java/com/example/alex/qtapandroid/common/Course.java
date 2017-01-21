package com.example.alex.qtapandroid.common;

import android.provider.BaseColumns;

/**
 * Created by Carson on 19/01/2017.
 */
public class Course implements BaseColumns {
    public static final String TABLE_NAME = "class";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_TIME = "time";
    //row number each field ends up in
    public static final int TITLE_POS = 1;
    public static final int LOCATION_POS = 2;
    public static final int TIME_POS = 3;
    private String title;
    private String location;
    private String time;

    public Course(String title, String location, String time) {
        this.title = title;
        this.location = location;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String TITLE) {
        this.title = TITLE;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String LOCATION) {
        this.location = LOCATION;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String TIME) {
        this.time = TIME;
    }
}
