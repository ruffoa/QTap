package com.example.alex.qtapandroid.common.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Carson on 19/01/2017.
 * Manages creating/upgrading/downgrading the database.
 * Also holds static SQL query strings to create/delete the database.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4; //**NOTE** this must be incremented if you are
    //trying to run changes to the database schema

    private static DbHelper mInstance = null;

    public DbHelper(Context context) {
        super(context, SqlStringStatements.DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates an instance of the DBHelper. This method ensures that only one instance
     * of DBHelper can be created at once.
     *
     * @param context Context to create the DBHelper for.
     * @return returns the instance of DBHelper.
     */
    public static DbHelper getInstance(Context context) {
        if (mInstance == null) {
            //use application context so as to not accidentally leak application context in database.
            mInstance = new DbHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlStringStatements.CREATE_USERS);
        db.execSQL(SqlStringStatements.CREATE_COURSES);
        db.execSQL(SqlStringStatements.CREATE_BUILDINGS);
        db.execSQL(SqlStringStatements.CREATE_CLASSES);
        // db.execSQL(CREATE_SERVICES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //deletes the database and then re-creates the new version
        db.execSQL(SqlStringStatements.DELETE_USERS);
        db.execSQL(SqlStringStatements.DELETE_COURSES);
        db.execSQL(SqlStringStatements.DELETE_BUILDINGS);
        db.execSQL(SqlStringStatements.DELETE_CLASSES);
        // db.execSQL(DELETE_SERVICES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
