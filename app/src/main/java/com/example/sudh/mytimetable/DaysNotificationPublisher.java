package com.example.sudh.mytimetable;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.sudh.mytimetable.data.courseContract;
import com.example.sudh.mytimetable.data.courseHelper;


import java.util.Calendar;

/**
 * Created by sudo-chawhan on 12/01/19.
 */

public class DaysNotificationPublisher extends BroadcastReceiver {

    // dayOfWeek is according to sunday = 1
    // while our database is according to monday = 1
    static int dayOfWeek = 1;
    @Override
    public void onReceive(Context context, Intent nIntent) {
        Toast.makeText(context, "recieved broadcast", Toast.LENGTH_SHORT);
        Calendar c = Calendar.getInstance();
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if(true || dayOfWeek != Calendar.FRIDAY && dayOfWeek != Calendar.SATURDAY){

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(GlobalHelper.reminder_notification_id, makeNotification(context));
        }
    }

    // makes a notification depending on day and next day classes
    public static Notification makeNotification(Context context){
        String TAG = "DNP";

        Intent intent = new Intent(context, MainActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, GlobalHelper.reminder_channel_id)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Reminder")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        ///

        /// find classes for next day
        Cursor cursor=null;

        courseHelper mDbhelper = new courseHelper(context);

        SQLiteDatabase database = mDbhelper.getReadableDatabase();
        String[] columns = new String[]{
                courseContract.courseEntry.COLUMN_SLOT_ID ,courseContract.courseEntry.COLUMN_NAME, courseContract.courseEntry.COLUMN_ROOM, courseContract.courseEntry.IS_EMPTY
        };
        // setting argument
        String[] selectionArgs = new String[]{
                String.valueOf(1)                           //////// get cursor depending on next day
        };
        cursor = database.query(courseContract.courseEntry.TABLE_NAME, columns, courseContract.courseEntry.COLUMN_DAY_ID+"=?", selectionArgs,null,null,null);
        cursor.moveToFirst();

        NotificationCompat.InboxStyle nItems = new NotificationCompat.InboxStyle();

        String contentText = "Tomorrow first class at ";
        Log.d(TAG, "makeNotification: here 2.1");
        boolean foundNone = true;
        for(int i=0; i<cursor.getCount(); i++){
//            Log.d(TAG, "makeNotification: cursor empty is " + cursor.getColumnIndex(courseContract.courseEntry.IS_EMPTY));
            if(cursor.getInt(cursor.getColumnIndex(courseContract.courseEntry.IS_EMPTY))==0){
                String slotString = GlobalHelper.getStringSlotTime(cursor.getInt(cursor.getColumnIndex(courseContract.courseEntry.COLUMN_SLOT_ID)));
                if(foundNone) contentText += slotString;
                foundNone = false;
                String itemText = slotString + " - ";
                itemText += cursor.getString(cursor.getColumnIndex(courseContract.courseEntry.COLUMN_NAME));
                String room = cursor.getString(cursor.getColumnIndex(courseContract.courseEntry.COLUMN_ROOM));
                if(!TextUtils.isEmpty(room)) itemText += " in " + room;
                nItems.addLine(itemText);
            }
            cursor.moveToNext();
        }
        Log.d(TAG, "makeNotification: here 2.2");
        if(foundNone) contentText = "Yippee! No class tomorrow";
        else mBuilder.setStyle(nItems);
        ///

        mBuilder.setContentText(contentText);
        return mBuilder.build();
    }

}
