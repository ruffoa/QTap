package com.example.alex.qtapandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.course.Course;
import com.example.alex.qtapandroid.common.database.course.CourseManager;

import java.util.ArrayList;
import java.util.Calendar;

import static java.sql.Types.NULL;

/**
 * Created by Carson on 02/12/2016.
 * Fragment used to control UI of the calendar.
 * Other scheduling information could be added to other parts of the screen.
 * This is the first screen user sees upon logging in (unless first time login).
 * Attached to MainTabActivity only.
 */
public class CalendarFragment extends Fragment {
    private CourseManager mCourseManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
                        Log.d("Date", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);
                        getData();
                    }
                }
        );

        dataInfo.setMovementMethod(new ScrollingMovementMethod());
        dataInfo.setText("Initializing Database");
        setup();
    }

    public void getData() {
        mCourseManager = new CourseManager(this.getContext());
        TextView dataInfo = (TextView) getView().findViewById(R.id.calendarEvents);
        DatePicker dateSel = (DatePicker) getView().findViewById(R.id.datePicker);

        ArrayList<Course> data = mCourseManager.getTable();
        String output = "";
        for (int i = 0; i < data.size(); i++) {
            output = System.getProperty("line.separator") + "COURSE id:" + data.get(i).getID() + " title: " + data.get(i).getTitle()
                    + " Location: " + data.get(i).getRoomNum() + " Start Time: " + data.get(i).getStartTime() + " End Time: " + data.get(i).getEndTime() + " Day: " + data.get(i).getDay() + " Month: " + data.get(i).getMonth() + " Year: " + data.get(i).getYear();
        }
        Log.d("SQLITE", "INFO: " + output);
        dataInfo.setText("Event Information for " + dateSel.getMonth() + 1 + "/" + dateSel.getDayOfMonth());

        int day = 0, month = 0, year = 0;

        for (int i = 0; i < data.size(); i++) {
            day = Integer.parseInt(data.get(i).getDay());
            month = Integer.parseInt(data.get(i).getMonth());
            year = Integer.parseInt(data.get(i).getYear());
            Log.d("Date", "Year=" + year + " Month=" + month + " day=" + day + " Data To String: " + data.get(i).getTitle());

            if (year == dateSel.getYear() && month == (dateSel.getMonth() + 1) && dateSel.getDayOfMonth() == day) {
                dataInfo.append(System.getProperty("line.separator") + "Event Name: " + data.get(i).getTitle() + " Location: " + data.get(i).getRoomNum() + " at: " + data.get(i).getStartTime() + " to " + data.get(i).getEndTime());
            }

        }

        if (dataInfo.getText() == "Event Information for " + dateSel.getMonth() + 1 + "/" + dateSel.getDayOfMonth())
        {
            dataInfo.setText("No Data Found; has the database been initialized?");
        }
    }

    public void setup() {
        getData();
    }

}
