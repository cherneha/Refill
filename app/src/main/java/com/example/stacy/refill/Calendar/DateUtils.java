package com.example.stacy.refill.Calendar;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static int getTimeRemaining(Date previousDate)
    {
        Calendar sDate = getCalendar(previousDate);
        Calendar eDate = getCalendar(System.currentTimeMillis());
        //Calendar eDate = toCalendar(System.currentTimeMillis());

        // Get the represented date in milliseconds
        long milis1 = sDate.getTimeInMillis();
        long milis2 = eDate.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = Math.abs(milis2 - milis1);

        return (int)(diff / (24 * 60 * 60 * 1000));
    }

    public static Calendar getCalendar(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Calendar getCalendar(long millis){
        Date date = new Date(millis);
        return getCalendar(date);
    }
}
