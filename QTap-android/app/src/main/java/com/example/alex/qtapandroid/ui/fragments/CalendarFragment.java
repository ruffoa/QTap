package com.example.alex.qtapandroid.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alex.qtapandroid.R;

/**
 * Created by Carson on 02/12/2016.
 */

/**
 *Empty fragment so that when switching between drawer items, you can use fragments for each element.
 * Calendar is a part of the main content xml, NOT this fragment.
 * XML fragment of this file is also empty.
 * This is only a temporary fix:
 *         -on first open, 'schedule' not blue on drawer
 *         ****-next fragments will still have calendar in background****
 *         -just poor design in general, will cause future problems
 */
public class CalendarFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar,container,false);
    }
}
