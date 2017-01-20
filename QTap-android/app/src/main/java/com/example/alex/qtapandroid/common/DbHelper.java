package com.example.alex.qtapandroid.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carson on 19/01/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + Shop.TABLE_NAME + " (" +
            Shop._ID + " INTEGER PRIMARY KEY," +
            Shop.ROW_NAME + " TEXT," +
            Shop.ROW_ADDRESS + " TEXT)";

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

    public void addShop(Shop shop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Shop.ROW_NAME, shop.getName());
        values.put(Shop.ROW_ADDRESS, shop.getAddress());
        long newRowId = db.insert(Shop.TABLE_NAME, null, values);
    }

    public ArrayList<Shop> getTable(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                Shop._ID,
                Shop.ROW_NAME,
                Shop.ROW_ADDRESS
        };
        ArrayList<Shop> shops = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        try (Cursor cursor = db.query(tableName, projection, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                Shop shop = new Shop(cursor.getString(Shop.NAME_POS), cursor.getString(Shop.ADDRESS_POS));
                shops.add(shop);
            }
        }
        return shops;
    }

    public void deleteShops() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = Shop.ROW_NAME + " LIKE ?";
        String[] selectionArgs = {"Joe"};
        db.delete(Shop.TABLE_NAME, null, null);
    }

    public int updateShop() {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Shop.ROW_NAME, "mike");
        String selection = Shop.ROW_NAME + " LIKE ?";
        String[] selectionArgs = {"Joe"};
        int count = db.update(Shop.TABLE_NAME, values, selection, selectionArgs);
        return count;
    }
}
