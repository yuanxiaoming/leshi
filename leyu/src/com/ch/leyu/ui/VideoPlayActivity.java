
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.LYViewPagerAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.VideoPlayResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.view.LYViewPager;
import com.ch.leyu.view.PagerSlidingTabStrip;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.webkit.WebView;

import java.util.ArrayList;

/***
 * 视频详情
 *
 * @author L
 */
public class VideoPlayActivity extends BaseActivity {

    private LYViewPager mViewPager;

    private PagerSlidingTabStrip mSlideTabIndicator;

    private LYViewPagerAdapter mLyViewPagerAdapter;

    private WebView mWebView ;

    private String mId ;

    private ArrayList<Fragment> mFragments;

    private ArrayList<String> mTitles;

    private DetailFragment mDetailFragment;

    @Override
    protected void getExtraParams() {
        Intent intent = getIntent();
        if(intent!=null){
            mId = intent.getStringExtra(Constant.UID);
            Log.d("tag", "mId::--"+mId);
        }

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_videodetail);
    }

    @Override
    protected void findViewById() {
        mWebView = (WebView) findViewById(R.id.act_videodetail_webview);
        mViewPager = (LYViewPager) findViewById(R.id.act_videodetail_viewpager);
        mSlideTabIndicator = (PagerSlidingTabStrip) findViewById(R.id.act_videodetail_pagertab);
        mDetailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constant.UID, mId);
        mDetailFragment.setArguments(args);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        mLyViewPagerAdapter = new LYViewPagerAdapter(getSupportFragmentManager(), addFragment(), addTitle());
        mViewPager.setAdapter(mLyViewPagerAdapter);
        mSlideTabIndicator.setViewPager(mViewPager);
        mSlideTabIndicator.setTextSize(24);
    }

    private ArrayList<Fragment> addFragment(){
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new CommentFragment());
        mFragments.add(mDetailFragment);
        mFragments.add(new RecommendFragment());

        return mFragments;
    }

    private ArrayList<String> addTitle(){
        mTitles = new ArrayList<String>();
        mTitles.add("评论");
        mTitles.add("详情");
        mTitles.add("相关推荐");

        return mTitles;
    }


    }
