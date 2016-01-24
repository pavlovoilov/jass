package com.jass.data.random;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimestampData {

    public static String getTodayWithMilliseconds() {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        return new StringBuilder()
                .append("_").append(localCalendar.get(Calendar.DAY_OF_MONTH))
                .append("_").append(localCalendar.get(Calendar.HOUR))
                .append("_").append(localCalendar.get(Calendar.MINUTE))
                .append("_").append(localCalendar.get(Calendar.MILLISECOND))
                .toString();
    }

    public static String getTodayWithFormat() {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("E, dd MMM yyyy");
        return  ft.format(date);
    }

    public static String getMilliseconds() {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        return new StringBuilder()
                .append("_").append(localCalendar.get(Calendar.MILLISECOND))
                .toString();
    }

}
