package com.example.alex.qtapandroid.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.qtapandroid.common.Book;


import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.common.DataRecords;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InformationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information,container,false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
       setup();
    }

    public void setup(){
        setData();
        listData();
    }

    public void setData() {

        DataRecords record = new DataRecords("Elec 212", "Walter Light", "12:30", "14:30", 10180070);
        record.save();

        Book book = new Book("Title here", "2nd edition");
        book.save();

    }

    public void listData(){

        List<DataRecords> datRec = DataRecords.listAll(DataRecords.class);
        TextView dataInfo = (TextView) getView().findViewById(R.id.dataInfo1);
        dataInfo.setText(datRec.toString());

    }

}
