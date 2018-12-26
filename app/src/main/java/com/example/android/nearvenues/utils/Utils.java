package com.example.android.nearvenues.utils;

import android.location.Location;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {



    public static String getLatLon(Location location) {
        DecimalFormat format = new DecimalFormat("#.##");
        return format.format(location.getLatitude()) + "," + format.format(location.getLongitude());
    }



}
