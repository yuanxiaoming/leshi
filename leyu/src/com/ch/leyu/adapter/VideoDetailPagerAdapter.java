
package com.ch.leyu.adapter;

import com.ch.leyu.responseparse.VideoDetailResponse;
import com.ch.leyu.ui.CommentFragment;
import com.ch.leyu.ui.DetailFragment;
import com.ch.leyu.ui.RecommendFragment;
import com.ch.leyu.utils.Constant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class VideoDetailPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragments;

    private ArrayList<String> mStrings;

    private DetailFragment detailFragment;

    private RecommendFragment recommendFragment;

    private VideoDetailResponse mResponse;
    
    private CommentFragment commentFragment;
    
    private String mid;

    public VideoDetailPagerAdapter(FragmentManager fm, VideoDetailResponse Response,String mid) {
        super(fm);
        this.mResponse = Response;
        this.mid = mid;
        addFragment();
        addTitle();
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = null;
        if (position == 0) {
            args = new Bundle();
            args.putString(Constant.GMAE_ID, mid);
            commentFragment.setArguments(args);
        }
        if (position == 1) {
            args = new Bundle();
            args.putSerializable(Constant.DATA, mResponse);
            detailFragment.setArguments(args);
        }
        if (position == 2) {
            args = new Bundle();
            args.putSerializable(Constant.DATA, mResponse);
            recommendFragment.setArguments(args);
        }

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

    private ArrayList<Fragment> addFragment() {
        detailFragment = new DetailFragment();
        recommendFragment = new RecommendFragment();
        commentFragment = new CommentFragment();
        mFragments = new ArrayList<Fragment>();
        mFragments.add(commentFragment);
        mFragments.add(detailFragment);
        mFragments.add(recommendFragment);

        return mFragments;
    }

    private ArrayList<String> addTitle() {
        mStrings = new ArrayList<String>();
        mStrings.add("评论");
        mStrings.add("详情");
        mStrings.add("相关推荐");

        return mStrings;
    }

}
