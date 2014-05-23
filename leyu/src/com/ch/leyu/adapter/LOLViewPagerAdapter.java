
package com.ch.leyu.adapter;

import com.ch.leyu.ui.LOLVideoFragment;
import com.ch.leyu.utils.Constant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/***
 * lol首页viewpager适配器
 * 
 * @author L
 */
public class LOLViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> mStrings;

    private LOLVideoFragment mFragment;

    public LOLViewPagerAdapter(FragmentManager fm) {
        super(fm);
        addTitle();
    }

    @Override
    public Fragment getItem(int position) {
        mFragment = new LOLVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.POSITION, position);
        mFragment.setArguments(bundle);

        return mFragment;
    }

    @Override
    public int getCount() {
        if (mStrings != null) {
            return mStrings.size();
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mStrings.get(position);
    }

    private ArrayList<String> addTitle() {
        mStrings = new ArrayList<String>();
        mStrings.add("全部");
        mStrings.add("本周热门");
        mStrings.add("教学视频");
        mStrings.add("解说视频");

        return mStrings;
    }

}
