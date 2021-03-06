package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.LYViewPagerAdapter;
import com.ch.leyu.responseparse.LoginResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.ch.leyu.utils.SharedPreferencesUtil;
import com.ch.leyu.widget.view.PagerSlidingTabStrip;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyZoneActivity extends BaseActivity {
    
    private ImageView mIcon ;
    
    private TextView mName ;
    
    private PagerSlidingTabStrip mSlideTabIndicator;

    private ViewPager mViewPager; 
    
    private ArrayList<String> mTitleList;

    private ArrayList<Fragment> mFragmentList;
    
    private LYViewPagerAdapter mVPagerAdapter;
    
    private SharedPreferencesUtil mPreferencesUtil;
    
    private LoginResponse mResponse;

    public LoginResponse getmResponse() {
        return mResponse;
    }

    public void setmResponse(LoginResponse mResponse) {
        this.mResponse = mResponse;
    }

    @Override
    protected void getExtraParams() {
        Intent intent = getIntent();
        if(intent!=null){
            mResponse = (LoginResponse) intent.getSerializableExtra(Constant.DATA);
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.act_my_zone);
    }

    @Override
    protected void loadfindViewById() {
        mIcon = (ImageView) findViewById(R.id.act_zone_img_icon);
        mName = (TextView) findViewById(R.id.act_zone_txt_name);
        mSlideTabIndicator = (PagerSlidingTabStrip) findViewById(R.id.act_zone_tabstrip);
        mViewPager = (ViewPager) findViewById(R.id.act_zone_viewpager);
        mPreferencesUtil = SharedPreferencesUtil.getInstance(this);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        if(mResponse!=null){
            mName.setText(mResponse.getUserInfo().getNickname());
            ImageLoader.getInstance().displayImage(mResponse.getUserInfo().getThumb(), mIcon, ImageLoaderUtil.getImageLoaderOptions());
        }
        mVPagerAdapter = new LYViewPagerAdapter(getSupportFragmentManager(), addFragment(), addTitle());
        mViewPager.setAdapter(mVPagerAdapter);
        mSlideTabIndicator.setViewPager(mViewPager);
        final int textSize = (int) getResources().getDimension(R.dimen.tab_title_size);
        mSlideTabIndicator.setTextSize(textSize);
    }

    @Override
    protected void reload() {

    }
    
    private ArrayList<String> addTitle() {
        mTitleList = new ArrayList<String>();
        mTitleList.add("我的视频");
        mTitleList.add("我的订阅");
        mTitleList.add("观看历史");
        return mTitleList;
    }

    private ArrayList<Fragment> addFragment() {
        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new MyVideosFragment());
        mFragmentList.add(new MySubscriptionFragment());
        mFragmentList.add(new HistoryRecordFragment());
        return mFragmentList;

    }
    
    
    @Override
    protected void onStart() {
        super.onStart();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.zone);
        actionBar.setLogo(R.drawable.legames_back);
        actionBar.setHomeButtonEnabled(true);
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
                
            case R.id.action_quit:
//                mPreferencesUtil.clear();
                mPreferencesUtil.putBoolean(Constant.TAG, false);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                break;
        }

        return true;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.zone, menu);
        return true;
    }
    

}
