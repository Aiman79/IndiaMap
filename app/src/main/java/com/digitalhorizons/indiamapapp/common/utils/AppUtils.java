package com.digitalhorizons.indiamapapp.common.utils;

import android.text.format.DateFormat;

import java.util.Date;

public class AppUtils {
    public static final String DATA_TRIP = "trip_model";
    public static final String DATE_FORMAT_DD_MM_YYYY = "dd MMM yyyy";

    public static String getDateInFormat(long milis, String format){
        try {
            Date date = new Date(milis);
            DateFormat dateFormat = new DateFormat();
            return dateFormat.format(format, date).toString();
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
