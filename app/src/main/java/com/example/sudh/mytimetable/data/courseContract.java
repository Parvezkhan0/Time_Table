package com.example.sudh.mytimetable.data;

import android.provider.BaseColumns;

/**
 * Created by sudo-chawhan on 26/12/17.
 */

public final class courseContract {
    public static final class courseEntry implements BaseColumns{
        public static final String TABLE_NAME = "courses";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_SLOT_ID = "slot_id";
        public static final String COLUMN_DAY_ID = "day_id";
        public static final String COLUMN_NAME = "course_name";
        public static final String COLUMN_CODE = "course_code";
        public static final String COLUMN_ROOM = "room";
        public static final String COLUMN_PROF = "prof";
        public static final String COLUMN_NOTES = "notes";
        public static final String IS_EMPTY = "is_empty";
    }
}
