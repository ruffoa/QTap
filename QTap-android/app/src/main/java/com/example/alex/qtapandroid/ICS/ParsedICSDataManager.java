package com.example.alex.qtapandroid.ICS;

import android.content.Context;
import android.util.Log;

import com.example.alex.qtapandroid.ui.fragments.StudentToolsFragment;

import java.util.List;

/**
 * Created by Alex on 1/22/2017.
 */

public class ParsedICSDataManager {

    private Context mContext;
    private boolean isEvent;
    public static final String TAG = StudentToolsFragment.class.getSimpleName();

    public static final String mPath = "testCal.ics";
    private ParseICS mParser;
    private List<String> mLines;

    public ParsedICSDataManager(Context context) {
        this.mContext = context;
    }

    public void AddDataToTable() {
        boolean isEvent = false;
        String sTime = "", eTime = "", loc = "", name = "";
        int hour = 0, minute = 0, day = 0, month = 0;
        int shour = 0, sminute = 0, sday = 0, smonth = 0;

        mParser = new ParseICS(this.mContext.getApplicationContext());
        mLines = mParser.readLine(mPath);
        for (String string : mLines) {

            if (string.contains("BEGIN:VEVENT")) {
                isEvent = true;
            } else if (string.contains("END:VEVENT")) {
                isEvent = false;
//            eventData.append(System.getProperty("line.separator"));
//            eventData.append(System.getProperty("line.separator") + "Event Name: " + name);
//            eventData.append(System.getProperty("line.separator") + "Event Location: " + loc);
//            eventData.append(System.getProperty("line.separator") + "Starts: " + smonth + "/" + sday + " at " + shour + ":" + sminute);
//            eventData.append(System.getProperty("line.separator") + "Ends: " + month + "/" + day + " at " + hour + ":" + minute);


            } else if (isEvent) {
                if (string.contains("LOCATION"))
                    loc = string.substring(9);
                else if (string.contains("DTSTART")) {
                    sTime = string.replaceAll("[^0-9]", "");
                    shour = Integer.parseInt(sTime.substring(8, 10));
                    sminute = Integer.parseInt(sTime.substring(10, 12));
                    sday = Integer.parseInt(sTime.substring(6, 8));
                    smonth = Integer.parseInt(sTime.substring(4, 6));

                } else if (string.contains("DTEND")) {
                    eTime = string.replaceAll("[^0-9]", "");
                    hour = Integer.parseInt(eTime.substring(8, 10));
                    minute = Integer.parseInt(eTime.substring(10, 12));
                    day = Integer.parseInt(eTime.substring(6, 8));
                    month = Integer.parseInt(eTime.substring(4, 6));
                } else if (string.contains("SUMMARY")) {
                    name = (string.substring(string.lastIndexOf(":") + 1));
                }

            }

            Log.d(TAG, string);
        }
    }
}

