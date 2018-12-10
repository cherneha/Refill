package com.example.stacy.refill;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingsPreferences {
    SharedPreferences settingsPreferences;
    Context context;
    public SettingsPreferences(Context context){
        this.context = context;
        settingsPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getDaysBeforeRunout(){
        return settingsPreferences.getInt("days_before_runout", 3);
    }

    public void setDaysBeforeRunout(int days){
        SharedPreferences.Editor editor = settingsPreferences.edit();
        editor.putInt("days_before_runout", days);
        editor.apply();
    }

    public void setNotificationHour(int hour){
        SharedPreferences.Editor editor = settingsPreferences.edit();
        editor.putInt("notification_hour", hour);
        editor.apply();
    }

    public void setNotificationMinutes(int minutes){
        SharedPreferences.Editor editor = settingsPreferences.edit();
        editor.putInt("notification_minute", minutes);
        editor.apply();
    }

    public int getNotificationHour(){
        return settingsPreferences.getInt("notification_hour", 9);
    }

    public int getNotificationMinutes(){
        return settingsPreferences.getInt("notification_minute", 0);
    }
}
