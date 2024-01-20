package com.example.sudh.mytimetable;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.sudh.mytimetable.data.courseHelper;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    courseHelper mDbHelper = new courseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        DaysManager adapter = new DaysManager(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        Log.d(TAG, "onCreate: here 1.0");
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        int currentPosition = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        viewPager.setCurrentItem(currentPosition);
        tabLayout.setupWithViewPager(viewPager);
        Log.d(TAG, "onCreate: here 1.1");
        createNotificationChannel();

//        setAlarm(getApplicationContext());

        //
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d(TAG, "onCreate: here 1.2");
        notificationManager.notify(GlobalHelper.reminder_notification_id, DaysNotificationPublisher.makeNotification(getApplicationContext()));
        //
        Log.d(TAG, "onCreate: here 1.3");
    }

    private void setAlarm(Context context){
        // set a repeating alarm
        int alarmHr = 00;
        int alarmMn = 41;
        Intent notificationIntent = new Intent(context, DaysNotificationPublisher.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        /////
        // get a Calendar object with current time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, alarmHr);
//        calendar.set(Calendar.MINUTE, alarmMn);

        AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, sender);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "helloName";
            String description = "Notification for notify";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(GlobalHelper.reminder_channel_id, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
