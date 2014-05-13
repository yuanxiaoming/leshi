
package com.ch.leyu.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;

import android.view.LayoutInflater;
import android.view.View;

import com.ch.leyu.R;
import com.ch.leyu.adapter.CLYAdapter;
import com.ch.leyu.adapter.HeadofAllFragmentPagerAdapter;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.RegisterResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.view.AutoScrollViewPager;
import com.ch.leyu.view.CircleLoopPageIndicator;
import com.ch.leyu.widget.xlistview.XListView;
import com.ch.leyu.widget.xlistview.XListView.IXListViewListener;

/**
 * 新闻资讯---全部Fragment
 *
 * @author CAIJIA
 */
public class AllFragment extends BaseFragment {

    private XListView mXListView;
    private CLYAdapter mAdapter;
    private SimpleDateFormat mSimpleDateFormat;

    //ListView 要加入的头部View
    private View mListViewHeaderView ;

    private AutoScrollViewPager mAutoScrollViewPager ;
    private CircleLoopPageIndicator mCircleLoopPageIndicator ;

	private int mDrawable[] = new int[] { R.drawable.biz_plugin_weather_beijin,
			R.drawable.biz_plugin_weather_guangzhou,
			R.drawable.biz_plugin_weather_shanghai };

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_all);
        mListViewHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.all_fragment_head, null);
    }

    @Override
    protected void findViewById() {
    	mAutoScrollViewPager = (AutoScrollViewPager) mListViewHeaderView.findViewById(R.id.all_auto_scroll_viewpager);
    	mCircleLoopPageIndicator = (CircleLoopPageIndicator) mListViewHeaderView.findViewById(R.id.all_cirle_pageindicator);
        mXListView = (XListView) findViewById(R.id.all_listview_cly);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {

    	mAutoScrollViewPager.startAutoScroll(2000);
    	HeadofAllFragmentPagerAdapter adapter = new HeadofAllFragmentPagerAdapter(getActivity() , mDrawable);
    	mAutoScrollViewPager.setAdapter(adapter);
    	mAutoScrollViewPager.setCurrentItem(mDrawable.length * 10000);
    	mCircleLoopPageIndicator.setPageCount(mDrawable.length);
    	mCircleLoopPageIndicator.setViewPager(mAutoScrollViewPager);

    	mXListView.addHeaderView(mListViewHeaderView);
        mXListView.setPullRefreshEnable(true);
        mXListView.setPullLoadEnable(true);
        mXListView.setXListViewListener(mIXListViewListenerImp);
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        mXListView.setRefreshTime(mSimpleDateFormat.format(new Date()));

    	loadData();

    }

	private void loadData() {
		JHttpClient.get(getActivity(), Constant.A_URL, null, RegisterResponse.class,
                new DataCallback<RegisterResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, RegisterResponse data) {
                        mAdapter = new CLYAdapter(getActivity(), data.getList());
                        mXListView.setAdapter(mAdapter);
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

    private XListView.IXListViewListener mIXListViewListenerImp = new IXListViewListener() {
        // 下拉刷新
        @Override
        public void onRefresh() {

        }

        // 上拉加载
        @Override
        public void onLoadMore() {

        }
    };
}
