package engsoc.qlife.database.local.courses.Course;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import engsoc.qlife.database.local.DatabaseManager;
import engsoc.qlife.database.local.DatabaseRow;

/**
 * Created by Carson on 21/01/2017.
 * Holds all information for the courses table.
 * Manages the courses within the database. Inserts/deletes rows and the entire table.
 */
public class CourseManager extends DatabaseManager {

    public CourseManager(Context context) {
        super(context);
    }

    @Override
    public void insertRow(DatabaseRow row) {
        if (row instanceof Course) {
            Course course = (Course) row;
            ContentValues values = new ContentValues();
            values.put(Course.COLUMN_TITLE, course.getTitle());
            getDatabase().insert(Course.TABLE_NAME, null, values);
        }
    }

    @Override
    public ArrayList<DatabaseRow> getTable() {
        String[] projection = {
                Course.ID,
                Course.COLUMN_TITLE
        };
        ArrayList<DatabaseRow> courses = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        try (Cursor cursor = getDatabase().query(Course.TABLE_NAME, projection, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                Course course = new Course(cursor.getInt(Course.ID_POS), cursor.getString(Course.TITLE_POS));
                courses.add(course);
            }
            cursor.close();
            return courses; //return only when the cursor has been closed
        }
    }

    @Override
    public Course getRow(long id) {
        String[] projection = {
                Course.ID,
                Course.COLUMN_TITLE
        };
        Course course;
        String selection = Course.ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = getDatabase().query(Course.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToNext()) {
                course = new Course(cursor.getInt(Course.ID_POS), cursor.getString(Course.TITLE_POS));
                cursor.close();
                return course; //return only when the cursor has been closed.
                //Return statement never missed, try block always finishes this.
            } else {
                return null;
            }
        }
    }

    @Override
    public void updateRow(DatabaseRow oldRow, DatabaseRow newRow) {
        if (oldRow instanceof Course && newRow instanceof Course) {
            Course oldCourse = (Course) oldRow;
            Course newCourse = (Course) newRow;
            ContentValues values = new ContentValues();
            values.put(Course.COLUMN_TITLE, newCourse.getTitle());
            String selection = Course.ID + " LIKE ?";
            String selectionArgs[] = {String.valueOf(oldCourse.getId())};
            getDatabase().update(Course.TABLE_NAME, values, selection, selectionArgs);
        }
    }
}