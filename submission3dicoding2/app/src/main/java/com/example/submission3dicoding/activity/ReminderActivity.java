package com.example.submission3dicoding.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.example.submission3dicoding.R;
import com.example.submission3dicoding.service.AlarmServiceReceiver;
import com.example.submission3dicoding.service.ReturnAlarm;

public class ReminderActivity extends AppCompatActivity {
    private Switch releaseReminder;
    private Switch dailyReminder;
    private AlarmServiceReceiver alarmServiceReceiver;
    private ReturnAlarm returnAlarm;
    private SharedPreferences sharedPreferences;

    private String PREFS_NAME = "REMINDER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        releaseReminder = findViewById(R.id.release_reminder);
        dailyReminder = findViewById(R.id.daily_reminder);
        returnAlarm = new ReturnAlarm();
        alarmServiceReceiver = new AlarmServiceReceiver();

        sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        boolean rms = sharedPreferences.getBoolean("release_switch", false);
        boolean dms = sharedPreferences.getBoolean("daily_remind", false);

        releaseReminder.setChecked(rms);
        dailyReminder.setChecked(dms);

        releaseReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String repeatTime = "08:00";
                    alarmServiceReceiver.setAlarmRepeat(getApplicationContext(),AlarmServiceReceiver.TYPING_RELEASE,repeatTime);
                }else{
                alarmServiceReceiver.cancleAlarm(getApplicationContext());
                }
                SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,0);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putBoolean("release_switch",isChecked);
                edit.apply();
            }
        });
        dailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    String repeatTime ="07:00";
                            String repeatMessage = getString(R.string.checkyuk);
                            returnAlarm.setRepeatingAlarm(getApplicationContext(),repeatTime,repeatMessage);
                }else{
                    returnAlarm.cancelAlarm(getApplicationContext());

                }
            SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,0);
                SharedPreferences.Editor editor =  sharedPreferences.edit();
                editor.putBoolean("daily_remind",isChecked);
                editor.apply();
            }
        });


    }
}
