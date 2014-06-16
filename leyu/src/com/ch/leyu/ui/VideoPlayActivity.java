
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.ShareGridViewAdapter;
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
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

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
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.play, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                showPopup();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    
    
    public void showPopup() {
        View popView = LayoutInflater.from(this).inflate(R.layout.play_popopwindow, null);
        GridView gridView = (GridView) popView.findViewById(R.id.menu_pop_gridview);
        ShareGridViewAdapter shareGridViewAdapter = new ShareGridViewAdapter();
        gridView.setAdapter(shareGridViewAdapter);

        PopupWindow popWindow = new PopupWindow(popView);
        popWindow.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        popWindow.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setFocusable(true);
        popWindow.setTouchable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setAnimationStyle(R.style.popwindow_anim_style);
        popWindow.showAtLocation(mImg, Gravity.BOTTOM|Gravity.LEFT, 0, 0);
    }
    
    
    
    
    
}
