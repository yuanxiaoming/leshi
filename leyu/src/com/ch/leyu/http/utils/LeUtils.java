
package com.ch.leyu.http.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LeUtils {

    public static String toDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date(time));
        return date;
    }

}
