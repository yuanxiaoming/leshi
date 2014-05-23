
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.VideoDetailPagerAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.VideoPlayResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.view.LYViewPager;
import com.ch.leyu.view.PagerSlidingTabStrip;

import org.apache.http.Header;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

/***
 * 视频详情
 * 
 * @author L
 */
public class VideoPlayActivity extends BaseActivity {

    private LYViewPager mViewPager;

    private PagerSlidingTabStrip mSlideTabIndicator;

    private WebView mWebView;

    private String mId;

    private VideoDetailPagerAdapter mAdapter;

    @Override
    protected void getExtraParams() {
        Intent intent = getIntent();
        if (intent != null) {
            mId = intent.getStringExtra(Constant.UID);
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
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        requestData(mId, Constant.URL + Constant.VIDEO_URL + Constant.VIDEO_DETAIL);
    }

    private void requestData(String mid, String url) {
        RequestParams params = new RequestParams();
        params.put("id", mid);
        JHttpClient.get(this, url, params, VideoPlayResponse.class,new DataCallback<VideoPlayResponse>() {

                    @Override
                    public void onStart() {
                        mHttpLoadingView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, VideoPlayResponse data) {
                        if (data != null) {
                            mAdapter = new VideoDetailPagerAdapter(getSupportFragmentManager(),data.getVideoInfo());
                            mViewPager.setAdapter(mAdapter);
                            mSlideTabIndicator.setViewPager(mViewPager);
                            mSlideTabIndicator.setTextSize(24);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,Exception exception) {

                    }

                    @Override
                    public void onFinish() {
                        mHttpLoadingView.setVisibility(View.GONE);
                    }
                });
    }
}
