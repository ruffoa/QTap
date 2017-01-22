package com.example.alex.qtapandroid.common.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Carson on 21/01/2017.
 */

public class DatabaseAccessor {
    public SQLiteDatabase mDatabase;
    private DBHelper mDBHelper;
    private Context mContext;

    public DatabaseAccessor(Context context) {
        this.mContext = context;
        mDBHelper = DBHelper.getInstance(mContext);
        open();
    }

    public void open() throws SQLException {
        if (mDBHelper == null) {
            mDBHelper = DBHelper.getInstance(mContext);
        }
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void close() {
        mDBHelper.close();
        mDatabase = null;
    }
}
