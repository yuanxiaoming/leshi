
package com.ch.leyu.ui;

import org.apache.http.Header;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.ch.leyu.R;
import com.ch.leyu.adapter.HeadofAllFragmentPagerAdapter;
import com.ch.leyu.adapter.LOLViewPagerAdapter;
import com.ch.leyu.adapter.eventbus.XListViewTouchEventBus;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.HSResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.view.AutoScrollViewPager;
import com.ch.leyu.widget.view.CircleLoopPageIndicator;
import com.ch.leyu.widget.view.LYViewPager;
import com.ch.leyu.widget.view.PagerSlidingTabStrip;
import com.ch.leyu.widget.xlistview.XListView;
import de.greenrobot.event.EventBus;

/***
 * 首页--英雄联盟
 *
 * @author L
 */
public class LOLFragment extends BaseFragment {

    /** 焦点栏viewpager */
    private AutoScrollViewPager mfocusViewPager;

    /** 焦点栏ViewPager指示点 */
    private CircleLoopPageIndicator mPageIndicator;

    private LYViewPager mViewPager;

    private PagerSlidingTabStrip mSlideTabIndicator;

    private LOLViewPagerAdapter mPagerAdapter;

    private View mView;

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_lol);

    }

    @Override
    protected void findViewById() {
        mViewPager = (LYViewPager) findViewById(R.id.fragment_lol_viewpager);
        mSlideTabIndicator = (PagerSlidingTabStrip) findViewById(R.id.fragment_lol_pagertab);
        mView = findViewById(R.id.fragment_lol_include);
        mfocusViewPager = (AutoScrollViewPager) mView.findViewById(R.id.all_auto_scroll_viewpager);
        mPageIndicator = (CircleLoopPageIndicator) mView.findViewById(R.id.all_cirle_pageindicator);

    }

    @Override
    protected void setListener() {
        
    }

    @Override
    protected void processLogic() {
    	//注册EventBus 事件
    	EventBus.getDefault().register(this);
    	
        mPagerAdapter = new LOLViewPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mSlideTabIndicator.setViewPager(mViewPager);
        mSlideTabIndicator.setTextSize(24);
        JHttpClient.get(getActivity(), Constant.URL + Constant.LOL_URL, null, HSResponse.class,
                new DataCallback<HSResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, HSResponse data) {
                        if (data != null) {
                            mfocusViewPager.startAutoScroll(2000);
                            mfocusViewPager.setInterval(4000);
                            mfocusViewPager.setCurrentItem(data.getFocus().size() * 10000);
                            mPageIndicator.setPageCount(data.getFocus().size());
                            mfocusViewPager.setAdapter(new HeadofAllFragmentPagerAdapter(getActivity(), data.getFocus()));
                            mPageIndicator.setViewPager(mfocusViewPager);
                            
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
    
    //onEventMainThread，当使用这种类型时，回调函数会在主线程中执行.
    
	public void onEventMainThread(XListViewTouchEventBus event) {

		int deltaY = (int)( event.getDeltaY() / 2.0);
		XListView listView = event.getListView() ;
		System.out.println("deltaY = " + deltaY);
		
		// 根据触摸的XListView的高度来动态改变AutoScrollerViewPager的高度
		RelativeLayout.LayoutParams params = (LayoutParams) mView.getLayoutParams();
		int newTopMargin = params.topMargin + deltaY;
		
		//表示向下拉  并且当前可见的List item 为1 ListView的header为0
		if(deltaY > 0 )
		{
			//当前可见的Item 为1 或者为 0 才让其下滑
			if(listView.getFirstVisiblePosition() == 1 || listView.getFirstVisiblePosition() == 0)
			{
				if(newTopMargin > 0)
					newTopMargin = 0 ;
			}
			else
			{
				newTopMargin -= deltaY ;
			}
		}
		//deltaY <= 0 表示向上拉 
		else if(deltaY < 0 && newTopMargin < -mView.getHeight())
		{
			//处理topMargin的边界
			newTopMargin = - mView.getHeight() ;
		}
		
		params.topMargin = newTopMargin ;
		mView.setLayoutParams(params);
		getView().invalidate();
		
	}
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	//注销EventBus 事件监听
    	EventBus.getDefault().unregister(this);
    }
    

}
