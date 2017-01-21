package com.example.alex.qtapandroid.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Carson on 19/01/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + Course.TABLE_NAME + " (" +
            Course._ID + " INTEGER PRIMARY KEY," +
            Course.COLUMN_TITLE + " TEXT," +
            Course.COLUMN_LOCATION + " TEXT, "+ Course.COLUMN_TIME+")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Course.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "QTap.db";

    private static DbHelper mInstance = null;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DbHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new DbHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addShop(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_TITLE, course.getTitle());
        values.put(Course.COLUMN_LOCATION, course.getLocation());
        values.put(Course.COLUMN_TIME, course.getTime());
        long newRowId = db.insert(Course.TABLE_NAME, null, values);
    }

    public ArrayList<Course> getTable(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                Course._ID,
                Course.COLUMN_TITLE,
                Course.COLUMN_LOCATION,
                Course.COLUMN_TIME
        };
        ArrayList<Course> courses = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        try (Cursor cursor = db.query(tableName, projection, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                Course course = new Course(cursor.getString(Course.TITLE_POS),
                        cursor.getString(Course.LOCATION_POS), cursor.getString(Course.TIME_POS));
                courses.add(course);
            }
        }
        return courses;
    }

    public void deleteClasses() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Course.TABLE_NAME, null, null);
    }

    public int updateShop() {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_TITLE, "mike");
        String selection = Course.COLUMN_TITLE + " LIKE ?";
        int count = db.update(Course.TABLE_NAME, values, selection, null);
        return count;
    }
}
