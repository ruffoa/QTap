package com.example.alex.qtapandroid.common.database.courses;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Carson on 19/01/2017.
 * Defines the schema for the Courses table. Currently holds a field for the class title,
 * room number, class time and ID.
 * **Note** Each lecture/lab/studio needs an entry, so 'Course' is a misnomer.
 */
public class Course implements BaseColumns {
    //table schema
    public static final String TABLE_NAME = "courses";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_BUILDING_ID = "buildingID";
    public static final String COLUMN_ROOM_NUM = "roomNumber";
    public static final String COLUMN_TIME = "time";

    //column number each field ends up in
    public static final int ID_POS = 0;
    public static final int TITLE_POS = 1;
    public static final int BUILDING_ID_POS = 2;
    public static final int ROOM_NUM_POS = 3;
    public static final int TIME_POS = 4;

    //fields in database
    private String title;
    private long buildingID;
    private String roomNum;
    private String time;
    private long id;

    public Course(String title, long building, String roomNum, String time) {
        this.title = title;
        this.buildingID = building;
        this.roomNum = roomNum;
        this.time = time;
    }

    /**
     * Prints out course information.
     *
     * @param courses ArrayList of courses to print out.
     */
    public static void printCourses(ArrayList<Course> courses) {
        String output = "COURSES:\n";
        for (int i = 0; i < courses.size(); i++) {
            output += " id:" + courses.get(i).getID() + " title: " + courses.get(i).getTitle()
                    + " building: " + courses.get(i).getBuildingID()
                    + " num: " + courses.get(i).getRoomNum() + " time: " + courses.get(i).getTime() + "\n";
        }
        Log.d("SQLITE",output);
    }

    //getters for each field and setter for ID
    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public long getBuildingID() {
        return buildingID;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public String getTime() {
        return time;
    }
}
