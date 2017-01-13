package com.example.alex.qtapandroid.common;

import com.orm.SugarRecord;

/**
 * Created by Alex on 1/10/2017.
 */

public class DataRecords  extends SugarRecord {
//    String title = "";
//    String location = "";
//    String startTime = "2016.01.01.12.30";
//    String endTime = "2016.01.01.12.30";
//    int studentNumber = 0;

    String title;
    String location;
    String startTime;
    String endTime;
    int studentNumber;


    public DataRecords(){

    }

    public DataRecords(String title, String location, String startTime, String endTime, int studentNumber){
        this.title = title;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.studentNumber = studentNumber;
    }

}