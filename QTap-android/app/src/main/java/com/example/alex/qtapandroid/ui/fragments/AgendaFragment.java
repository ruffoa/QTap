package com.example.alex.qtapandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.courses.OneClass;
import com.example.alex.qtapandroid.common.database.courses.OneClassManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Carson on 02/12/2016.
 * Fragment that shows the classes for one day, and allows the user to cycle through to the next days.
 */
public class AgendaFragment extends Fragment {

    private static final String TAG = AgendaFragment.class.getSimpleName();

    private TextView mDataInfo;
    private View view; //not mView because that hides an attribute in a parent class (fragment)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_agenda, container, false);
        ListView listview = (ListView) view.findViewById(R.id.agendaList);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int day = bundle.getInt("day", 0);
            int month = bundle.getInt("month", 0);
            int year = bundle.getInt("year", 2016);

            calendar.set(year, month, day);

        }

        String[] itemArr = getDayEventData(calendar);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemArr);
        listview.setAdapter(adapter);

        mDataInfo = (TextView) view.findViewById(R.id.textDay);
        CharSequence s = DateFormat.format("yyyy-MM-dd", calendar.getTime());
        mDataInfo.setText("Showing Information For: " + s + " (" + calendar.getDisplayName(
                Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) + ")");

        Button mEmailSignInButton = (Button) view.findViewById(R.id.nextDay);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEmailSignInButton();
            }
        });

        return view;
    }

    /**
     * Defines the on click listener code for the email sign in button = mEmailSignInButton.
     */
    public void onClickEmailSignInButton() {
        String rTime = mDataInfo.getText().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        rTime = rTime.substring(rTime.indexOf(":") + 2);

        int yr = Integer.parseInt(rTime.substring(0, 4));
        int mon = Integer.parseInt(rTime.substring(5, 7)) - 1;
        int day = Integer.parseInt(rTime.substring(8, 10));

        calendar.set(yr, mon, day);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_YEAR, 2);
        }

        ListView listview = (ListView) view.findViewById(R.id.agendaList);
        String[] itemArr = getDayEventData(calendar);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemArr);
        listview.setAdapter(adapter);

        CharSequence s = DateFormat.format("yyyy-MM-dd", calendar.getTime());
        mDataInfo.setText("Showing Information For: " + s + " (" + calendar.getDisplayName(
                Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) + ")");
    }

    /**
     * Method that displays the events for the day selected in the calendar.
     *
     * @param calendar The calendar where the user selects a day.
     * @return Array of Strings that hold each event's information.
     */
    public String[] getDayEventData(Calendar calendar) {
        OneClassManager oneClassManager = new OneClassManager(this.getContext());

        List<String> list = new ArrayList<String>();
        ArrayList<OneClass> data = oneClassManager.getTable();

        int day, month, year;
        boolean isInfo = false;
        for (int i = 0; i < data.size(); i++) {             // look for the selected day in the events from the database
            day = Integer.parseInt(data.get(i).getDay());
            month = Integer.parseInt(data.get(i).getMonth());
            year = Integer.parseInt(data.get(i).getYear());

            int calDay = calendar.get(Calendar.DAY_OF_MONTH);
            int calMon = calendar.get(Calendar.MONTH) + 1;
            int calYear = calendar.get(Calendar.YEAR);


            if (year == calYear && month == calMon && calDay == day) { // if the day matches add the event
                list.add("Event Name: " + data.get(i).getType() + " Location: " +
                        data.get(i).getRoomNum() + " at: " + data.get(i).getStartTime() +
                        " to " + data.get(i).getEndTime());
                isInfo = true;
            }
        }
        if (!isInfo) {
            list.add("Nothing is happening today");
            return list.toArray(new String[list.size()]);
        }

        List<String> result = new ArrayList<String>();

        int startHour;
        int startMin;
        int posSmall = 0;
        int minHour;
        int minMin;

        for (int i = 0; i < list.size(); i++) {
            minHour = 25;
            minMin = 61;

            for (int j = 0; j < list.size(); j++) {
                String s = list.get(j);
                int index = s.indexOf("at: ") + 4;
                String tp = s.substring(index);
                String temp1 = tp.substring(0, tp.indexOf(":"));
                index = temp1.length() + 1;
                String temp2 = tp.substring(index, s.indexOf(" ") - 1);

                startHour = Integer.parseInt(temp1);
                startMin = Integer.parseInt(temp2);
                if (startHour < minHour) {
                    posSmall = j;
                    minHour = startHour;
                    minMin = startMin;
                } else if (startHour == minHour) {
                    if (startMin < minMin) {
                        posSmall = j;
                        minHour = startHour;
                        minMin = startMin;
                    }
                }
            }
            result.add(list.get(posSmall));
            list.remove(posSmall);
            i = 0;
        }
        if (list.size() > 0)
            result.add(list.get(0));
        return result.toArray(new String[list.size()]);
    }
}


