package com.example.alex.qtapandroid.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import java.util.Date;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.classes.icsParser;
import com.example.alex.qtapandroid.common.database.course.Course;
import com.example.alex.qtapandroid.common.database.course.CourseManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Created by Carson on 02/12/2016.
 * Fragment used to control UI of the calendar.
 * Other scheduling information could be added to other parts of the screen.
 * This is the first screen user sees upon logging in (unless first time login).
 * Attached to MainTabActivity only.
 */
public class CalendarFragment extends Fragment {
    private CourseManager mCourseManager;
    public static final String TAG = StudentToolsFragment.class.getSimpleName();
    public static final String mPath = "testCal.ics";
    private icsParser mParser;
    private List<String> mLines;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setup();
        return inflater.inflate(R.layout.fragment_calendar, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView dataInfo = (TextView) getView().findViewById(R.id.calendarEvents);
        DatePicker dateSel = (DatePicker) getView().findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        dateSel.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
//                        Log.d("Date", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);
                        getData();
                    }
                }
        );

        dataInfo.setMovementMethod(new ScrollingMovementMethod());
        dataInfo.setText("Initializing Database");
        getData();
    }

    public void getData() {
        mCourseManager = new CourseManager(this.getContext());
        TextView dataInfo = (TextView) getView().findViewById(R.id.calendarEvents);
        DatePicker dateSel = (DatePicker) getView().findViewById(R.id.datePicker);

        ArrayList<Course> data = mCourseManager.getTable();

//        String output = "";               //For Debugging purposes
//        for (int i = 0; i < data.size(); i++) {
//            output = System.getProperty("line.separator") + "COURSE id:" + data.get(i).getID() + " title: " + data.get(i).getTitle()
//                    + " Location: " + data.get(i).getRoomNum() + " Start Time: " + data.get(i).getStartTime() + " End Time: " + data.get(i).getEndTime() + " Day: " + data.get(i).getDay() + " Month: " + data.get(i).getMonth() + " Year: " + data.get(i).getYear();
//        }
//        Log.d("SQLITE", "INFO: " + output);
        dataInfo.setText("Event Information for " + dateSel.getMonth() + 1 + "/" + dateSel.getDayOfMonth());

        int day = 0, month = 0, year = 0;
        boolean isInfo = false;
        for (int i = 0; i < data.size(); i++) {
            day = Integer.parseInt(data.get(i).getDay());
            month = Integer.parseInt(data.get(i).getMonth());
            year = Integer.parseInt(data.get(i).getYear());
//            Log.d("Date", "Year=" + year + " Month=" + month + " day=" + day + " Data To String: " + data.get(i).getTitle());

            if (year == dateSel.getYear() && month == (dateSel.getMonth() + 1) && dateSel.getDayOfMonth() == day) {
                dataInfo.append(System.getProperty("line.separator") + "Event Name: " + data.get(i).getTitle() + " Location: " + data.get(i).getRoomNum() + " at: " + data.get(i).getStartTime() + " to " + data.get(i).getEndTime());
                isInfo = true;
            }

        }

        if (isInfo == false)
        {
            dataInfo.setText("No Data Found; has the database been initialized?");
        }
    }

    public void setup() {
//        TextView dataInfo = (TextView) getView().findViewById(R.id.parsingTestText);
//        TextView eventData = (TextView) getView().findViewById(R.id.testEventDetails);
//        eventData.setMovementMethod(new ScrollingMovementMethod());
        mCourseManager = new CourseManager(this.getContext());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());    // Get the default SharedPreferences context

//        if (mCourseManager.getTable().isEmpty() == true ||  preferences.getString("DatabaseDate", "noData") != "noData" ) {
            mCourseManager.deleteTable();


            boolean isEvent = false;
            String sTime = "", eTime = "", loc = "", name = "", rTime;
            int hour = 0, minute = 0, day = 0, month = 0, year = 0;
            int shour = 0, sminute = 0, sday = 0, smonth = 0;
            int rday = 0, rmonth = 0, ryear = 0;
            boolean repeatWeekly = false;
            String rDayStr = "", rMonStr = "", rYrStr = "", rHrStr = "", rMinStr ="", rSecStr = "";

            mParser = new icsParser(this.getContext());
