
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.LYViewPagerAdapter;
import com.ch.leyu.adapter.ViewFlowAdapter;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.HSResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.view.CircleFlowIndicator;
import com.ch.leyu.view.LYViewFlow;
import com.ch.leyu.view.LYViewPager;
import com.ch.leyu.view.PagerSlidingTabStrip;

import org.apache.http.Header;

import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;

/***
 * 首页--英雄联盟
 * 
 * @author L
 */
public class LOLFragment extends BaseFragment {
    /** 滚动新闻 */
    private LYViewFlow mViewFlow;

    /** ViewFlow指示器 */
    private CircleFlowIndicator mIndicator;

    /** 滑动显示组件 */
    private LYViewPager mViewPager;

    /** ViewPager指示器 */
    private PagerSlidingTabStrip mSlideTabIndicator ;

    private ArrayList<String> mTitleList;

    private ArrayList<Fragment> mFragmentList;

    private LYViewPagerAdapter mPagerAdapter;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_lol);

    }

    @Override
    protected void findViewById() {
        mViewFlow = (LYViewFlow) findViewById(R.id.lol_viewflow);
        mIndicator = (CircleFlowIndicator) findViewById(R.id.lol_viewflowindic);
        mViewPager = (LYViewPager) findViewById(R.id.lol_viewpager);
        mSlideTabIndicator = (PagerSlidingTabStrip) findViewById(R.id.lol_pagertab);
        mPagerAdapter = new LYViewPagerAdapter(getChildFragmentManager(), addFragment(), addTitle());
       

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        mViewPager.setAdapter(mPagerAdapter);
        mSlideTabIndicator.setViewPager(mViewPager);
        mSlideTabIndicator.setTextSize(24);
        JHttpClient.get(getActivity(), Constant.URL + Constant.LOL_URL, null, HSResponse.class,
                new DataCallback<HSResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, HSResponse data) {
                        mViewFlow.setmSideBuffer(data.getFocus().size()); // 实际图片张数，
                        mViewFlow.setFlowIndicator(mIndicator);
                        mViewFlow.setTimeSpan(3000);
                        mViewFlow.setSelection(3 * 1000); // 设置初始位置
                        mViewFlow.startAutoFlowTimer(); // 启动自动播放
                        mViewFlow.setAdapter(new ViewFlowAdapter(getActivity(), data.getFocus()));
                    }

                    @Override
                    public void onStart() {
                        mHttpLoadingView.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onFinish() {
                        mHttpLoadingView.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,
                            Exception exception) {

                    }

                });
    }

    private ArrayList<String> addTitle() {
        mTitleList = new ArrayList<String>();
        mTitleList.add("全部");
        mTitleList.add("本周热门");
        mTitleList.add("教学视频");
        mTitleList.add("解说视频");

        return mTitleList;
    }

    private ArrayList<Fragment> addFragment() {
        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new LOLVedioFragment());
        mFragmentList.add(new LOLVedioFragment());
        mFragmentList.add(new LOLVedioFragment());
        mFragmentList.add(new LOLVedioFragment());
        return mFragmentList;

    }

}
