
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.HSNewsGridViewAdapter;
import com.ch.leyu.adapter.HeadofAllFragmentPagerAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.AllNewResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.view.AutoScrollViewPager;
import com.ch.leyu.view.CircleLoopPageIndicator;

import org.apache.http.Header;

import android.view.View;
import android.widget.GridView;

/***
 * 新闻资讯--炉石攻略
 * 
 * @author Administrator
 */
public class HSNewsFragment extends BaseFragment {
    
    private GridView mGridView ;
    
    private AutoScrollViewPager mViewPager ;
    
    private CircleLoopPageIndicator mPageIndicator;
    
    private View layout;
    
    private HSNewsGridViewAdapter maAdapter ;
    
    @Override
    public void onClick(View v) {

    }

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_hsnews);
    }

    @Override
    protected void findViewById() {
        mGridView = (GridView) findViewById(R.id.fragment_hsnews_grid);
        layout = findViewById(R.id.fragment_hsnews_include);
        mViewPager = (AutoScrollViewPager)layout. findViewById(R.id.all_auto_scroll_viewpager);
        mPageIndicator = (CircleLoopPageIndicator)layout. findViewById(R.id.all_cirle_pageindicator);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        RequestParams params = new RequestParams();
        params.put(Constant.GMAE_ID, "23");
        JHttpClient.get(getActivity(), Constant.URL+Constant.ALL_NEWS, params, AllNewResponse.class, new DataCallback<AllNewResponse>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, AllNewResponse data) {
                mViewPager.startAutoScroll(2000);
                mViewPager.setCurrentItem(data.getFocus().size() * 10000);
                mPageIndicator.setPageCount(data.getFocus().size());
                mViewPager.setAdapter(new HeadofAllFragmentPagerAdapter(getActivity(), data.getFocus()));
                mPageIndicator.setViewPager(mViewPager);
                
//                maAdapter = new HSNewsGridViewAdapter(data.getNewsList(), getActivity());
//                mGridView.setAdapter(maAdapter);
                
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

}