//            mLines = mParser.readLine(mPath);
            mLines = mParser.readDownloadFile("cal.ics");

            for (String string : mLines) {

                if (string.contains("BEGIN:VEVENT")) {
                    isEvent = true;
//                dataInfo.append("\n" + "New Event");
                } else if (string.contains("END:VEVENT")) {
                    isEvent = false;
//                eventData.append(System.getProperty("line.separator"));
//                eventData.append(System.getProperty("line.separator") +"Event Name: " + name);
//                eventData.append(System.getProperty("line.separator") +"Event Location: " + loc);
//                eventData.append(System.getProperty("line.separator") +"Starts: " + smonth + "/" + sday + " at " + shour + ":" + sminute);
//                eventData.append(System.getProperty("line.separator") +"Ends: " + month + "/" + day + " at " + hour + ":" + minute);

                    /// Inserting to Database

                    String tempTime = Integer.toString(shour) + ":" + Integer.toString(sminute);
                    String tempEndTime = Integer.toString(hour) + ":" + Integer.toString(minute);
                    Course one = new Course(name, loc, tempTime, tempEndTime, Integer.toString(sday), Integer.toString(smonth), Integer.toString(year));
                    one.setID(mCourseManager.insertRow(one));

                    if (repeatWeekly == true)
                    {
                        String endDateString = rYrStr + "-" + rMonStr + "-" + rDayStr + " " + rHrStr +":" + rMinStr + ":" + rSecStr;

                        // get the supported ids for GMT-08:00 (Pacific Standard Time)
                        String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
                        // if no ids were returned, something is wrong. get out.
                        if (ids.length == 0)
                            System.exit(0);

                        // begin output
                        System.out.println("Current Time");

                        // create a Pacific Standard Time time zone
                        SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);

                        // set up rules for Daylight Saving Time
                        pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
                        pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

                        // create a GregorianCalendar with the Pacific Daylight time zone
                        // and the current date and time
                        Calendar cal2 = new GregorianCalendar(pdt);
                        Calendar cal = new GregorianCalendar(pdt);

                        cal.set(year, smonth -1, sday);

                        cal2.set(Integer.parseInt(rYrStr), Integer.parseInt(rMonStr) - 1, Integer.parseInt(rDayStr));

                        Log.d(TAG, "Cal 2 Date: " + cal2.get(Calendar.DAY_OF_MONTH) + "/" + cal2.get(Calendar.MONTH) + "/" + cal2.get(Calendar.YEAR));
                        Date endDate = cal2.getTime();
                        Date date1 = cal.getTime();
                        int ctr = 0;

                        while (date1.before(endDate))
                        {
                            cal.add(Calendar.DATE, 7);
                            date1 = cal.getTime();
                            sday = cal.get(Calendar.DAY_OF_MONTH);
                            smonth = cal.get(Calendar.MONTH);
                            year = cal.get(Calendar.YEAR);
                            ctr += 1;

                            Log.d(TAG, "Repeated Event Date =>  Year: " + Integer.toString(year) + " Month: " + Integer.toString(smonth) + " Day: "+ Integer.toString(sday) + " Name: " + name + " At: " + loc + " *DATE* = " + rday + "/" + rmonth + "/" + ryear + " End Date: " + endDateString + " CTR " + ctr);

                            one = new Course(name, loc, tempTime, tempEndTime, Integer.toString(sday), Integer.toString(smonth + 1), Integer.toString(year));
                            one.setID(mCourseManager.insertRow(one));
                        }

                    }
                    repeatWeekly = false;
//                Log.d(TAG, "Event Date =>  Year: " + Integer.toString(year) + " Month: " + Integer.toString(month) + " Day: "+ Integer.toString(day));
                }
                    else if (string.contains(("RRULE:FREQ=WEEKLY;")) )
                {
                    repeatWeekly = true;

                    if (string.contains("UNTIL=")) {
                        rTime = string.replaceAll("[^0-9]", "");
                        rday = Integer.parseInt(rTime.substring(6, 8));
                        rmonth = Integer.parseInt(rTime.substring(4, 6));
                        ryear = Integer.parseInt(rTime.substring(0, 4));

                        rSecStr = rTime.substring(12, 14);
                        rHrStr = rTime.substring(8, 10);
                        rMinStr = rTime.substring(10, 12);
                        rDayStr = rTime.substring(6, 8);
                        rMonStr = rTime.substring(4, 6);
                        rYrStr = rTime.substring(0, 4);
                    }

                } else if (isEvent) {
                    if (string.contains("LOCATION"))
                        loc = string.substring(9);
                    else if (string.contains("DTSTART")) {
                        sTime = string.replaceAll("[^0-9]", "");
                        shour = Integer.parseInt(sTime.substring(8, 10));
                        sminute = Integer.parseInt(sTime.substring(10, 12));
                        sday = Integer.parseInt(sTime.substring(6, 8));
                        smonth = Integer.parseInt(sTime.substring(4, 6));
                        year = Integer.parseInt(sTime.substring(0, 4));
                    } else if (string.contains("DTEND")) {
                        eTime = string.replaceAll("[^0-9]", "");
                        hour = Integer.parseInt(eTime.substring(8, 10));
                        minute = Integer.parseInt(eTime.substring(10, 12));
                        day = Integer.parseInt(eTime.substring(6, 8));
                        month = Integer.parseInt(eTime.substring(4, 6));

                    } else if (string.contains("SUMMARY")) {
                        name = (string.substring(string.lastIndexOf(":") + 1));
                    }

//                dataInfo.append("\n"+string);

                }

//            Log.d(TAG, string);

            }

//        }
    }

}
