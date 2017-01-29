package com.example.alex.qtapandroid.common.database.building;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Carson on 22/01/2017.
 * Defines the schema for the Buildings table. Currently holds a field for the building name,
 * and ID.
 */
public class Building implements BaseColumns {
    //table schema
    public static final String TABLE_NAME = "buildings";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ESTABLISMENTS = "establishments";

    //column number each field ends up in
    public static final int ID_POS = 0;
    public static final int NAME_POS = 1;
    public static final int ESTAB_POS = 2;

    //fields in database
    private long id;
    private String name;

    public Building(String name) {
        this.name = name;
    }

    /**
     * Prints out course information.
     *
     * @param buildings ArrayList of courses to print out.
     */
    public static void printBuildings(ArrayList<Building> buildings) {
        String output = "";
        for (int i = 0; i < buildings.size(); i++) {
            output += " id:" + buildings.get(i).getID() + " title: " + buildings.get(i).getName();
        }
        Log.d("SQLITE", "BUILDINGS:" + output);
    }

    public String getName() {
        return name;
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }
}
