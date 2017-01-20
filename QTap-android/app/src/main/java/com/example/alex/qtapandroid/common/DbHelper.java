package com.example.alex.qtapandroid.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carson on 19/01/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + Shop.TABLE_NAME + " (" +
            Shop._ID + " INTEGER PRIMARY KEY," +
            Shop.COLUMN_NAME + " TEXT," +
            Shop.COLUMN_ADDRESS + " TEXT)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Shop.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Businesses.db";

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

    public void addShop(String name, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Shop.COLUMN_NAME, name);
        values.put(Shop.COLUMN_ADDRESS, address);
        long newRowId = db.insert(Shop.TABLE_NAME, null, values);
    }

    public List getShopNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                Shop._ID,
                Shop.COLUMN_NAME,
                Shop.COLUMN_ADDRESS
        };

        String selection = Shop.COLUMN_NAME + " = ?";
        String[] selectionArgs = {""};



        List<String> shopNames = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        try (Cursor cursor = db.query(Shop.TABLE_NAME, projection, null, null, null, null, null)){
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(Shop.COLUMN_NAME));
                shopNames.add(name);
            }
        }
        return shopNames;
    }

    public void deleteShops() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = Shop.COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = {"Joe"};
        db.delete(Shop.TABLE_NAME, null, null);
    }

    public int updateShop() {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Shop.COLUMN_NAME, "mike");
        String selection = Shop.COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = {"Joe"};
        int count = db.update(Shop.TABLE_NAME, values, selection, selectionArgs);
        return count;
    }
}
