package com.example.omar.fragfloat.Data;

import java.util.Random;

/**
 * Created by Omar on 1/12/2019.
 */

public class Constants {
    public static final String URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02";
    public static final int LIMIT = 30;

    public static int randomInt(int max , int min){
        return new Random().nextInt(max-min)+min;
    }
}
