
package com.ch.leyu.adapter;

import com.ch.leyu.responseparse.TagResponse;
import com.ch.leyu.responseparse.VideoBankResponse;
import com.ch.leyu.ui.VideoBankFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class VideobankPagerAdapter extends FragmentStatePagerAdapter {


    private VideoBankResponse mVideoBankResponse;
    private VideoBankFragment videoBankFragment;

    public  static final String POSITION ="position";

    private ArrayList<TagResponse> mTagResponse;

    public VideobankPagerAdapter(FragmentManager fm, VideoBankResponse videoBankResponse) {
        super(fm);
        this.mVideoBankResponse=videoBankResponse;
        mTagResponse = mVideoBankResponse.getTags();

    }

    @Override
    public Fragment getItem(int position) {
        videoBankFragment = new VideoBankFragment();
        Bundle bundle=new Bundle();
        bundle.putCharSequence(POSITION, mTagResponse.get(position).getKeyword());
        videoBankFragment.setArguments(bundle);
        return videoBankFragment;
    }

    @Override
    public int getCount() {
        return mVideoBankResponse.getTags().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTagResponse.get(position).getTag();
    }



}
