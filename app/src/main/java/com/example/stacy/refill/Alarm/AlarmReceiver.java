package com.example.stacy.refill.Alarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.example.stacy.refill.AppDatabase;
import com.example.stacy.refill.Constants;
import com.example.stacy.refill.Database;
import com.example.stacy.refill.Product;
import com.example.stacy.refill.ProductDao;
import com.example.stacy.refill.R;
import com.example.stacy.refill.SyncProductDao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmUtil.setAlarm(context, pendingIntent);
        AppDatabase db = Database.getInstance(context).getAppDatabase();
        ProductDao productDao = db.productDao();
        SyncProductDao syncProductDao = new SyncProductDao(productDao);

        // for test purpose
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();

        // for test purpose
        String firstProduct = "no product";

        List<Product> products = syncProductDao.getAll();
        if(products.size() > 0)
            firstProduct = products.get(0).getName();
        for (Product product : products){
            Double averageDays = product.getAverageDays();
            Double lastAmount = product.getLastUpdateQuantity();
            if(averageDays != -1){
                int daysFromLastUpdate = getTimeRemaining(product.getLastUpdate());
                // TODO think about adding remaining days logic instead of remaining product logic
                int remainingDays = averageDays.intValue() - daysFromLastUpdate;
                double remainingProductPercent = daysFromLastUpdate / (averageDays * lastAmount);
                if(remainingProductPercent < Constants.ProductLeastAmount){
                    sendNotif(product.hashCode(), pendingIntent, context, product.getName());
                }
            }
        }

        //TODO check producrs in db and push notifications
        //Next code it's just an example to test
        // For our recurring task, we'll just display a message
        // for test purpose
        Toast.makeText(context, firstProduct, Toast.LENGTH_SHORT).show();

        nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        sendNotif(1, pendingIntent, context);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //TODO remove WAKEUP, just for testing
        // set 1000* 3600*24 - one day
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000*5, pendingIntent);

    }

    void sendNotif(int id, PendingIntent pIntent, Context context) {
        //Notification notif = new Notification(R.drawable.ic_launcher_foreground, "Notif "
        //        + id, System.currentTimeMillis());
        //notif.flags |= Notification.FLAG_AUTO_CANCEL;
        //notif.setLatestEventInfo(context, "Title " + id, "Content " + id, pIntent);
        Notification.Builder builder = new Notification.Builder(context);
        //builder.setAutoCancel(false);
        builder.setTicker("this is ticker text");
        builder.setContentTitle("WhatsApp Notification");
        builder.setContentText("You have a new message");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentIntent(pIntent);
        //builder.setOngoing(true);
       // builder.setSubText("This is subtext...");   //API level 16
        //builder.setNumber(100);
        Notification notif = builder.build();

        nm.notify(id, notif);
    }

    void sendNotif(int id, PendingIntent pIntent, Context context, String productName) {
        //Notification notif = new Notification(R.drawable.ic_launcher_foreground, "Notif "
        //        + id, System.currentTimeMillis());
        //notif.flags |= Notification.FLAG_AUTO_CANCEL;
        //notif.setLatestEventInfo(context, "Title " + id, "Content " + id, pIntent);
        Notification.Builder builder = new Notification.Builder(context);
        //builder.setAutoCancel(false);
        //builder.setTicker("this is ticker text");
        builder.setContentTitle("Refill Notification");
        builder.setContentText("You should buy product " + productName);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentIntent(pIntent);
        //builder.setOngoing(true);
        // builder.setSubText("This is subtext...");   //API level 16
        //builder.setNumber(100);
        Notification notif = builder.build();

        nm.notify(id, notif);
    }

    private int getTimeRemaining(Date previousDate)
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

    private Calendar getCalendar(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    private Calendar getCalendar(long millis){
        Date date = new Date(millis);
        return getCalendar(date);
    }
}
