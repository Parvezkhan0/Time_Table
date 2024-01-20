package com.example.sudh.mytimetable;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.sudh.mytimetable.data.courseContract.courseEntry;
import com.example.sudh.mytimetable.data.courseHelper;


/**
 * Created by sudo-chawhan on 26/12/17.
 */

public class EditorActivity extends AppCompatActivity {

    private String TAG = "EditorActivity";

    EditText mName;
    EditText mCode;
    EditText mRoom;
    EditText mProf;
    EditText mNotes;

    Intent mIntent;
    courseHelper mDbHelper = new courseHelper(this);
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        mName = (EditText) findViewById(R.id.edit_course_name);
         mCode = (EditText) findViewById(R.id.edit_course_code);
         mRoom = (EditText) findViewById(R.id.edit_course_room);
         mProf = (EditText) findViewById(R.id.edit_course_prof);
         mNotes = (EditText) findViewById(R.id.edit_course_notes);
        mIntent = getIntent();
        showView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_save:
                updateCourse();
                Log.d(TAG, "onOptionsItemSelected: save icon is pressed but never came here");
                finish();
                return true;
            case R.id.action_delete:
                eraseCourse();
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    
    private void eraseCourse(){
        // substitue all values as "" in the dataset of the entry
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int isEmpty=0;
        String name = "";
        String code = "";
        String room = "";
        String prof = "";
        String notes = "";
        Log.d(TAG, "updateCourse: string value is name "+name);
        if(TextUtils.isEmpty(name)) isEmpty=1;
        ContentValues values = new ContentValues();
        values.put(courseEntry.COLUMN_NAME,name);
        values.put(courseEntry.COLUMN_CODE,code);
        values.put(courseEntry.COLUMN_ROOM,room);
        values.put(courseEntry.COLUMN_PROF,prof);
        values.put(courseEntry.COLUMN_NOTES,notes);
        values.put(courseEntry.IS_EMPTY,isEmpty);

        int slot_id = mIntent.getIntExtra(courseEntry.COLUMN_SLOT_ID,-1);
        int day_id = mIntent.getIntExtra(courseEntry.COLUMN_DAY_ID,-1);

        String[] selectionArgs = new String[]{
                String.valueOf(slot_id), String.valueOf(day_id)
        };
        Log.d(TAG, "updateCourse: int slot id in intent"+slot_id);
        database.update(courseEntry.TABLE_NAME,values,courseEntry.COLUMN_SLOT_ID + "= ? AND "+courseEntry.COLUMN_DAY_ID +"= ?",selectionArgs);

    }

    private void updateCourse(){

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int isEmpty=0;
        String name = mName.getText().toString().trim();
        String code = mCode.getText().toString().trim();
        String room = mRoom.getText().toString().trim();
        String prof = mProf.getText().toString().trim();
        String notes = mNotes.getText().toString().trim();
        Log.d(TAG, "updateCourse: string value is name "+name);
        if(TextUtils.isEmpty(name)&&TextUtils.isEmpty(room)) isEmpty=1;
        ContentValues values = new ContentValues();
        values.put(courseEntry.COLUMN_NAME,name);
        values.put(courseEntry.COLUMN_CODE,code);
        values.put(courseEntry.COLUMN_ROOM,room);
        values.put(courseEntry.COLUMN_PROF,prof);
        values.put(courseEntry.COLUMN_NOTES,notes);
        values.put(courseEntry.IS_EMPTY,isEmpty);

        int slot_id = mIntent.getIntExtra(courseEntry.COLUMN_SLOT_ID,-1);
        int day_id = mIntent.getIntExtra(courseEntry.COLUMN_DAY_ID,-1);

        String[] selectionArgs = new String[]{
                String.valueOf(slot_id), String.valueOf(day_id)
        };
        Log.d(TAG, "updateCourse: int slot id in intent"+slot_id);
        database.update(courseEntry.TABLE_NAME,values,courseEntry.COLUMN_SLOT_ID + "= ? AND "+courseEntry.COLUMN_DAY_ID +"= ?",selectionArgs);

    }
    private void showView(){
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        int slot_id = mIntent.getIntExtra(courseEntry.COLUMN_SLOT_ID,-1);
        int day_id = mIntent.getIntExtra(courseEntry.COLUMN_DAY_ID,-1);

        String[] selectionArgs = new String[]{
                String.valueOf(slot_id), String.valueOf(day_id)
        };
        Cursor cursor = database.query(courseEntry.TABLE_NAME,null,courseEntry.COLUMN_SLOT_ID + "= ? AND "+courseEntry.COLUMN_DAY_ID +"= ?",selectionArgs,null,null,null);
        cursor.moveToFirst();
        mName.setText(cursor.getString(cursor.getColumnIndex(courseEntry.COLUMN_NAME)));
        mCode.setText(cursor.getString(cursor.getColumnIndex(courseEntry.COLUMN_CODE)));
        mRoom.setText(cursor.getString(cursor.getColumnIndex(courseEntry.COLUMN_ROOM)));
        mProf.setText(cursor.getString(cursor.getColumnIndex(courseEntry.COLUMN_PROF)));
        mNotes.setText(cursor.getString(cursor.getColumnIndex(courseEntry.COLUMN_NOTES)));

        cursor.close();

    }
}
