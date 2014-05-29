
package com.ch.leyu.adapter;

import com.ch.leyu.responseparse.TagResponse;
import com.ch.leyu.ui.StarGirefFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class VideobankPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<TagResponse> mStrings;

    public VideobankPagerAdapter(FragmentManager fm, ArrayList<TagResponse> mStrings) {
        super(fm);
        this.mStrings = mStrings;
    }

    @Override
    public Fragment getItem(int position) {

        return new StarGirefFragment();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mStrings.get(position).getTag();
    }

}
