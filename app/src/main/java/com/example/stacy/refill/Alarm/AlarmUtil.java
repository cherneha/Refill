package com.example.stacy.refill.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AlarmUtil {

    public static int HOUR_OF_DAY = 6;
    public static int MINUTE = 0;

    public static void setAlarm(AlarmManager alarmManager, PendingIntent pendingIntent){
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY);
        cal.set(Calendar.MINUTE, MINUTE);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        //check if we want to wake up tomorrow
        if (System.currentTimeMillis() > cal.getTimeInMillis()){
            cal.setTimeInMillis(cal.getTimeInMillis()+ 24*60*60*1000);// Okay, then tomorrow ...
        }
        // TODO think about wake up
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, AlarmManager.INTERVAL_DAY, cal.getTimeInMillis(), pendingIntent);
    }
}
