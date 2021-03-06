package com.example.alex.qtapandroid.ICS;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.alex.qtapandroid.ui.fragments.StudentToolsFragment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 1/18/2017.
 * Class to parse the ICS file.
 */
public class ParseICS {

    private String TAG = StudentToolsFragment.class.getSimpleName();

    private Context mContext;

    public ParseICS(Context context) {
        this.mContext = context;
    }

    public List<String> readLine(String path) {
        List<String> mLines = new ArrayList<>();
        AssetManager am = mContext.getAssets();

        try {
            InputStream is = am.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null)
                mLines.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mLines;
    }

    public List<String> readDownloadFile(String path) {
        List<String> mLines = new ArrayList<>();

        String ret = "";

        try {
            InputStream inputStream = mContext.openFileInput(path);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder(); //TODO what is stringbuilder used for

                while ((receiveString = bufferedReader.readLine()) != null) {
                    mLines.add(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        }
        Log.e(TAG, "Done: " + ret);
        return mLines;
    }
}
