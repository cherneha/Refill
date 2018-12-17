package com.example.stacy.refill.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import com.example.stacy.refill.Calendar.DateUtils;
import com.example.stacy.refill.DBManager.Constants;
import com.example.stacy.refill.Product;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AlarmUtil {

    public static int HOUR_OF_DAY = 6;
    public static int MINUTE = 0;

    // for test purpose
    private static int secondsPlus = 0;

    public static void setAlarm(Context context, PendingIntent pendingIntent){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY);
        cal.set(Calendar.MINUTE, MINUTE);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        //check if we want to wake up tomorrow
        if (System.currentTimeMillis() > cal.getTimeInMillis()){
            // for test purpose. this row should be removed. next row should be uncommented
            cal.setTimeInMillis(System.currentTimeMillis() + 15*1000);// Okay, then tomorrow ...
//            cal.setTimeInMillis(cal.getTimeInMillis()+ 24*60*60*1000);// Okay, then tomorrow ...
        }
        // TODO think about wake up
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 15*1000, pendingIntent);
        }
        else{
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 15*1000, pendingIntent);
        }
    }

    public static boolean isRemainingDaysLessThanAverage(Product product){
        Double averageDays = product.getAverageDays();
        Double lastAmount = product.getLastUpdateQuantity();
        if (averageDays != -1) {
            int daysFromLastUpdate = DateUtils.getTimeRemaining(product.getLastUpdate());
            int remainingDays = Double.valueOf(averageDays * lastAmount).intValue() - daysFromLastUpdate;
            if (remainingDays <= Constants.DaysLeastAmount)
                return true;
        }
        return false;
    }
}
