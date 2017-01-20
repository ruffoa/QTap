package com.example.alex.qtapandroid.common;

import android.provider.BaseColumns;

/**
 * Created by Carson on 19/01/2017.
 */

public class Shop implements BaseColumns {
    public static final String TABLE_NAME = "shop";
    public static final String ROW_NAME = "name";
    public static final String ROW_ADDRESS = "address";
    public static final int NAME_POS = 1;
    public static final int ADDRESS_POS = 2;
    private String NAME;
    private String ADDRESS;

    public Shop(String NAME, String ADDRESS) {
        this.NAME = NAME;
        this.ADDRESS = ADDRESS;
    }

    public String getName() {
        return NAME;
    }

    public void setName(String NAME) {
        this.NAME = NAME;
    }

    public String getAddress() {
        return ADDRESS;
    }

    public void setAddress(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }
}
