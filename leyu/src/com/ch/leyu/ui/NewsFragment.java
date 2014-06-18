
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.LYViewPagerAdapter;
import com.ch.leyu.application.CLYApplication;
import com.ch.leyu.widget.view.PagerSlidingTabStrip;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.animation.Animation;
import java.util.ArrayList;

/***
 * 首页--新闻资讯
 *
 * @author L
 */
public class NewsFragment extends BaseFragment {

    private PagerSlidingTabStrip mSlideTabIndicator;

    private ViewPager mViewPager;

    private ArrayList<String> mTitleList;

    private ArrayList<Fragment> mFragmentList;


    @Override
    protected void getExtraParams() {
       Bundle bundle = getArguments();
        
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_news);

    }

    @Override
    protected void findViewById() {
        mSlideTabIndicator = (PagerSlidingTabStrip) findViewById(R.id.newsfragment_tab_indicator);
        mViewPager = (ViewPager) findViewById(R.id.newsfragment_viewpager);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        mViewPager.setAdapter(new LYViewPagerAdapter(getChildFragmentManager(), addFragment(),addTitle()));
        mSlideTabIndicator.setViewPager(mViewPager);
        mSlideTabIndicator.setTextSize(24);
    }

    private ArrayList<String> addTitle() {
        mTitleList = new ArrayList<String>();
        mTitleList.add("全部");
        mTitleList.add("炉石传说");
        mTitleList.add("英雄联盟");
        mTitleList.add("其他");

        return mTitleList;
    }

    private ArrayList<Fragment> addFragment() {
        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new AllNewsFragment());
        mFragmentList.add(new HSNewsFragment());
        mFragmentList.add(new LOLNewsFragment());
        mFragmentList.add(new OtherNewsFragment());
        return mFragmentList;

    }
    
    
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if(enter){
            if(((CLYApplication)getActivity().getApplication()).ismFlag()==false){
                mViewPager.setCurrentItem(1);
            }else {
                mViewPager.setCurrentItem(0);
            }
        }
        
        return super.onCreateAnimation(transit, enter, nextAnim);
        
    }
    
}
