
package com.ch.leyu.ui;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.animation.Animation;

import com.ch.leyu.R;
import com.ch.leyu.adapter.LYViewPagerAdapter;
import com.ch.leyu.application.CLYApplication;
import com.ch.leyu.widget.view.PagerSlidingTabStrip;

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

	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.fragment_news);

	}

	@Override
	protected void loadfindViewById() {
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
		final int textSize = (int) getActivity().getResources().getDimension(R.dimen.tab_title_size);
		mSlideTabIndicator.setTextSize(textSize);
	}

	private ArrayList<String> addTitle() {
		mTitleList = new ArrayList<String>();
		mTitleList.add("全部");
		mTitleList.add("职业攻略");
		mTitleList.add("竞技场");
		mTitleList.add("天梯");

		return mTitleList;
	}

	private ArrayList<Fragment> addFragment() {
		mFragmentList = new ArrayList<Fragment>();
		mFragmentList.add(new AllNewsFragment());
		mFragmentList.add(new HSNewsFragment());
		mFragmentList.add(new EventNewsFragment());
		mFragmentList.add(new TiantiNewsFragment());
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

	@Override
	protected void reload() {

	}

}
