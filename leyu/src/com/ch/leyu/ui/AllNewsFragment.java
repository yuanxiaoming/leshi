
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.CLYAdapter;
import com.ch.leyu.adapter.HeadofAllFragmentPagerAdapter;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.AllNewResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.view.AutoScrollViewPager;
import com.ch.leyu.view.CircleLoopPageIndicator;
import com.ch.leyu.widget.xlistview.XListView;
import com.ch.leyu.widget.xlistview.XListView.IXListViewListener;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 新闻资讯---全部
 * 
 * @author L
 */
@SuppressLint("SimpleDateFormat")
public class AllNewsFragment extends BaseFragment {

    private XListView mXListView;

    private CLYAdapter mAdapter;

    private SimpleDateFormat mSimpleDateFormat;

    // ListView 要加入的头部View
    private View mListViewHeaderView;

    private AutoScrollViewPager mAutoScrollViewPager;

    private CircleLoopPageIndicator mCircleLoopPageIndicator;

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

        mXListView.addHeaderView(mListViewHeaderView);
        mXListView.setPullRefreshEnable(true);
        mXListView.setPullLoadEnable(true);
        mXListView.setXListViewListener(mIXListViewListenerImp);
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        mXListView.setRefreshTime(mSimpleDateFormat.format(new Date()));

        loadData();

    }

    private void loadData() {
        JHttpClient.get(getActivity(), Constant.URL + Constant.ALL_NEWS, null,
                AllNewResponse.class, new DataCallback<AllNewResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, AllNewResponse data) {
                       if(data!=null){
                           mAutoScrollViewPager.startAutoScroll(2000);
                           mAutoScrollViewPager.setCurrentItem(data.getFocus().size() * 10000);
                           mCircleLoopPageIndicator.setPageCount(data.getFocus().size());
                           mAutoScrollViewPager.setAdapter(new HeadofAllFragmentPagerAdapter(getActivity(), data.getFocus()));
                           mCircleLoopPageIndicator.setViewPager(mAutoScrollViewPager);

                           mAdapter = new CLYAdapter(getActivity(), data.getNewsList());
                           mXListView.setAdapter(mAdapter);
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
