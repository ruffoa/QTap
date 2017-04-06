package com.example.alex.qtapandroid.common.maps;

/**
 * Created by Alex on 4/6/2017.
 */

public class icsToBuilding {
    public static double[] getAddress(String loc) {
        double[] adr = new double[2];

        if (loc.contains("WALTER LIGHT")) {
            adr[0] = 44.227973;
            adr[1] = -76.491807;
        } else if (loc.contains("MCLAUGHLIN")) {
            adr[0] = 44.223761;
            adr[1] = -76.495409;
        } else if (loc.contains("BIOSCI")) {
            adr[0] = 44.226486;
            adr[1] = -76.491286;
        } else if (loc.contains("KINES & HLTH")) {
            adr[0] = 44.228575;
            adr[1] = -76.493408;
        } else {
            adr[0] = 44.225233;
            adr[1] = 76.495163;
        }
        return adr;
    }
}
