
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.LYViewPagerAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.StarDetailResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.ch.leyu.utils.SharedPreferencesUtil;
import com.ch.leyu.widget.view.LYViewPager;
import com.ch.leyu.widget.view.PagerSlidingTabStrip;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
    
    /**订阅*/
    private Button mSubscription;

    private LYViewPager mLyViewPager;

    private PagerSlidingTabStrip mTabStrip;

    private ArrayList<String> mTitles;

    private ArrayList<Fragment> mFragments;
    
    private String name ="";
    
    /**判断是否已经登录*/
    private boolean mIsLogin ; 
    
    private String mLoginUid ;
    
    private String mAuth ;
    
    private String mPassStr ;
    
    private SharedPreferencesUtil mPreferencesUtil;
    
    private int mSubscribeId;
  
    private boolean mIsStarTag;  
    
    @Override
    public void onClick(View v) {
         
    }

    @Override
    protected void getExtraParams() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            uid = bundle.getString(Constant.UID);
            name = bundle.getString("name");
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_stardetail);
    }

    @Override
    protected void loadfindViewById() {
        mImageView = (ImageView) findViewById(R.id.star_act_img);
        mName = (TextView) findViewById(R.id.star_act_tv_name);
        mDetail = (TextView) findViewById(R.id.star_act_tv_detail);
        mLyViewPager = (LYViewPager) findViewById(R.id.star_act_viewpager);
        mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.star_act_pagertab);
        mSubscription = (Button) findViewById(R.id.act_star_bt_subscription);
        mPreferencesUtil = SharedPreferencesUtil.getInstance(this);
        getSharedPreferences(mPreferencesUtil);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(name);
        actionBar.setLogo(R.drawable.legames_back);
        actionBar.setHomeButtonEnabled(true);
        }
    
    @Override
    protected void onResume() {
        super.onResume();
        getSharedPreferences(mPreferencesUtil);
        if(mIsStarTag==true){
            subscription("mySubscribe", "add", uid, mAuth, mLoginUid, mPassStr);
            mPreferencesUtil.putBoolean(Constant.STAR_TAG, false);
        }
    }

    public void getSharedPreferences(SharedPreferencesUtil mPreferencesUtil){
        mIsLogin = mPreferencesUtil.getBoolean(Constant.TAG, false);
        mAuth = mPreferencesUtil.getString(Constant.AUTH, "");
        mLoginUid = mPreferencesUtil.getString(Constant.LOGIN_UID, "");
        mPassStr = mPreferencesUtil.getString(Constant.PASS_STR, "");
        mIsStarTag = mPreferencesUtil.getBoolean(Constant.STAR_TAG, false);
    }
    
    @Override
    protected void setListener() {
        mSubscription.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if(!mIsLogin){
                    Intent intent = new Intent(StarDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    subscription("mySubscribe", "add", uid, mAuth, mLoginUid, mPassStr);
                }
                
            }
        });
    }

    @Override
    protected void processLogic() {
        mLyViewPager.setAdapter(new LYViewPagerAdapter(getSupportFragmentManager(), addFragment(),addTitle()));
        mTabStrip.setViewPager(mLyViewPager);
        final int textSize = (int)getResources().getDimension(R.dimen.tab_title_size);
        mTabStrip.setTextSize(textSize);
        requestData();

    }
    
    private void requestData() {
        RequestParams params = new RequestParams();
        params.put(Constant.UID, uid);
        if(mIsLogin==true){
            params.put(Constant.LOGIN_UID, mLoginUid);
        }
        JHttpClient.getFromServer(this, Constant.URL + Constant.STAR_DETAIL, params,StarDetailResponse.class, new DataCallback<StarDetailResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, StarDetailResponse data) {
                       if(data!=null){
                           mName.setText(data.getUserInfo().getNickname());
                           ImageLoader.getInstance().displayImage(data.getUserInfo().getThumb(), mImageView, ImageLoaderUtil.getImageLoaderOptions());
                           mDetail.setText(data.getUserInfo().getDetail());
                           mSubscribeId = data.getSubscribeId();
                           if(mSubscribeId>0){
                               mSubscription.setText("已订阅");
                               mSubscription.setClickable(false);
                           }
                       }
                    }

                    @Override
                    public void onStart() {
                        mHttpErrorView.setVisibility(View.GONE);
                        mHttpLoadingView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        mHttpLoadingView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,
                            Exception exception) {
                        mHttpLoadingView.setVisibility(View.GONE);
                    }
                });
    }
    
    private void subscription(String action,String sort,String uid,String auth,String loginUid,String passStr){
        RequestParams params = new RequestParams();
        params.put("action", action);
        params.put(Constant.SORT, sort);
        params.put(Constant.UID, uid);
        params.put(Constant.AUTH,auth);
        params.put(Constant.LOGIN_UID,loginUid);
        params.put(Constant.PASS_STR,passStr);
        JHttpClient.getFromServer(this, Constant._URL, params, StarDetailResponse.class, new DataCallback<StarDetailResponse>() {

            @Override
            public void onStart() {
                
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, StarDetailResponse data) {
                    mSubscription.setText("已订阅");
                    mSubscription.setClickable(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                    Exception exception) {
                
            }

            @Override
            public void onFinish() {
                
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

   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.star, menu);
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
            case android.R.id.home:
                finish();
                return true;
                
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void reload() {
        requestData();
        
    }
    
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    
}
