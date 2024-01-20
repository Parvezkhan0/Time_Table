package com.example.sudh.mytimetable;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sudo-chawhan on 04/01/18.
 */

public class DaysManager extends FragmentPagerAdapter {

    private Context mContext;

    public DaysManager(Context context, FragmentManager fm){
        super(fm);
        mContext=context;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if(position==0){
            return new monFragment();
        }else if(position==1)
            return new tueFragment();
        else if(position==2)
            return new wedFragment();
        else if (position==3)
            return new thuFragment();
        else
            return new friFragment();
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "mon";
        }else if(position==1)
            return "tue";
        else if(position==2)
            return "wed";
        else if (position==3)
            return "thu";
        else
            return "fri";
    }
}
