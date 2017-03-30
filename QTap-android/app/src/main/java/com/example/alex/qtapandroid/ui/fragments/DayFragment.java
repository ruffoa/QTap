package com.example.alex.qtapandroid.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.card.elements.DataObject;
import com.example.alex.qtapandroid.common.card.elements.RecyclerViewAdapter;
import com.example.alex.qtapandroid.common.database.courses.OneClass;
import com.example.alex.qtapandroid.common.database.courses.OneClassManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DayFragment extends Fragment {

    private static final String TAG = AgendaFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    private View view; //not mView because that hides an attribute in a parent class (fragment)
    private TextView dateText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_day, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        dateText = (TextView) view.findViewById(R.id.DateTextDisplay);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

//        String[] s = getDayEventData(cal);
        mAdapter = new RecyclerViewAdapter(getDayEventData(cal));
        mRecyclerView.setAdapter(mAdapter);

        // Code to Add an item with default animation
//        ((RecyclerViewAdapter) mAdapter).addItem(new DataObject("TEST!", "EXAMPLE TEST TEXT..."), 0);

        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        ((RecyclerViewAdapter) mAdapter).setOnItemClickListener(new RecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }

    private ArrayList<DataObject> getDataSet(String[] s) {
        ArrayList results = new ArrayList<DataObject>();
        for (int i = 0; i < s.length - 1; i++) {
            String temp = s[i].substring(s[i].indexOf("at: "), 10);
            DataObject obj = new DataObject(s[i].substring(0, 10) + i,
                    temp);
            results.add(i, obj);
        }
        return results;
    }

    public ArrayList<DataObject> getDayEventData(Calendar calendar) {
        OneClassManager oneClassManager = new OneClassManager(this.getContext());

        List<String> list = new ArrayList<String>();
        List<String> loc = new ArrayList<>();
        List<String> time = new ArrayList<>();
        List<String> des = new ArrayList<>();
        ArrayList<DataObject> result = new ArrayList<DataObject>();

        ArrayList<OneClass> data = oneClassManager.getTable();

        int day, month, year, sDay, sMon;
        boolean isInfo = false;
        Calendar cal = calendar;

        sDay = calendar.get(Calendar.DAY_OF_MONTH);
        sMon = calendar.get(Calendar.MONTH) + 1;

        int calDay = calendar.get(Calendar.DAY_OF_MONTH);
        int calMon = calendar.get(Calendar.MONTH) + 1;
        int calYear = calendar.get(Calendar.YEAR);

        CharSequence f = DateFormat.format("yyyy-MM-dd", calendar.getTime());
        CharSequence date = DateFormat.format("EEE, d MMM, yyyy", cal.getTime());

        dateText.setText(date);
//        list.add("Showing Information For: " + date);

        for (int i = 0; i < data.size(); i++) {             // look for the selected day in the events from the database
            day = Integer.parseInt(data.get(i).getDay());
            month = Integer.parseInt(data.get(i).getMonth());
            year = Integer.parseInt(data.get(i).getYear());


            if (year == calYear && month == calMon && calDay == day) { // if the day matches add the event
                list.add(data.get(i).getType());
                loc.add(data.get(i).getRoomNum());
                time.add(data.get(i).getStartTime() + "-" + data.get(i).getEndTime());
                des.add(data.get(i).getType());

                isInfo = true;
            }
        }

        if (!isInfo) {
//            list.clear();
//            list.add("No events today (" + sMon + "/" + sDay + " - " + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + ")");
//
            result.add(new DataObject("No events today", date.toString()));
            return result;
        }


        int startHour;
        int startMin;
        int posSmall = 0;
        int minHour;
        int minMin;
        int endHour =0;
        int endMin =0;
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i) == ("Nothing is happening today")) {
                result.add(new DataObject(list.get(i), f.toString()));
                list.remove(i);
                i -= 1;

            } else {
                minHour = 25;
                minMin = 61;

                for (int j = 0; j < list.size(); j++) {
                    String s = time.get(j);
                    String s1 = s.substring(0, s.indexOf("-"));
                    int div = s1.indexOf(":");
                    String shour = s1.substring(0,div);
                    String smin = s1.substring(div + 1, s1.length());

                    int index = s.indexOf("-") + 1;
                    String s2 = s.substring(index,s.length());
                    Log.d(TAG, "Event: " + list.get(i) + " sTime:" + s1 + " eTime: " + s2);
                    div = s2.indexOf(":");

                    startHour = Integer.parseInt(shour);
                    startMin = Integer.parseInt(smin);
                    if (startHour < minHour) {
                        posSmall = j;
                        minHour = startHour;
                        minMin = startMin;
                        endHour = Integer.parseInt(s2.substring(0,div));
                        endMin = Integer.parseInt(s2.substring(div + 1,s2.length()));

                    } else if (startHour == minHour) {
                        if (startMin < minMin) {
                            posSmall = j;
                            minHour = startHour;
                            minMin = startMin;
                            endHour = Integer.parseInt(s2.substring(0,div));
                            endMin = Integer.parseInt(s2.substring(div + 1,s2.length()));

                        }
//                        }
                    }
                }
                String amPMTime;
                if (minHour > 12)
                    amPMTime = (minHour - 12) + ":" +  minMin + "-" + (endHour - 12) + ":" + endMin + " PM";
                else amPMTime = time.get(posSmall) + " AM";

                result.add(new DataObject(list.get(posSmall), amPMTime + " at: " + loc.get(posSmall)));
                list.remove(posSmall);
                time.remove(posSmall);
                loc.remove(posSmall);
                des.remove(posSmall);
                i = -1;
            }
        }
        if (list.size() > 0) {
            result.add(new DataObject(list.get(0), time.get(0) + " at: " + loc.get(0) + " description: " + des.get(0)));
        }


        return result;
    }
}
