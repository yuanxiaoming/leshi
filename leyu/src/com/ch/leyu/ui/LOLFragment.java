
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.HeadofAllFragmentPagerAdapter;
import com.ch.leyu.adapter.LOLViewPagerAdapter;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.HSResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.view.AutoScrollViewPager;
import com.ch.leyu.view.CircleLoopPageIndicator;
import com.ch.leyu.view.LYViewPager;
import com.ch.leyu.view.PagerSlidingTabStrip;

import org.apache.http.Header;

import android.view.View;

/***
 * 首页--英雄联盟
 *
 * @author L
 */
public class LOLFragment extends BaseFragment {

    /** 焦点栏viewpager */
    private AutoScrollViewPager mfocusViewPager;

    /** 焦点栏ViewPager指示点 */
    private CircleLoopPageIndicator mPageIndicator;

    private LYViewPager mViewPager;

    private PagerSlidingTabStrip mSlideTabIndicator;

    private LOLViewPagerAdapter mPagerAdapter;

    private View mView;

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_lol);

    }

    @Override
    protected void findViewById() {
        mViewPager = (LYViewPager) findViewById(R.id.fragment_lol_viewpager);
        mSlideTabIndicator = (PagerSlidingTabStrip) findViewById(R.id.fragment_lol_pagertab);
        mView = findViewById(R.id.fragment_lol_include);
        mfocusViewPager = (AutoScrollViewPager) mView.findViewById(R.id.all_auto_scroll_viewpager);
        mPageIndicator = (CircleLoopPageIndicator) mView.findViewById(R.id.all_cirle_pageindicator);

    }

    @Override
    protected void setListener() {
        
    }

    @Override
    protected void processLogic() {
        mPagerAdapter = new LOLViewPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mSlideTabIndicator.setViewPager(mViewPager);
        mSlideTabIndicator.setTextSize(24);
        JHttpClient.get(getActivity(), Constant.URL + Constant.LOL_URL, null, HSResponse.class,
                new DataCallback<HSResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, HSResponse data) {
                        if (data != null) {
                            mfocusViewPager.startAutoScroll(2000);
                            mfocusViewPager.setCurrentItem(data.getFocus().size() * 10000);
                            mPageIndicator.setPageCount(data.getFocus().size());
                            mfocusViewPager.setAdapter(new HeadofAllFragmentPagerAdapter(getActivity(), data.getFocus()));
                            mPageIndicator.setViewPager(mfocusViewPager);
                        }

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

}
