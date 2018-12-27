package com.example.android.nearvenues.utils;

import android.location.Location;
import java.text.DecimalFormat;

public class Utils {

    /**
    Method used for FourSquare API call
        @param location user last known location
        @return String with latitude & longitude data as required in API call.
     */
    public static String getLatLon(Location location) {
        DecimalFormat format = new DecimalFormat("#.##");
        return format.format(location.getLatitude()) + "," + format.format(location.getLongitude());
    }



}
