
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.LYViewPagerAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.StarDetailResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.ch.leyu.view.LYViewPager;
import com.ch.leyu.view.TabPageIndicator;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    private TabPageIndicator mIndicator;

    private ArrayList<String> mTitleList;

    private ArrayList<Fragment> mFragmentList;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void getExtraParams() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            uid = bundle.getString(Constant.UID);
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
        mIndicator = (TabPageIndicator) findViewById(R.id.star_act_pagertab);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        mLyViewPager.setAdapter(new LYViewPagerAdapter(getSupportFragmentManager(), addFragment(),
                addTitle()));
        mIndicator.setViewPager(mLyViewPager);
        RequestParams params = new RequestParams();
        params.put(Constant.UID, uid);
        JHttpClient.get(this, Constant.URL + Constant.STAR_DETAIL, params,StarDetailResponse.class, new DataCallback<StarDetailResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, StarDetailResponse data) {
                        mName.setText(data.getUserInfo().getNickname());
                        ImageLoader.getInstance().displayImage(data.getUserInfo().getThumb(),mImageView, ImageLoaderUtil.getImageLoaderOptions());
                        mDetail.setText(data.getUserInfo().getDetail());
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
