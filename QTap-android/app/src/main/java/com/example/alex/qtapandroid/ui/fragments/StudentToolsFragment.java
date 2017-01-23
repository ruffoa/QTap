package com.example.alex.qtapandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.classes.icsParser;
import com.example.alex.qtapandroid.common.database.course.Course;
import com.example.alex.qtapandroid.common.database.course.CourseManager;

import java.util.List;

import static android.R.attr.value;


/**
 * Created by Carson on 02/12/2016.
 */

public class StudentToolsFragment extends Fragment {

    public static final String TAG = StudentToolsFragment.class.getSimpleName();

    public static final String mPath = "testCal.ics";
    private icsParser mParser;
    private List<String> mLines;

    private CourseManager mCourseManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_tools,container,false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView dataInfo = (TextView) getView().findViewById(R.id.parsingTestText);
        dataInfo.setMovementMethod(new ScrollingMovementMethod());
        dataInfo.setText("Initializing Database");
        setup();

//        dataInfo.setText("Database created, 1 Record inserted");
//        dataInfo.setText("Information set to above textview");
    }

    public void setup(){
        TextView dataInfo = (TextView) getView().findViewById(R.id.parsingTestText);
        TextView eventData = (TextView) getView().findViewById(R.id.testEventDetails);
        eventData.setMovementMethod(new ScrollingMovementMethod());
        mCourseManager = new CourseManager(this.getContext());
        mCourseManager.deleteTable();

        boolean isEvent = false;
        String sTime = "" ,eTime = "", loc = "", name = "";
        int hour = 0, minute = 0, day = 0, month = 0, year = 0;
        int shour = 0, sminute = 0, sday = 0, smonth = 0;

        mParser = new icsParser(this.getContext());
        mLines = mParser.readLine(mPath);
        for (String string : mLines) {

            if (string.contains("BEGIN:VEVENT"))
            {
                isEvent = true;
                dataInfo.append("\n" + "New Event");
            }
            else if (string.contains("END:VEVENT")) {
                isEvent = false;
                eventData.append(System.getProperty("line.separator"));
                eventData.append(System.getProperty("line.separator") +"Event Name: " + name);
                eventData.append(System.getProperty("line.separator") +"Event Location: " + loc);
                eventData.append(System.getProperty("line.separator") +"Starts: " + smonth + "/" + sday + " at " + shour + ":" + sminute);
                eventData.append(System.getProperty("line.separator") +"Ends: " + month + "/" + day + " at " + hour + ":" + minute);

                /// Inserting to Database

                String tempTime = Integer.toString(shour) + ":" + Integer.toString(sminute);
                String tempEndTime = Integer.toString(hour) + ":" + Integer.toString(minute);
                Course one = new Course(name, loc, tempTime, tempEndTime, Integer.toString(sday), Integer.toString(smonth), Integer.toString(year));
                one.setID(mCourseManager.insertRow(one));

                Log.d(TAG, "Event Date =>  Year: " + Integer.toString(year) + " Month: " + Integer.toString(month) + " Day: "+ Integer.toString(day));

            }
            else if(isEvent)
            {
                if (string.contains("LOCATION"))
                    loc = string.substring(9);
                else if (string.contains("DTSTART"))
                {
                    sTime = string.replaceAll("[^0-9]", "");
                    shour = Integer.parseInt(sTime.substring(8, 10));
                    sminute = Integer.parseInt(sTime.substring(10, 12));
                    sday = Integer.parseInt(sTime.substring(6, 8));
                    smonth = Integer.parseInt(sTime.substring(4, 6));
                    year = Integer.parseInt(sTime.substring(0, 4));
                }
                else if (string.contains("DTEND"))
                {
                    eTime = string.replaceAll("[^0-9]", "");
                    hour = Integer.parseInt(eTime.substring(8, 10));
                    minute = Integer.parseInt(eTime.substring(10, 12));
                    day = Integer.parseInt(eTime.substring(6, 8));
                    month = Integer.parseInt(eTime.substring(4, 6));

                }
                else if (string.contains("SUMMARY"))
                {
                    name = (string.substring(string.lastIndexOf(":") + 1));
                }

                dataInfo.append("\n"+string);

            }

//            Log.d(TAG, string);

        }

    }
}