package com.example.alex.qtapandroid.common.database.course;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Carson on 19/01/2017.
 */
public class Course implements BaseColumns {
    //course that has 3 lectures and a lab gets 4 total Course classes
    public static final String TABLE_NAME = "class";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ROOM_NUM = "location";
    public static final String COLUMN_TIME = "time";

    //row number each field ends up in
    public static final int ID_POS = 0;
    public static final int TITLE_POS = 1;
    public static final int ROOM_NUM_POS = 2;
    public static final int TIME_POS = 3;

    private String title;
    private String roomNum;
    private String time;
    private long id;

    public Course(String title, String roomNum, String time) {
        this.title = title;
        this.roomNum = roomNum;
        this.time = time;
    }

    public static void printCourses(ArrayList<Course> courses) {
        String output = "";
        for (int i = 0; i < courses.size(); i++) {
            output += "COURSE id:" + courses.get(i).getID() + " title: " + courses.get(i).getTitle()
                    + " num: " + courses.get(i).getRoomNum() + " time: " + courses.get(i).getTime()+" ";
        }
        Log.d("SQLITE", "INFO: " + output);
    }


    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public String getTime() {
        return time;
    }
}
