package com.example.alex.qtapandroid.common;

import com.orm.SugarRecord;

/**
 * Created by Alex on 1/12/2017.
 */

public class Book extends SugarRecord {
    String title;
    String edition;

    public Book(){
    }

    public Book(String title, String edition){
        this.title = title;
        this.edition = edition;
    }
}
