package com.example.sudh.mytimetable;

/**
 * Created by sudo-chawhan on 12/01/19.
 */

public class GlobalHelper {
    public static int reminder_notification_id = 100;
    public static String reminder_channel_id = "100";

    public static String getStringSlotTime(int slot){
        switch (slot){
            case 1: return "9:00  am";
            case 2: return "10:00 am";
            case 3: return "11:00 am";
            case 4: return "12:00 pm";
            case 5: return "2:30  pm";
            case 6: return "4:00  pm";
            default: return null;
        }
    }
}
