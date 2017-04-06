package com.example.alex.qtapandroid.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
        View v = inflater.inflate(R.layout.fragment_student_tools, container, false);
        TextView solus = (TextView) v.findViewById(R.id.solus_button);
        TextView outlook = (TextView) v.findViewById(R.id.outlook_button);
        TextView onq = (TextView) v.findViewById(R.id.onq_button);
        solus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.my.queensu.ca/"));
                startActivity(intent);
            }
        });
        outlook.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://login.microsoftonline.com/"));
                startActivity(intent);
            }
        });
        onq.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.queensu.ca/"));
                startActivity(intent);
            }
        });

        return v;
    }
}