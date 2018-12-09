package com.example.stacy.refill;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingsPreferences {
    SharedPreferences settingsPreferences;
    Activity activity;
    public SettingsPreferences(Activity activity){
        this.activity = activity;
        settingsPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
    }

    public int getDaysBeforeRunout(){
        return settingsPreferences.getInt("days_before_runout", 3);
    }

    public void setDaysBeforeRunout(int days){
        SharedPreferences.Editor editor = settingsPreferences.edit();
        editor.putInt("days_before_runout", days);
        editor.apply();
    }
}
