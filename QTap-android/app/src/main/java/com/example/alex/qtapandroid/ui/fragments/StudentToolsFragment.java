package com.example.alex.qtapandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alex.qtapandroid.R;
import com.example.alex.qtapandroid.ICS.ParseICS;
import com.example.alex.qtapandroid.common.database.courses.CourseManager;

import java.util.List;


/**
 * Created by Carson on 02/12/2016.
 */

public class StudentToolsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_tools, container, false);
    }
}