package com.example.alex.qtapandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.database.courses.Course;
import com.example.alex.qtapandroid.common.database.courses.CourseManager;
import com.example.alex.qtapandroid.common.database.courses.OneClass;
import com.example.alex.qtapandroid.common.database.courses.OneClassManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Carson on 02/12/2016.
 */

public class AgendaFragment extends Fragment {

    private OneClassManager mOneClassManager;
    public static final String TAG = AgendaFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.fragment_agenda, container, false);


        ListView listview =(ListView)V.findViewById(R.id.agendaList);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        String[] itemArr = getData(calendar);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemArr);
        listview.setAdapter(adapter);


        TextView dataInfo = (TextView) V.findViewById(R.id.textDay);

        CharSequence s = DateFormat.format("yyyy-MM-dd", calendar.getTime());

        dataInfo.setText( "Showing Information For: " + s + " (" + calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) + ")");

        Button mEmailSignInButton = (Button) V.findViewById(R.id.nextDay);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClick();
            }
        });

        return V;



//        return inflater.inflate(R.layout.fragment_agenda,container,false);        // created a different view for the listView
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    public void btnClick()
    {

        TextView dataInfo = (TextView) this.getView().findViewById(R.id.textDay);
        String rTime = dataInfo.getText().toString();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());


        Log.d("TEST", "Date: " + rTime );

        rTime = rTime.substring(rTime.indexOf(":") + 2);
        Log.d("TEST", "Breakdown: " + rTime.substring(0, 4) + "/" + rTime.substring(5, 7) + " / " +  rTime.substring(8,10));

        int yr = Integer.parseInt(rTime.substring(0, 4));
        int mon = Integer.parseInt(rTime.substring(5, 7));
        int day = Integer.parseInt(rTime.substring(8,10));

        mon -= 1;

        calendar.set(yr, mon, day);


        calendar.add(Calendar.DAY_OF_YEAR, 1);
        if (calendar.get(Calendar.DAY_OF_WEEK)  == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
        {
            calendar.add(Calendar.DAY_OF_YEAR, 2);
        }

        Log.d("TEST", "Cal Success! -> new date: " + yr + "/" + mon + "/" + day + " -> Cal values: (mon/day) " + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DAY_OF_MONTH));

        ListView listview =(ListView) this.getView().findViewById(R.id.agendaList);
        String[] itemArr = getData(calendar);
        Log.d("TEST", "GET VIEW SUCCESS!" );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemArr);
        listview.setAdapter(adapter);
        Log.d("TEST", "DONE?" );

        CharSequence s = DateFormat.format("yyyy-MM-dd", calendar.getTime());
        dataInfo.setText( "Showing Information For: " + s + " (" + calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) + ")");


//        adapter.notifyDataSetChanged();

    }


    public String [] getData(Calendar calendar) {                     // this function displays the data for the selected day in the green text view



        mOneClassManager = new OneClassManager(this.getContext());

        Log.d("TEST", "Loaded func GetData" );

//        String [] data = new String [];
        List<String> list = new ArrayList<String>();

        ArrayList<OneClass> data = mOneClassManager.getTable();

        int day = 0, month = 0, year = 0;
        boolean isInfo = false;
        for (int i = 0; i < data.size(); i++) {             // look for the selected day in the events from the database
            day = Integer.parseInt(data.get(i).getDay());
            month = Integer.parseInt(data.get(i).getMonth());
            year = Integer.parseInt(data.get(i).getYear());


            int calDay = calendar.get(Calendar.DAY_OF_MONTH);
            int calMon = calendar.get(Calendar.MONTH) + 1;
            int calYear = calendar.get(Calendar.YEAR);

//            Log.d("Get Data: Date", "Year=" + year + " Month=" + (month + 1) + " day=" + day + " Calendar: " + calYear + "/" + calMon + "/" + calDay );

            if (year == calYear && month == calMon && calDay == day) {     // if the day matches...
//                dataInfo.append(System.getProperty("line.separator") + "Event Name: " + data.get(i).getTitle() + " Location: " + data.get(i).getRoomNum() + " at: " + data.get(i).getStartTime() + " to " + data.get(i).getEndTime());
//                arrayList.add("Event Name: " + data.get(i).getTitle() + " Location: " + data.get(i).getRoomNum() + " at: " + data.get(i).getStartTime() + " to " + data.get(i).getEndTime());
                list.add("Event Name: " + data.get(i).getType() + " Location: " + data.get(i).getRoomNum() + " at: " + data.get(i).getStartTime() + " to " + data.get(i).getEndTime());
                isInfo = true;
            }

        }

        if (isInfo == false) {
//            arrayList.add("No Data Found; has the database been initialized?");
            list.add("No Data Found; has the database been initialized?");

            String[] listArr = new String[list.size()];
            listArr = list.toArray(listArr);
            return listArr;

        }

        List<String> result = new ArrayList<String>();

        int startHour; int startMin; int endHour = 0; int endMinute = 0; int posSmall = 0; int minHour; int minMin;

        for (int i = 0; i < list.size(); i++)
        {
            minHour = 25; minMin = 61;

            for (int j = 0; j < list.size(); j++) {

                String s =list.get(j);

                int index = s.indexOf("at: ") + 4;
                String tp = s.substring(index);
                String temp1 = tp.substring(0, tp.indexOf(":"));

                index = temp1.length() + 1;
                String temp2 = tp.substring(index, s.indexOf(" ") - 1);

//                Log.d("Date", "String: " + s + " -> Hour: " + temp1 + " Minute: " + temp2);

                startHour = Integer.parseInt(temp1);
                startMin = Integer.parseInt(temp2);

//                startHour = Integer.parseInt(s.substring(s.indexOf("at: ") + 4, s.indexOf("at: ") + 6) );
//                startMin = Integer.parseInt(s.substring(s.indexOf("at: ") + 7, s.indexOf("at: ") + 9) );

                if (startHour < minHour)
                {
                    posSmall = j;
                    minHour = startHour;
                    minMin = startMin;
                }
                else if (startHour == minHour)
                {
                    if (startMin < minMin)
                    {
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
        result.add(list.get(0));

//        adapter.notifyDataSetChanged();
        String[] listArr = new String[list.size()];
        listArr = result.toArray(listArr);
        return listArr;


    }
}


