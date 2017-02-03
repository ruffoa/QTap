package com.example.alex.qtapandroid.common.database.course;

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
    public static final String COLUMN_STARTTIME = "startTime";
    public static final String COLUMN_ENDTIME = "endTime";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_YEAR = "year";

    //column number each field ends up in
    public static final int ID_POS = 0;
    public static final int TITLE_POS = 1;
<<<<<<< HEAD
    public static final int ROOM_NUM_POS = 2;
    public static final int STIME_POS = 3;
    public static final int ETIME_POS = 4;
    public static final int DAY_POS = 5;
    public static final int MONTH_POS = 6;
    public static final int YEAR_POS = 7;
=======
    public static final int BUILDING_ID_POS = 2;
    public static final int ROOM_NUM_POS = 3;
    public static final int TIME_POS = 4;
>>>>>>> sqlitedatabase

    //fields in database
    private String title;
    private long buildingID;
    private String roomNum;
    private String startTime;
    private String endTime;
    private String Day;
    private String Month;
    private String Year;
    private long id;

<<<<<<< HEAD
    public Course(String title, String roomNum, String sTime, String eTime, String Day, String Month, String Year) {
=======
    public Course(String title, long building, String roomNum, String time) {
>>>>>>> sqlitedatabase
        this.title = title;
        this.buildingID = building;
        this.roomNum = roomNum;
        this.startTime = sTime;
        this.endTime = eTime;
        this.Day = Day;
        this.Month = Month;
        this.Year = Year;

    }

    /**
     * Prints out course information.
     *
     * @param courses ArrayList of courses to print out.
     */
    public static void printCourses(ArrayList<Course> courses) {
        String output = "";
        for (int i = 0; i < courses.size(); i++) {
<<<<<<< HEAD
            output += System.getProperty("line.separator") +"COURSE id:" + courses.get(i).getID() + " title: " + courses.get(i).getTitle()
                    + " Location: " + courses.get(i).getRoomNum() + " Start Time: " + courses.get(i).getStartTime()+" End Time: " + courses.get(i).getEndTime() + " Day: " + courses.get(i).getDay() + " Month: " + courses.get(i).getMonth()+" Year: " + courses.get(i).getYear();
=======
            output += " id:" + courses.get(i).getID() + " title: " + courses.get(i).getTitle()
                    + " building: " + courses.get(i).getBuildingID()
                    + " num: " + courses.get(i).getRoomNum() + " time: " + courses.get(i).getTime() + " ";
>>>>>>> sqlitedatabase
        }
        Log.d("SQLITE", "COURSES:" + output);
    }

    //getters and setters for fields
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

    public String getStartTime() {  return startTime;  }

    public String getEndTime() {  return endTime;  }

    public String getDay() {  return Day;  }

    public String getMonth() {  return Month;  }

    public String getYear() {  return Year;  }
}
