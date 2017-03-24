package com.example.alex.qtapandroid.common.database.courses;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Carson on 07/02/2017.
 * Defines the schema for the oneClass table. Fields for
 * type of class (tutorial/lecture/lab), start/end times, building ID,
 * room number, day, month, year. Also references the Course table
 * so that each individual class is tied together to one course.
 */
public class OneClass implements BaseColumns {

    public static final String TABLE_NAME = "oneClass";
    public static final String COLUMN_CLASS_TYPE = "classType";
    public static final String COLUMN_BUILDING_ID = "buildingID";
    public static final String COLUMN_ROOM_NUM = "roomNumber";
    public static final String COLUMN_START_TIME = "startTime";
    public static final String COLUMN_END_TIME = "endTime";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_COURSE_ID = "courseID";

    //column number each field ends up in
    public static final int ID_POS = 0;
    public static final int CLASS_TYPE_POS = 1;
    public static final int BUILDING_ID_POS = 2;
    public static final int ROOM_NUM_POS = 3;
    public static final int STIME_POS = 4;
    public static final int ETIME_POS = 5;
    public static final int DAY_POS = 6;
    public static final int MONTH_POS = 7;
    public static final int YEAR_POS = 8;
    public static final int COURSE_ID_POS = 9;

    //fields in database
    private long id;
    private String type;
    private long buildingID;
    private String roomNum;
    private String startTime;
    private String endTime;
    private String day;
    private String month;
    private String year;
    private long courseID;

    public OneClass(String type, String roomNum, String startTime, String endTime, String day, String month, String year) {
        this.type = type;
        this.roomNum = roomNum;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public static void printClasses(ArrayList<OneClass> classes) {
        String output = "";
        for (int i = 0; i < classes.size(); i++) {
            output += System.getProperty("line.separator") + "COURSE id:" + classes.get(i).getID() +
                    " title: " + classes.get(i).getType() + " Location: " + classes.get(i).getRoomNum()
                    + " Start Time: " + classes.get(i).getStartTime() + " End Time: " +
                    classes.get(i).getEndTime() + " day: " + classes.get(i).getDay() + " month: " +
                    classes.get(i).getMonth() + " year: " + classes.get(i).getYear();
        }
        Log.d("SQLITE", output);
    }

    //getters and setters
    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

    public void setID(long id) {
        this.id = id;
    }

    public void setBuildingID(long buildingID) {
        this.buildingID = buildingID;
    }

    public long getID() {
        return id;
    }

    public String getType() {
        return type;
    }

    public long getBuildingID() {
        return buildingID;
    }
    
    public String getRoomNum() {
        return roomNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }
}
