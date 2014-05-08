
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.LYViewPagerAdapter;
import com.ch.leyu.view.LYViewPager;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.view.View;

import java.util.ArrayList;

public class D extends BaseFragment {

    private LYViewPager mViewPager;

    private PagerTabStrip mTabStrip;

    /** viewpager标头 */
    private ArrayList<String> mTitleList;

    /***/
    private ArrayList<Fragment> mFragmentList;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_d);

    }

    @SuppressLint("InlinedApi")
    @Override
    protected void findViewById() {
        mViewPager = (LYViewPager) findViewById(R.id.d_viewpager);
        mTabStrip = (PagerTabStrip) findViewById(R.id.d_pagertab);
        // 设置下划线的颜色
        mTabStrip.setTabIndicatorColor(getResources().getColor(android.R.color.holo_green_dark));
        // 设置背景的颜色
        mTabStrip.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        mViewPager.setAdapter(new LYViewPagerAdapter(getChildFragmentManager(), addFragment(),
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
