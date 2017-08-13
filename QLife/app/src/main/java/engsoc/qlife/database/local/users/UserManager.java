package engsoc.qlife.database.local.users;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import engsoc.qlife.database.local.DatabaseManager;
import engsoc.qlife.database.local.DatabaseRow;

/**
 * Created by Carson on 29/01/2017.
 * Holds all information for the courses table.
 * Manages the courses within the database. Inserts/deletes rows and the entire table.
 */
public class UserManager extends DatabaseManager {
    public UserManager(Context context) {
        super(context);
    }

    @Override
    public void insertRow(DatabaseRow row) {
        if (row instanceof User) {
            User user = (User) row;
            ContentValues values = new ContentValues();
            values.put(User.COLUMN_NETID, user.getNetid());
            values.put(User.COLUMN_FIRST_NAME, user.getFirstName());
            values.put(User.COLUMN_LAST_NAME, user.getLastName());
            values.put(User.COLUMN_DATE_INIT, user.getDateInit());
            values.put(User.COLUMN_ICS_URL, user.getIcsURL());
            getDatabase().insert(User.TABLE_NAME, null, values);
        }
    }

    @Override
    public ArrayList<DatabaseRow> getTable() {
        ArrayList<DatabaseRow> users = new ArrayList<>();
        //try with resources - automatically closes cursor whether or not its completed normally
        try (Cursor cursor = getDatabase().query(User.TABLE_NAME, null, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                User user = getRow(cursor.getInt(User.ID_POS));
                users.add(user);
            }
            cursor.close();
            return users; //return only when the cursor has been closed
        }
    }

    /**
     * Queries the Users table for a user with a given netID. Since netIDs are unique they
     * can be used to identify users.
     *
     * @param netid The netid searched for.
     * @return User class obtained from the table. Contains all information held in that row.
     */
    public User getRow(String netid) {
        String selection = User.COLUMN_NETID + " LIKE ?";
        String[] selectionArgs = {netid};
        try (Cursor cursor = getDatabase().query(User.TABLE_NAME, null, selection, selectionArgs, null, null, null)) {
            User user = null;
            if (cursor != null && cursor.moveToNext()) {
                user = new User(cursor.getInt(User.ID_POS), cursor.getString(User.NETID_POS), cursor.getString(User.FIRST_NAME_POS),
                        cursor.getString(User.LAST_NAME_POS), cursor.getString(User.DATE_INIT_POS), cursor.getString(User.ICS_URL_POS));
                cursor.close();
            }
            return user;
        }
    }

    @Override
    public User getRow(long id) {
        String[] projection = {
                User.ID,
                User.COLUMN_NETID,
                User.COLUMN_FIRST_NAME,
                User.COLUMN_LAST_NAME,
                User.COLUMN_DATE_INIT,
                User.COLUMN_ICS_URL
        };
        User user;
        String selection = User.ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        try (Cursor cursor = getDatabase().query(User.TABLE_NAME, projection, selection, selectionArgs, null, null, null)) {
            cursor.moveToNext();
            user = new User(cursor.getInt(User.ID_POS), cursor.getString(User.NETID_POS), cursor.getString(User.FIRST_NAME_POS),
                    cursor.getString(User.LAST_NAME_POS), cursor.getString(User.DATE_INIT_POS), cursor.getString(User.ICS_URL_POS));
            cursor.close();
            return user; //return only when the cursor has been closed.
            //Return statement never missed, try block always finishes this.
        }

    }

    @Override
    public void updateRow(DatabaseRow oldRow, DatabaseRow newRow) {
        if (oldRow instanceof User && newRow instanceof User) {
            User oldUser = (User) oldRow;
            User newUser = (User) newRow;
            ContentValues values = new ContentValues();
            values.put(User.COLUMN_NETID, newUser.getNetid());
            values.put(User.COLUMN_FIRST_NAME, newUser.getFirstName());
            values.put(User.COLUMN_LAST_NAME, newUser.getLastName());
            values.put(User.COLUMN_DATE_INIT, newUser.getDateInit());
            values.put(User.COLUMN_ICS_URL, newUser.getIcsURL());

            String selection = User.ID + " LIKE ?";
            String selectionArgs[] = {String.valueOf(oldUser.getId())};
            getDatabase().update(User.TABLE_NAME, values, selection, selectionArgs);
        }
    }
}
