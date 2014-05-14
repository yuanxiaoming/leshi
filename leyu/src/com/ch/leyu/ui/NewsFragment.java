
package com.ch.leyu.ui;

import android.content.res.ColorStateList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ch.leyu.R;
import com.ch.leyu.view.PagerSlidingTabStrip;
import com.ch.leyu.view.TabPageIndicator;

/***
 * 首页--新闻资讯
 * @author L
 *
 */
public class NewsFragment extends BaseFragment {

    private PagerSlidingTabStrip mSlideTabIndicator ;
    private ViewPager mViewPager ;
    private String [] mNewsTitle = new String[]{"全部","英雄联盟","其他"};

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void getExtraParams() {

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
    	mViewPager.setAdapter(new NewsAdapter(getChildFragmentManager()));
    	mSlideTabIndicator.setViewPager(mViewPager);
    	mSlideTabIndicator.setTextSize(24);
    }

    private final class NewsAdapter extends FragmentStatePagerAdapter{

		public NewsAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int positionn) {
			return new AllFragment();
		}

		@Override
		public int getCount() {
			return mNewsTitle.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mNewsTitle[position];
		}
    }
}