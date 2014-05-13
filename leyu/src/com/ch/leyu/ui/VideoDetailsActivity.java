
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.LYViewPagerAdapter;
import com.ch.leyu.view.LYViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/***
 * 暂无接口
 * @author Administrator
 *
 */
public class VideoDetailsActivity extends BaseActivity {

    private LYViewPager mViewPager;

    private PagerTabStrip mTabStrip;

    private ImageView mImageView;

    private ArrayList<String> mTitleList;

    private ArrayList<Fragment> mFragmentList;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_video);
    }

    @Override
    protected void findViewById() {
        mImageView = (ImageView) findViewById(R.id.activity_video_imageView);
        mViewPager = (LYViewPager) findViewById(R.id.activity_video_viewpager);
        mTabStrip = (PagerTabStrip) findViewById(R.id.activity_video_pagertab);
        // 设置下划线的颜色
        mTabStrip.setTabIndicatorColor(getResources().getColor(android.R.color.holo_green_dark));
        // 设置背景的颜色
        mTabStrip.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        mViewPager.setAdapter(new LYViewPagerAdapter(getSupportFragmentManager(), addFragment(),
                addTitle()));
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {

    }

    private ArrayList<String> addTitle() {
        mTitleList = new ArrayList<String>();
        mTitleList.add("全部");
        mTitleList.add("英雄联盟");
        mTitleList.add("其他");

        return mTitleList;
    }

    private ArrayList<Fragment> addFragment() {
        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new AllFragment());
        mFragmentList.add(new AllFragment());
        mFragmentList.add(new AllFragment());

        return mFragmentList;

    }

}
