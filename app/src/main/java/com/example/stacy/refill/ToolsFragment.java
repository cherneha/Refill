package com.example.stacy.refill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;

public class ToolsFragment extends Fragment {
    SettingsPreferences settingsPreferences;
    EditText daysBeforeRunoutEdit;
    TimePicker notificationTime;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tools_fragment, container, false);
        settingsPreferences = new SettingsPreferences(getActivity());
        notificationTime = view.findViewById(R.id.notification_time);
        notificationTime.setHour(settingsPreferences.getNotificationHour());
        notificationTime.setMinute(settingsPreferences.getNotificationMinutes());
        daysBeforeRunoutEdit = view.findViewById(R.id.days_before_runout);
        daysBeforeRunoutEdit.setText(String.valueOf(settingsPreferences.getDaysBeforeRunout()));
        return view;
    }

    @Override
    public void onPause(){
        super.onPause();
        int newDaysBeforeRunout  = Integer.valueOf(daysBeforeRunoutEdit.getText().toString());
        settingsPreferences.setDaysBeforeRunout(newDaysBeforeRunout);
        settingsPreferences.setNotificationHour(notificationTime.getHour());
        settingsPreferences.setNotificationMinutes(notificationTime.getMinute());
    }
}
