package com.example.stacy.refill.Alarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.example.stacy.refill.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        //TODO check producrs in db and push notifications
        //Next code it's just an example to test
        // For our recurring task, we'll just display a message
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();

        nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        //Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        sendNotif(1, pendingIntent, context);
        //AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //TODO remove WAKEUP, just for testing
        // set 1000* 3600*24 - one day
        //manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000*30, pendingIntent);

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
}
