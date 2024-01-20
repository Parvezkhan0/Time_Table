package com.example.sudh.mytimetable;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.example.sudh.mytimetable.data.courseHelper;
import com.example.sudh.mytimetable.data.courseContract;


/**
 * Created by sudo-chawhan on 04/01/18.
 */

public class tueFragment extends android.support.v4.app.Fragment {

    private String TAG = "MainActivity";
    int currentDayId=2;
    courseHelper mDbHelper;
    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.day_fragment,container,false);
        Log.d(TAG, "onCreateView: before init table");
        mDbHelper = new courseHelper(getActivity().getApplicationContext());
        courseHelper.InitTableData(mDbHelper);
        Log.d(TAG, "onCreateView: before set listener");
        setListeners();
        Log.d(TAG, "onCreateView: before display content");
        courseHelper.displayContent(rootView, mDbHelper, currentDayId, getContext());
        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        courseHelper.displayContent(rootView, mDbHelper, currentDayId, getContext());
    }

    public void setListeners(){
        // setting layout references
        LinearLayout slot_one = (LinearLayout) rootView.findViewById(R.id.slot_one);
        LinearLayout slot_two = (LinearLayout) rootView.findViewById(R.id.slot_two);
        LinearLayout slot_three = (LinearLayout) rootView.findViewById(R.id.slot_three);
        LinearLayout slot_four = (LinearLayout) rootView.findViewById(R.id.slot_four);
        LinearLayout slot_five = (LinearLayout) rootView.findViewById(R.id.slot_five);
        LinearLayout slot_six = (LinearLayout) rootView.findViewById(R.id.slot_six);
        if(slot_one==null) Log.d(TAG, "setListeners: after setiing up view ids *");
        //setting on clock listeners for each slot
        slot_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditorActivity.class);
                Log.d(TAG, "onClick: hahaha");
                intent.putExtra(courseContract.courseEntry.COLUMN_SLOT_ID,1);
                intent.putExtra(courseContract.courseEntry.COLUMN_DAY_ID,currentDayId);
                Log.d(TAG, "onClick: hahaha");
                tueFragment.this.startActivity(intent);
            }
        });
        slot_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(),EditorActivity.class);
                intent.putExtra(courseContract.courseEntry.COLUMN_SLOT_ID,2);
                intent.putExtra(courseContract.courseEntry.COLUMN_DAY_ID,currentDayId);
                tueFragment.this.startActivity(intent);
            }
        });
        slot_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditorActivity.class);
                intent.putExtra(courseContract.courseEntry.COLUMN_SLOT_ID,3);
                intent.putExtra(courseContract.courseEntry.COLUMN_DAY_ID,currentDayId);
                tueFragment.this.startActivity(intent);
            }
        });
        slot_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditorActivity.class);
                intent.putExtra(courseContract.courseEntry.COLUMN_SLOT_ID,4);
                intent.putExtra(courseContract.courseEntry.COLUMN_DAY_ID,currentDayId);
                tueFragment.this.startActivity(intent);
            }
        });
        slot_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditorActivity.class);
                intent.putExtra(courseContract.courseEntry.COLUMN_SLOT_ID,5);
                intent.putExtra(courseContract.courseEntry.COLUMN_DAY_ID,currentDayId);
                tueFragment.this.startActivity(intent);
            }
        });
        slot_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditorActivity.class);
                intent.putExtra(courseContract.courseEntry.COLUMN_SLOT_ID,6);
                intent.putExtra(courseContract.courseEntry.COLUMN_DAY_ID,currentDayId);
                tueFragment.this.startActivity(intent);
            }
        });
    }

}
