package com.example.alex.qtapandroid.common.database.users;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Carson on 29/01/2017.
 * Defines the schema for the Courses table. Currently holds a field for the class title,
 * room number, class time and ID.
 */
public class User implements BaseColumns {
    //table schema
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_NETID = "netid";
    public static final String COLUMN_FIRST_NAME = "firstName";
    public static final String COLUMN_LAST_NAME = "lastName";

    //column number each field ends up in
    public static final int ID_POS = 0;
    public static final int NETID_POS = 1;
    public static final int FIRST_NAME_POS = 2;
    public static final int LAST_NAME_POS = 3;

    //fields in database
    private String netid;
    private String firstName;
    private String lastName;
    private long id;

    public User(String netid, String firstName, String lastName) {
        this.netid = netid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static void printUsers(ArrayList<User> users){
        String output = "USERS:\n";
        for (int i=0; i<users.size(); i++){
            output += "ID: " + users.get(i).getID() + " NAME: " + users.get(i).getFirstName() + " "
                    + users.get(i).getLastName() + " NETID: " + users.get(i).getNetid() + "\n";
        }
        Log.d("SQLITE", output);
    }

    //getters for each field and setter for ID
    public String getNetid() {
        return netid;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }
}
