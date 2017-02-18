package com.example.alex.qtapandroid.common.database;

import com.example.alex.qtapandroid.common.database.buildings.Building;
import com.example.alex.qtapandroid.common.database.courses.Course;
import com.example.alex.qtapandroid.common.database.courses.OneClass;
import com.example.alex.qtapandroid.common.database.services.Service;
import com.example.alex.qtapandroid.common.database.users.User;

/**
 * Created by Carson on 18/02/2017.
 * Class to hold SQL statements. All are public static final.
 * Referenced from DbHelper only, this is just to limit the size of DbHelper.
 */
public class SqlStringStatements {

    public static final String DATABASE_NAME = "QTap.db";

    //create table statements
    public static final String CREATE_COURSES = "CREATE TABLE " + Course.TABLE_NAME + "(" +
            Course._ID + " INTEGER PRIMARY KEY," + Course.COLUMN_TITLE + " TEXT);";

    public static final String CREATE_BUILDINGS = "CREATE TABLE " + Building.TABLE_NAME + "(" +
            Building._ID + " INTEGER PRIMARY KEY," + Building.COLUMN_TITLE + " TEXT," +
            Building.COLUMN_ROOM_NUM + " TEXT," + Building.COLUMN_MONHOURS + " TEXT," +
            Building.COLUMN_TUESHOURS + " TEXT," + Building.COLUMN_WEDHOURS + " TEXT," +
            Building.COLUMN_THURSHOURS + " TEXT," + Building.COLUMN_FRIHOURS + " TEXT," +
            Building.COLUMN_CANBOOKROOMS + " TEXT," + Building.COLUMN_HASATM + " TEXT);";

    public static final String CREATE_USERS = "CREATE TABLE " + User.TABLE_NAME + "(" +
            User._ID + " INTEGER PRIMARY KEY," + User.COLUMN_NETID + " TEXT," +
            User.COLUMN_FIRST_NAME + " TEXT," + User.COLUMN_LAST_NAME + " TEXT," +
            User.COLUMN_DATE_INIT + " TEXT," + User.COLUMN_ICS_URL + " TEXT);";

    public static final String CREATE_CLASSES = "CREATE TABLE " + OneClass.TABLE_NAME + "(" +
            OneClass._ID + " INTEGER PRIMARY KEY," + OneClass.COLUMN_CLASS_TYPE + " TEXT," +
            OneClass.COLUMN_BUILDING_ID + " INT," + OneClass.COLUMN_ROOM_NUM + " TEXT," +
            OneClass.COLUMN_START_TIME + " TEXT," + OneClass.COLUMN_END_TIME + " TEXT," +
            OneClass.COLUMN_DAY + " TEXT," + OneClass.COLUMN_MONTH + " TEXT," + OneClass.COLUMN_YEAR +
            " TEXT," + OneClass.COLUMN_COURSE_ID + " INT );";

    public static final String CREATE_SERVICES = "CREATE TABLE" + Service.TABLE_NAME + "(" +
            Service._ID + " INTEGER PRIMARY KEY" + Service.COLUMN_HOURS + " TEXT," +
            Service.COLUMN_BUILDING_ID + " INT )";

    //Delete table statements
    public static final String DELETE_COURSES = "DROP TABLE IF EXISTS " + Course.TABLE_NAME;
    public static final String DELETE_BUILDINGS = "DROP TABLE IF EXISTS " + Building.TABLE_NAME;
    public static final String DELETE_USERS = "DROP TABLE IF EXISTS " + User.TABLE_NAME;
    public static final String DELETE_SERVICES = "DROP TABLE IF EXISTS " + Service.TABLE_NAME;
    public static final String DELETE_CLASSES = "DROP TABLE IF EXISTS " + OneClass.TABLE_NAME;
}
