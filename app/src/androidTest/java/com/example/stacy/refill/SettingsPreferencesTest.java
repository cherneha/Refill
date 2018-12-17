package com.example.stacy.refill;

import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.*;

public class SettingsPreferencesTest {

    SettingsPreferences settingsPreferences= new SettingsPreferences(InstrumentationRegistry.getContext());
    @Test
    public void getDaysBeforeRunout() {
        int defauldDysBeforeRunout = settingsPreferences.getDaysBeforeRunout();
        assertEquals(defauldDysBeforeRunout, 3);
        settingsPreferences.setDaysBeforeRunout(5);
        int daysBeforeRunout = settingsPreferences.getDaysBeforeRunout();
        assertEquals(daysBeforeRunout, 5);
    }

    @Test
    public void setDaysBeforeRunout() {
        settingsPreferences.setDaysBeforeRunout(10);
        assertEquals(settingsPreferences.getDaysBeforeRunout(), 10);
        settingsPreferences.setDaysBeforeRunout(0);
        assertEquals(settingsPreferences.getDaysBeforeRunout(), 0);
        settingsPreferences.setDaysBeforeRunout(6);
        assertEquals(settingsPreferences.getDaysBeforeRunout(), 6);
    }

    @Test
    public void setNotificationHour() {
        settingsPreferences.setNotificationHour(20);
        assertEquals(settingsPreferences.getNotificationHour(), 20);
        settingsPreferences.setNotificationHour(0);
        assertEquals(settingsPreferences.getNotificationHour(), 0);
    }

    @Test
    public void setNotificationMinutes() {
        settingsPreferences.setNotificationMinutes(2);
        assertEquals(settingsPreferences.getNotificationMinutes(), 2);
        settingsPreferences.setNotificationMinutes(5);
        assertEquals(settingsPreferences.getNotificationMinutes(), 5);
    }

    @Test
    public void getNotificationHour() {
        assertEquals(settingsPreferences.getNotificationHour(), 9);
        settingsPreferences.setNotificationHour(20);
        assertEquals(settingsPreferences.getNotificationHour(), 20);

    }

    @Test
    public void getNotificationMinutes() {
        assertEquals(settingsPreferences.getNotificationMinutes(), 0);
        settingsPreferences.setNotificationMinutes(2);
        assertEquals(settingsPreferences.getNotificationMinutes(), 2);
    }
}