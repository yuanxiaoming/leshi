
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.LYViewPagerAdapter;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

public class E extends BaseFragment {

    private ViewPager mViewPager;

    private PagerTabStrip mTabStrip;

    /** viewpager标头 */
    private ArrayList<String> titleList;

    /***/
    private ArrayList<Fragment> fragmentList;

    @Override
    public void onClick(View v) {

    }

    @SuppressLint("InlinedApi")
    @Override
    protected void findViewById() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabStrip = (PagerTabStrip) findViewById(R.id.pagertab);
        // 设置下划线的颜色
        mTabStrip.setTabIndicatorColor(getResources().getColor(android.R.color.holo_green_dark));
        // 设置背景的颜色
        mTabStrip.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        mViewPager.setAdapter(new LYViewPagerAdapter(getChildFragmentManager(), addFragment(),
                addTitle()));
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_e);

    }

    @Override
    protected void processLogic() {
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void getExtraParams() {

    }

    private ArrayList<String> addTitle() {
        titleList = new ArrayList<String>();
        titleList.add("第一页");
        titleList.add("第二页");
        titleList.add("第三页");

        return titleList;
    }

    private ArrayList<Fragment> addFragment() {
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new B());
        fragmentList.add(new B());
        fragmentList.add(new B());

        return fragmentList;
    }

}
