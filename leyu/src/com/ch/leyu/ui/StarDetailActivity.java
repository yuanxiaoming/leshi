
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.LYViewPagerAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.StarDetailResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.ch.leyu.widget.view.LYViewPager;
import com.ch.leyu.widget.view.PagerSlidingTabStrip;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/***
 * 明星详情
 *
 * @author L
 */

public class StarDetailActivity extends BaseActivity implements OnClickListener {

    /** 明星ID */
    private String uid;

    /** 明星头像 */
    private ImageView mImageView;

    /** 明星名字 */
    private TextView mName;

    /** 明星详情 */
    private TextView mDetail;

    private LYViewPager mLyViewPager;

    private PagerSlidingTabStrip mTabStrip;

    private ArrayList<String> mTitles;

    private ArrayList<Fragment> mFragments;

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
        mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.star_act_pagertab);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        mLyViewPager.setAdapter(new LYViewPagerAdapter(getSupportFragmentManager(), addFragment(),
                addTitle()));
        mTabStrip.setViewPager(mLyViewPager);
        mTabStrip.setTextSize(24);

        RequestParams params = new RequestParams();
        params.put(Constant.UID, uid);
        JHttpClient.get(this, Constant.URL + Constant.STAR_DETAIL, params,StarDetailResponse.class, new DataCallback<StarDetailResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, StarDetailResponse data) {
                       if(data!=null){
                           mName.setText(data.getUserInfo().getNickname());
                           ImageLoader.getInstance().displayImage(data.getUserInfo().getThumb(), mImageView, ImageLoaderUtil.getImageLoaderOptions());
                           mDetail.setText(data.getUserInfo().getDetail());
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

    private ArrayList<String> addTitle() {
        mTitles = new ArrayList<String>();
        mTitles.add("最新上传");
        mTitles.add("最多播放");
        return mTitles;
    }

    private ArrayList<Fragment> addFragment() {
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new NewVideoFragment());
        mFragments.add(new HotsVideoFragment());
        return mFragments;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_search:
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;

            case R.id.action_feedback:
                intent = new Intent(this,FeedbackActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    
}
