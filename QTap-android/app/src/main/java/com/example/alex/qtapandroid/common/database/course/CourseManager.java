package com.example.alex.qtapandroid.common.database.course;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alex.qtapandroid.common.database.DatabaseAccessor;

import java.util.ArrayList;

/**
 * Created by Carson on 21/01/2017.
 */

public class CourseManager extends DatabaseAccessor {

    public CourseManager(Context context) {
        super(context);
    }

    public long insertRow(Course course) {
        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_TITLE, course.getTitle());
        values.put(Course.COLUMN_ROOM_NUM, course.getRoomNum());
        values.put(Course.COLUMN_STARTTIME, course.getStartTime());
        values.put(Course.COLUMN_ENDTIME, course.getEndTime());

        return mDatabase.insert(Course.TABLE_NAME, null, values);
    }

    public void deleteRow(Course course) {
        String selection = Course._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(course.getID())};
        mDatabase.delete(Course.TABLE_NAME, selection, selectionArgs);
    }

    public ArrayList<Course> getTable() {
        String[] projection = {
                Course._ID,
                Course.COLUMN_TITLE,
                Course.COLUMN_ROOM_NUM,
                Course.COLUMN_STARTTIME,
                Course.COLUMN_ENDTIME
        };
        ArrayList<Course> courses = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        try (Cursor cursor = mDatabase.query(Course.TABLE_NAME, projection, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                Course course = new Course(cursor.getString(Course.TITLE_POS),
                        cursor.getString(Course.ROOM_NUM_POS), cursor.getString(Course.STIME_POS), cursor.getString(Course.ETIME_POS));
                course.setID(cursor.getInt(Course.ID_POS));
                courses.add(course);
            }
            cursor.close();
            return courses; //return only when the cursor has been closed
        }
    }

    public Course getRow(long id) {
        String[] projection = {
                Course._ID,
                Course.COLUMN_TITLE,
                Course.COLUMN_ROOM_NUM,
                Course.COLUMN_STARTTIME,
                Course.COLUMN_ENDTIME
        };
        Course course;
        String selection = Course._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = mDatabase.query(Course.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            cursor.moveToNext();
            course = new Course(cursor.getString(Course.TITLE_POS),
                    cursor.getString(Course.ROOM_NUM_POS), cursor.getString(Course.STIME_POS), cursor.getString((Course.ETIME_POS)));
            course.setID(cursor.getInt(Course.ID_POS));
            cursor.close();
            return course; //return only when the cursor has been closed
        }
    }

    public void deleteTable() {
        mDatabase.delete(Course.TABLE_NAME, null, null);
    }

    public Course updateRow(Course oldCourse, Course newCourse) {
        ContentValues values = new ContentValues();
        values.put(Course.COLUMN_TITLE, newCourse.getTitle());
        values.put(Course.COLUMN_ROOM_NUM, newCourse.getRoomNum());
        values.put(Course.COLUMN_STARTTIME, newCourse.getStartTime());
        values.put(Course.COLUMN_ENDTIME, newCourse.getEndTime());
        String selection = Course._ID + " LIKE ?";
        String selectionArgs[] = {String.valueOf(oldCourse.getID())};
        mDatabase.update(Course.TABLE_NAME, values, selection, selectionArgs);
        newCourse.setID(oldCourse.getID());
        return newCourse;
    }
}