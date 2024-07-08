package com.rkbuildcon.playerdigitallibrary.utils;

import android.util.TimeUtils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static String getTimeInFormat(int millis){
        return String.format(Locale.getDefault(), "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}
