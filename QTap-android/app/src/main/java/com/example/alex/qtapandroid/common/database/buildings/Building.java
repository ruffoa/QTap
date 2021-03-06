package com.example.alex.qtapandroid.common.database.buildings;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Carson on 19/01/2017.
 */
public class Building implements BaseColumns {
    //course that has 3 lectures and a lab gets 4 total Course classes
    public static final String TABLE_NAME = "building";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ROOM_NUM = "roomNumber";
    public static final String COLUMN_MONHOURS = "mondayHours";
    public static final String COLUMN_TUESHOURS = "tuesdayHours";
    public static final String COLUMN_WEDHOURS = "wednesdayHours";
    public static final String COLUMN_THURSHOURS = "thursdayHours";
    public static final String COLUMN_FRIHOURS = "fridayHours";
    public static final String COLUMN_CANBOOKROOMS = "canBookRooms";
    public static final String COLUMN_HASATM = "hasATM";

    //row number each field ends up in
    public static final int ID_POS = 0;
    public static final int TITLE_POS = 1;
    public static final int ROOM_NUM_POS = 2;
    public static final int MON_POS = 3;


    private String title;
    private String roomNum;
    private long id;

    public Building(String title, String roomNum) {
        this.title = title;
        this.roomNum = roomNum;
    }

//    public static void printCourses(ArrayList<com.example.alex.qtapandroid.common.database.course.Course> courses) {
//        String output = "";
//        for (int i = 0; i < courses.size(); i++) {
//            output += "COURSE id:" + courses.get(i).getID() + " title: " + courses.get(i).getTitle()
//                    + " num: " + courses.get(i).getRoomNum() + " time: " + courses.get(i).getTime()+" ";
//        }
//        Log.d("SQLITE", "INFO: " + output);
//    }

/*
 * Created by Carson on 22/01/2017.
 * Defines the schema for the Buildings table. Currently holds a field for the building name,
 * and ID.
 */



    /**
     * Prints out course information.
     *
     * @param buildings ArrayList of courses to print out.
     */
    public static void printBuildings(ArrayList<Building> buildings) {
        String output = "BUIDLINGS:\n";
        for (int i = 0; i < buildings.size(); i++) {
            output += " id:" + buildings.get(i).getID() + " title: "  + "\n";
        }
        Log.d("SQLITE", output);
    }

    //getters for each field and setter for ID
   // public String getName() {return name;}


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
}
