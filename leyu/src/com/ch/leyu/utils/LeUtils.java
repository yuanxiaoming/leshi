
package com.ch.leyu.utils;

import android.os.SystemClock;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LeUtils {
    private static final long MILLISECOND = 1000;

    public static long getTime(long unixTime) {
        return unixTime * MILLISECOND;
    }

    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    public static long getElapsedRealTime() {
        return SystemClock.elapsedRealtime();
    }

    public static long getUnixTime() {
        return System.currentTimeMillis() / 1000;
    }

    public static String toDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date(getTime( time)));
        return date;
    }

}
