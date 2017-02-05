package com.example.alex.qtapandroid.common.database.services;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
/**
 * Created by owner on 2017-01-29.
 */

public class Service implements BaseColumns{

    public static final String TABLE_NAME = "service";
    public static final String COLUMN_HOURS = "hours";
    public static final String COLUMN_BUILDING_ID = "buildingID";
    public static final String COLUMN_WEBSITE = "website";
    public static final String COLUMN_PURPOSE = "purpose";

    public static final int ID_POS = 0;
    public static final int HOUSE_POS = 1;
    public static final int BUILDING_ID_POS = 2;
    public static final int WEBSITE_POS = 3;
    public static final int PURPOSE_POS =4;

    private String hours;
    private long buildingID;
    private String website;
    private String purpose;
    private long id;

    public Service(String hours, long building, String website, String purpose){
        this.hours=hours;
        this.buildingID = building;
        this.website=website;
        this.purpose=purpose;
    }
 public static void printServices(ArrayList<Service>services){
     String output = "Services\n";
     for(int i=0; i<services.size(); i++){
         output += "ID" + services.get(i).getID() + "Hours" + services.get(i).getHours() +
                 "Building" + services.get(i).getBuildingID() + "Website" + services.get(i).getWebsite()
                 + "Purpose" + services.get(i).getPurpose();
     }
     Log.d("SQLITE",output);

 }

    public long getID(){return this.id;}
    public void setID(long id){this.id=id;}
    public String getHours(){return this.hours;}
    public long getBuildingID(){return this.buildingID;}
    public String getWebsite(){return this.website;}
    public String getPurpose(){return this.purpose;}


}
