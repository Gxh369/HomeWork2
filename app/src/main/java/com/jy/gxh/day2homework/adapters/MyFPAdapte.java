package com.jy.gxh.day2homework.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by GXH on 2019/9/19.
 */

public class MyFPAdapte extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;

    public MyFPAdapte(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
