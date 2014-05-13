
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.LYViewPagerAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.StarResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.ch.leyu.view.LYViewPager;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/***
 * 明星详情
 * 
 * @author L
 */
public class StarDetailActivity extends BaseActivity {

    private String uid;

    /** 明星头像 */
    private ImageView mImageView;

    /** 明星名字 */
    private TextView mName;

    /** 明星详情 */
    private TextView mDetail;

    private LYViewPager mLyViewPager;

    private PagerTabStrip mStrip;

    private ArrayList<String> mTitleList;

    private ArrayList<Fragment> mFragmentList;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void getExtraParams() {
        Intent intent = getIntent();
        if (intent != null) {
            uid = intent.getStringExtra(Constant.UID);
        }

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_stardetail);
    }

    @Override
    protected void findViewById() {
        mImageView = (ImageView) findViewById(R.id.star_act_img);
        mName = (TextView) findViewById(R.id.star_act_tv_name);
        mDetail = (TextView) findViewById(R.id.star_act_tv_detail);
        mLyViewPager = (LYViewPager) findViewById(R.id.star_act_viewpager);
        mStrip = (PagerTabStrip) findViewById(R.id.star_act_pagertab);
        mLyViewPager = (LYViewPager) findViewById(R.id.star_act_viewpager);
        // 设置下划线的颜色
        mStrip.setTabIndicatorColor(getResources().getColor(android.R.color.holo_green_dark));
        // 设置背景的颜色
        mStrip.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        mLyViewPager.setAdapter(new LYViewPagerAdapter(getSupportFragmentManager(), addFragment(),
                addTitle()));
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        RequestParams params = new RequestParams();
        params.put(Constant.UID, uid);
        JHttpClient.get(this, Constant.URL + Constant.STAR_DETAIL, params, StarResponse.class,
                new DataCallback<StarResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, StarResponse data) {
                        mName.setText(data.getUserInfo().get(0).getNickname());
                        ImageLoader.getInstance().displayImage(
                                data.getUserInfo().get(0).getThumb(), mImageView,
                                ImageLoaderUtil.getImageLoaderOptions());
                        mDetail.setText(data.getUserInfo().get(0).getDetail());
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,
                            Exception exception) {

                    }
                });

    }

    private ArrayList<String> addTitle() {
        mTitleList = new ArrayList<String>();
        mTitleList.add("最新上传");
        mTitleList.add("最多播放");

        return mTitleList;
    }

    private ArrayList<Fragment> addFragment() {
        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new AllFragment());
        mFragmentList.add(new AllFragment());

        return mFragmentList;
    }
}
