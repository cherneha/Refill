package com.example.stacy.refill.Alarm;

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
import android.widget.Toast;

import com.example.stacy.refill.Calendar.DateUtils;
import com.example.stacy.refill.DBManager.AppDatabase;
import com.example.stacy.refill.DBManager.Constants;
import com.example.stacy.refill.DBManager.Database;
import com.example.stacy.refill.MainActivity;
import com.example.stacy.refill.NotificationActions.ApproveNotification;
import com.example.stacy.refill.NotificationActions.SnoozeNotification;
import com.example.stacy.refill.Product;
import com.example.stacy.refill.DBManager.ProductDao;
import com.example.stacy.refill.R;
import com.example.stacy.refill.DBManager.SyncProductDao;
import com.example.stacy.refill.SettingsPreferences;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        //android.os.Debug.waitForDebugger();
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

        nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        List<Product> products = syncProductDao.getAll();
        if(products != null) {
            if (products.size() > 0)
                firstProduct = products.get(0).getName();
            for (Product product : products) {
                Double averageDays = product.getAverageDays();
                Double lastAmount = product.getLastUpdateQuantity();

                if (averageDays != -1) {
                    int daysFromLastUpdate = DateUtils.getTimeRemaining(product.getLastUpdate());
                    double remainingProductPercent = lastAmount - (double)daysFromLastUpdate / averageDays;
                    if(remainingProductPercent < 0)
                        remainingProductPercent = 0;

                    boolean first = false;
                    if (AlarmUtil.isRemainingDaysLessThanAverage(product)){
                        if(!product.isUpdateNeeded()){
                            first = true;
                            sendNotifWithActions(product.getName().hashCode(), context, product.getName());
                        }
                        // Hope will be set if user approve notification
                        //product.setUpdateNeeded(true);
                    }
                    else{
                        product.setUpdateNeeded(false);
                        // Update product current quantity
                        product.setCurrentQuantity(remainingProductPercent);
                    }

                    if(product.isUpdateNeeded() && !first){
                        sendNotif(product.getName().hashCode(), context, product.getName());
                        // Update product current quantity
                        product.setCurrentQuantity(remainingProductPercent);
                    }
//                    else{
//                        product.setUpdateNeeded(false);
//                    }
//                    if(product.isUpdateNeeded()){
//                        sendNotif(product.getName().hashCode(), pendingIntent, context, product.getName());
//                    }

                    syncProductDao.update(product);
                }
            }
        }

        //TODO check producrs in db and push notifications
        //Next code it's just an example to test
        // For our recurring task, we'll just display a message
        // for test purpose
        Toast.makeText(context, firstProduct, Toast.LENGTH_SHORT).show();
//        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        sendNotif(1, pendingIntent, context);
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //TODO remove WAKEUP, just for testing
        // set 1000* 3600*24 - one day
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000*10, pendingIntent);

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
        Notification notif = builder.build();

        nm.notify(id, notif);
    }

    void sendNotif(int id, Context context, String productName) {
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent pContentIntent = PendingIntent.getActivity(context,
                0, contentIntent , 0);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setAutoCancel(true);
        builder.setContentTitle("Refill Notification");
        builder.setContentText("You should buy product " + productName);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentIntent(pContentIntent);
        Notification notif = builder.build();
        System.out.println(id);
        System.out.println(productName);

        nm.notify(id, notif);
    }

    void sendNotifWithActions(int id, Context context, String productName){
        Notification.Builder builder = new Notification.Builder(context);

        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent pContentIntent = PendingIntent.getActivity(context,
                0, contentIntent , 0);

        Intent approveIntent = new Intent(context, ApproveNotification.class);
        approveIntent.putExtra("productName", productName);
        approveIntent.setAction(productName);
        PendingIntent pApproveIntent = PendingIntent.getBroadcast(context,
                0, approveIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent snoozeIntent = new Intent(context, SnoozeNotification.class);
        snoozeIntent.putExtra("productName", productName);
        snoozeIntent.setAction(productName);
        PendingIntent pSnoozeIntent = PendingIntent.getBroadcast(context,
                0, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle("Refill Notification");
        builder.setContentText("You should buy product " + productName);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentIntent(pContentIntent);
        builder.setAutoCancel(false);
        builder.addAction(android.R.drawable.ic_menu_compass, "Approve" , pApproveIntent);
        builder.addAction(android.R.drawable.ic_menu_compass, "Snooze", pSnoozeIntent);

        Notification notif = builder.build();
        nm.notify(id, notif);
    }
}
