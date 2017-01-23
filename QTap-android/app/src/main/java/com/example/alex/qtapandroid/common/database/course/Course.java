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
    public static final String COLUMN_ROOM_NUM = "roomNumber";
    public static final String COLUMN_STARTTIME = "startTime";
    public static final String COLUMN_ENDTIME = "endTime";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_YEAR = "year";

    //row number each field ends up in
    public static final int ID_POS = 0;
    public static final int TITLE_POS = 1;
    public static final int ROOM_NUM_POS = 2;
    public static final int STIME_POS = 3;
    public static final int ETIME_POS = 4;
    public static final int DAY_POS = 5;
    public static final int MONTH_POS = 6;
    public static final int YEAR_POS = 7;

    private String title;
    private String roomNum;
    private String startTime;
    private String endTime;
    private String Day;
    private String Month;
    private String Year;
    private long id;

    public Course(String title, String roomNum, String sTime, String eTime, String Day, String Month, String Year) {
        this.title = title;
        this.roomNum = roomNum;
        this.startTime = sTime;
        this.endTime = eTime;
        this.Day = Day;
        this.Month = Month;
        this.Year = Year;

    }

    public static void printCourses(ArrayList<Course> courses) {
        String output = "";
        for (int i = 0; i < courses.size(); i++) {
            output += System.getProperty("line.separator") +"COURSE id:" + courses.get(i).getID() + " title: " + courses.get(i).getTitle()
                    + " Location: " + courses.get(i).getRoomNum() + " Start Time: " + courses.get(i).getStartTime()+" End Time: " + courses.get(i).getEndTime() + " Day: " + courses.get(i).getDay() + " Month: " + courses.get(i).getMonth()+" Year: " + courses.get(i).getYear();
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

    public String getStartTime() {  return startTime;  }

    public String getEndTime() {  return endTime;  }

    public String getDay() {  return Day;  }

    public String getMonth() {  return Month;  }

    public String getYear() {  return Year;  }
}
