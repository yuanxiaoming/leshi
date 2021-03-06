
package com.ch.leyu.adapter;

import com.ch.leyu.widget.view.RestoreFragmentPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

public class LYViewPagerAdapter extends RestoreFragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;

    private ArrayList<String> mStrings;

    public LYViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> mFragments,ArrayList<String> mStrings) {
        super(fm);
        this.mFragments = mFragments;
        this.mStrings = mStrings;
    }

    @Override
    public Fragment getItem(int position) {

        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mStrings.get(position);
    }

}
