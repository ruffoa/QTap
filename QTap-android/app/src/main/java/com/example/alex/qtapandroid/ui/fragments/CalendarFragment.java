package com.example.alex.qtapandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
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
import com.example.alex.qtapandroid.common.database.courses.Course;
import com.example.alex.qtapandroid.common.database.courses.CourseManager;
import com.example.alex.qtapandroid.common.database.courses.OneClass;
import com.example.alex.qtapandroid.common.database.courses.OneClassManager;
import com.example.alex.qtapandroid.common.database.users.User;
import com.example.alex.qtapandroid.common.database.users.UserManager;

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
    private OneClassManager mOneClassManager;
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

    public void getData() {                     // this function displays the data for the selected day in the green text view
        mOneClassManager = new OneClassManager(this.getContext());
        mCourseManager = new CourseManager(this.getContext());
        TextView dataInfo = (TextView) getView().findViewById(R.id.calendarEvents);
        DatePicker dateSel = (DatePicker) getView().findViewById(R.id.datePicker);

        ArrayList<OneClass> data = mOneClassManager.getTable();

        dataInfo.setText("Event Information for " + (dateSel.getMonth() + 1) + "/" + dateSel.getDayOfMonth());        // get the selected day

        int day = 0, month = 0, year = 0;
        boolean isInfo = false;
        for (int i = 0; i < data.size(); i++) {             // look for the selected day in the events from the database
            day = Integer.parseInt(data.get(i).getDay());
            month = Integer.parseInt(data.get(i).getMonth());
            year = Integer.parseInt(data.get(i).getYear());
            String courseTitle = mCourseManager.getRow(data.get(i).getID()).getTitle();
            if (year == dateSel.getYear() && month == (dateSel.getMonth() + 1) && dateSel.getDayOfMonth() == day) {     // if the day matches...
                dataInfo.append(System.getProperty("line.separator") + "Event Name: " + courseTitle +
                        " Location: " + data.get(i).getRoomNum() + " at: " + data.get(i).getStartTime() + " to " +
                        data.get(i).getEndTime());
                isInfo = true;
            }

        }

        if (!isInfo) {
            dataInfo.setText("No Data Found; has the database been initialized?");
        }
    }

    public void setup() {
        mOneClassManager = new OneClassManager(this.getContext());
        UserManager mUserManager = new UserManager(this.getContext());
        ArrayList<User> user = mUserManager.getTable();
        User.printUsers(mUserManager.getTable());
        boolean isInit = false;


        for (int i = 0; i < user.size(); i++) {             // see if user has initialized database yet, and if the database is up to date

            if (!user.get(i).getDateInit().isEmpty()) {
                String rTime = user.get(i).getDateInit();
                int yr = Integer.parseInt(rTime.substring(0, 4));
                int mon = Integer.parseInt(rTime.substring(5, 7));
                int day = Integer.parseInt(rTime.substring(8,10));

                Calendar c = Calendar.getInstance();
                c.set(yr, mon, day);
                c.add(Calendar.DATE, 7);

                Calendar endDate = Calendar.getInstance();

                if (c.after(endDate))
                    isInit = true;
                Log.d(TAG, "User Date Inf ->  Start Date:" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.DAY_OF_MONTH) + " End Date: " + endDate.get(Calendar.MONTH) + "/" + endDate.get(Calendar.DAY_OF_MONTH) + " isInit: " + isInit);

            }
        }

        if (mOneClassManager.getTable().isEmpty() || !isInit) {
            mOneClassManager.deleteTable();

            boolean isEvent = false;
            String sTime = "", eTime = "", loc = "", name = "", rTime;
            int hour = 0, minute = 0, day = 0, month = 0, year = 0;
            int shour = 0, sminute = 0, sday = 0, smonth = 0;
            boolean repeatWeekly = false;
            String rDayStr = "", rMonStr = "", rYrStr = "";

            mParser = new icsParser(this.getContext());
//            mLines = mParser.readLine(mPath); // this is for the hardcoded file
            mLines = mParser.readDownloadFile("cal.ics");
        int test=0;
            int test2=0;
            for (String string : mLines) {

                if (string.contains("BEGIN:VEVENT")) {
                    isEvent = true;
//                dataInfo.append("\n" + "New Event");
                } else if (string.contains("END:VEVENT")) {
                    isEvent = false;

                    String tempTime = Integer.toString(shour) + ":" + Integer.toString(sminute);
                    String tempEndTime = Integer.toString(hour) + ":" + Integer.toString(minute);
                    OneClass one = new OneClass(name, loc, tempTime, tempEndTime, Integer.toString(sday), Integer.toString(smonth), Integer.toString(year));
                    one.setID(mOneClassManager.insertRow(one));
                    //TODO set course ID as well: query Course table for entry with the same title, that is the ID to use

                    if (repeatWeekly) {

                        // get the supported ids for GMT-08:00 (Pacific Standard Time)
                        String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
                        // if no ids were returned, something is wrong. get out.
                        if (ids.length == 0)
                            System.exit(0);

                        // create a Pacific Standard Time time zone
                        SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);

                        // set up rules for Daylight Saving Time
                        pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
                        pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

                        // create a GregorianCalendar with the Pacific Daylight time zone
                        // and the current date and time
                        Calendar cal2 = new GregorianCalendar(pdt);
                        Calendar cal = new GregorianCalendar(pdt);

                        cal.set(year, smonth - 1, sday);

                        cal2.set(Integer.parseInt(rYrStr), Integer.parseInt(rMonStr) - 1, Integer.parseInt(rDayStr) + 1);

                        Date endDate = cal2.getTime();
                        Date date1;

                        cal.add(Calendar.DATE, 7);
                        date1 = cal.getTime();

                        while (date1.before(endDate)) {
                            sday = cal.get(Calendar.DAY_OF_MONTH);
                            smonth = cal.get(Calendar.MONTH);
                            year = cal.get(Calendar.YEAR);
//                                Log.d(TAG, "Repeated Event Date =>  Year: " + Integer.toString(year) + " Month: " + Integer.toString(smonth) + " Day: " + Integer.toString(sday) + " Name: " + name + " At: " + loc + " End Date: " + endDateString);

                            one = new OneClass(name, loc, tempTime, tempEndTime, Integer.toString(sday), Integer.toString(smonth + 1), Integer.toString(year));
                            one.setBuildingID(++test);
                            one.setCourseID(++test2);
                            one.setID(mOneClassManager.insertRow(one));
                            cal.add(Calendar.DATE, 7);
                            date1 = cal.getTime();
//                            }
                        }
                    }
                    repeatWeekly = false;

                } else if (string.contains(("RRULE:FREQ=WEEKLY;"))) {
                    repeatWeekly = true;

                    if (string.contains("UNTIL=")) {
                        rTime = string.replaceAll("[^0-9]", "");

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

                    } else if (string.contains("SUMMARY")) {
                        name = (string.substring(string.lastIndexOf(":") + 1));
                    }

                }

            }
            Date d = new Date();
            CharSequence s = DateFormat.format("yyyy-MM-dd hh:mm:ss", d.getTime());

            String uName =  user.get(0).getFirstName();
            String uLastName = user.get(0).getLastName();
            String uNetID =  user.get(0).getNetid();
            String uURL = user.get(0).getIcsURL();

            User nUser = new User (uNetID, uName, uLastName, s.toString(), uURL);
            mUserManager.updateRow(user.get(0), nUser);

            Log.d(TAG, "Updated User info: " + user.get(0).getDateInit() + " User NetID: "+ user.get(0).getNetid() + " User NetID: "+ user.get(0).getIcsURL());

        }
    }

}
