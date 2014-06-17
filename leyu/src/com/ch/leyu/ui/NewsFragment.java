
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.LYViewPagerAdapter;
import com.ch.leyu.application.CLYApplication;
import com.ch.leyu.widget.view.PagerSlidingTabStrip;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;

import java.io.FileDescriptor;
import java.io.PrintWriter;
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
    	super.setUserVisibleHint(isVisibleToUser);
    	System.out.println("setUserVisibleHint");
    }

	@Override
	public void dump(String prefix, FileDescriptor fd, PrintWriter writer,
			String[] args) {
		System.out.println("dump");
		super.dump(prefix, fd, writer, args);
	}

	@Override
	public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
		System.out.println("getLayoutInflater");
		return super.getLayoutInflater(savedInstanceState);
	}

	@Override
	public LoaderManager getLoaderManager() {
		System.out.println("getLoaderManager");
		return super.getLoaderManager();
	}

	@Override
	public boolean getUserVisibleHint() {
		System.out.println("getUserVisibleHint");
		return super.getUserVisibleHint();
	}

	@Override
	public View getView() {
		System.out.println("getView");
		return super.getView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		System.out.println("onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onAttach(Activity activity) {
		System.out.println("onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		System.out.println("onConfigurationChanged");
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		System.out.println("onContextItemSelected");
		return super.onContextItemSelected(item);
	}

	@Override
	public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
		System.out.println("onCreateAnimation"+"transit="+transit+"--enter"+enter+"--nextAnim"+nextAnim);
		if(enter)
		{
			//你的业务逻辑
		}
		
		return super.onCreateAnimation(transit, enter, nextAnim);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		System.out.println("onCreateContextMenu");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		System.out.println("onCreateOptionsMenu");
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onDestroy() {
		System.out.println("onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDestroyOptionsMenu() {
		
		super.onDestroyOptionsMenu();
		System.out.println("onDestroyOptionsMenu");
	}

	@Override
	public void onDestroyView() {
		System.out.println("onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		System.out.println("onDetach");
		super.onDetach();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		System.out.println("onHiddenChanged"+hidden);
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onInflate(Activity activity, AttributeSet attrs,
			Bundle savedInstanceState) {
		System.out.println("onInflate");
		super.onInflate(activity, attrs, savedInstanceState);
	}

	@Override
	public void onLowMemory() {
		System.out.println("onLowMemory");
		super.onLowMemory();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println("onOptionsItemSelected");
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		System.out.println("onOptionsMenuClosed");
		super.onOptionsMenuClosed(menu);
	}

	@Override
	public void onPause() {
		System.out.println("onPause");
		super.onPause();
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		System.out.println("onPrepareOptionsMenu");
		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onResume() {
		System.out.println("onResume");
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		System.out.println("onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onStart() {
		System.out.println("onStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		System.out.println("onStop");
		super.onStop();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		System.out.println("onViewCreated");
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		System.out.println("dumonViewStateRestoredp");
		super.onViewStateRestored(savedInstanceState);
	}

	@Override
	public void registerForContextMenu(View view) {
		System.out.println("registerForContextMenu");
		super.registerForContextMenu(view);
	}

	@Override
	public void setArguments(Bundle args) {
		System.out.println("setArguments");
		super.setArguments(args);
	}

	@Override
	public void setHasOptionsMenu(boolean hasMenu) {
		System.out.println("setHasOptionsMenu");
		super.setHasOptionsMenu(hasMenu);
	}

	@Override
	public void setInitialSavedState(SavedState state) {
		System.out.println("setInitialSavedState");
		super.setInitialSavedState(state);
	}

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		System.out.println("setMenuVisibility");
		super.setMenuVisibility(menuVisible);
	}

	@Override
	public void setRetainInstance(boolean retain) {
		System.out.println("setRetainInstance");
		super.setRetainInstance(retain);
	}

	@Override
	public void setTargetFragment(Fragment fragment, int requestCode) {
		System.out.println("setTargetFragment");
		super.setTargetFragment(fragment, requestCode);
	}

	@Override
	public void startActivity(Intent intent) {
		System.out.println("startActivity");
		super.startActivity(intent);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		System.out.println("startActivityForResult");
		super.startActivityForResult(intent, requestCode);
	}

	@Override
	public String toString() {
		System.out.println("toString");
		return super.toString();
	}

	@Override
	public void unregisterForContextMenu(View view) {
		System.out.println("unregisterForContextMenu");
		super.unregisterForContextMenu(view);
	}
    
    
    
    
    
}
