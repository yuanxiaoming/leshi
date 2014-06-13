
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.VideoDetailPagerAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.VideoPlayResponse;
import com.ch.leyu.utils.CommonUtil;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.ch.leyu.widget.view.LYViewPager;
import com.ch.leyu.widget.view.PagerSlidingTabStrip;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;

/***
 * 视频播放
 * 
 * @author L
 */
public class VideoPlayActivity extends BaseActivity {

    private LYViewPager mViewPager;

    private PagerSlidingTabStrip mSlideTabIndicator;

    private ImageView mImg;

    private String mId;

    private VideoDetailPagerAdapter mAdapter;

    @Override
    protected void getExtraParams() {
        Intent intent = getIntent();
        if (intent != null) {
            mId = intent.getStringExtra(Constant.CID);
        }

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_videodetail);
    }

    @Override
    protected void findViewById() {
        mImg = (ImageView) findViewById(R.id.act_videodetail_img);
        mViewPager = (LYViewPager) findViewById(R.id.act_videodetail_viewpager);
        mSlideTabIndicator = (PagerSlidingTabStrip) findViewById(R.id.act_videodetail_pagertab);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        mImg.setLayoutParams(new LinearLayout.LayoutParams(CommonUtil.getWidthMetrics(mContext) / 1, CommonUtil.getWidthMetrics(mContext) / 2));
        mImg.setScaleType(ScaleType.FIT_XY);
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
                            mAdapter = new VideoDetailPagerAdapter(getSupportFragmentManager(),data.getVideoInfo(),mId);
                            mViewPager.setAdapter(mAdapter);
                            mSlideTabIndicator.setViewPager(mViewPager);
                            mSlideTabIndicator.setTextSize(24);
                            ImageLoader.getInstance().displayImage(data.getVideoInfo().getImageSrc(), mImg,
                                    ImageLoaderUtil.getImageLoaderOptions());
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
