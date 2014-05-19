
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.LYViewPagerAdapter;
import com.ch.leyu.view.LYViewPager;
import com.ch.leyu.view.PagerSlidingTabStrip;

import android.support.v4.app.Fragment;
import android.widget.Button;

import java.util.ArrayList;

/** 炉石传说视频库 */
public class VideosActivity extends BaseActivity {
    private PagerSlidingTabStrip mSlideTabIndicator;

    private LYViewPager mViewPager;

    /** viewPager的title */
    private ArrayList<String> mTitie;
    
    private ArrayList<Fragment>mFragments;

    private Button mButton;

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_videos);
    }

    @Override
    protected void findViewById() {
        mSlideTabIndicator = (PagerSlidingTabStrip) findViewById(R.id.act_videos_tabstrip);
        mViewPager = (LYViewPager) findViewById(R.id.act_videos_viewpager);
        mButton = (Button) findViewById(R.id.act_videos_bt);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        
        mViewPager.setAdapter(new LYViewPagerAdapter(getSupportFragmentManager(),addFragment(),addTitle()));
        mSlideTabIndicator.setViewPager(mViewPager);
        mSlideTabIndicator.setTextSize(24);
        
       
    }

    private ArrayList<String> addTitle() {
        mTitie = new ArrayList<String>();
        mTitie.add("全部");
        mTitie.add("教学");
        mTitie.add("解说");
        mTitie.add("搞笑");
        mTitie.add("术士");
        mTitie.add("法师");
        mTitie.add("盗贼");
        mTitie.add("牧师");
        mTitie.add("战士");
        mTitie.add("萨满");
        mTitie.add("德鲁伊");
        mTitie.add("圣骑士");

        return mTitie;

    }
    
    private ArrayList<Fragment> addFragment() {
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new StarGirefFragment());
        mFragments.add(new StarGirefFragment());
        mFragments.add(new StarGirefFragment());
        mFragments.add(new StarGirefFragment());
        mFragments.add(new StarGirefFragment());
        mFragments.add(new StarGirefFragment());
        mFragments.add(new StarGirefFragment());
        mFragments.add(new StarGirefFragment());
        mFragments.add(new StarGirefFragment());
        mFragments.add(new StarGirefFragment());
        mFragments.add(new StarGirefFragment());
        mFragments.add(new StarGirefFragment());

        return mFragments;

    }
}
