
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.HSNewsGridViewAdapter;
import com.ch.leyu.adapter.HeadofAllFragmentPagerAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.AllNewResponse;
import com.ch.leyu.responseparse.Property;
import com.ch.leyu.utils.CommonUtil;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.view.AutoScrollViewPager;
import com.ch.leyu.widget.view.CircleLoopPageIndicator;

import org.apache.http.Header;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/***
 * 新闻资讯--炉石攻略
 *
 * @author Administrator
 */
public class HSNewsFragment extends BaseFragment implements OnItemClickListener {

    private GridView mGridView ;

    private AutoScrollViewPager mViewPager ;

    private CircleLoopPageIndicator mPageIndicator;

    private View layout;

    private HSNewsGridViewAdapter maAdapter ;


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
        mGridView.setOnItemClickListener(this);
    }

    @Override
    protected void processLogic() {
        RequestParams params = new RequestParams();
        params.put(Constant.GMAE_ID, "23");
        JHttpClient.get(getActivity(), Constant.URL+Constant.ALL_NEWS, params, AllNewResponse.class, new DataCallback<AllNewResponse>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, AllNewResponse data) {
               if(data!=null){
                   maAdapter = new HSNewsGridViewAdapter(data.getCareer(), getActivity());
                   mGridView.setAdapter(maAdapter);
                   mViewPager.startAutoScroll(2000);
                   mViewPager.setInterval(4000);
                   mViewPager.setCurrentItem(data.getFocus().size() * 10000);
                   mPageIndicator.setPageCount(data.getFocus().size());
                   mViewPager.setAdapter(new HeadofAllFragmentPagerAdapter(getActivity(), data.getFocus()));
                   mPageIndicator.setViewPager(mViewPager);
                  
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
            public void onFailure(int statusCode, Header[] headers, String responseString, Exception exception) {

            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           Property item = (Property) parent.getAdapter().getItem(position);
           if(item!=null){
               Bundle bundle = new Bundle();
               HSNewsDetailFragment detailFragment = new HSNewsDetailFragment();
               bundle.putString(Constant.CID, item.getCid());
               detailFragment.setArguments(bundle);
           }
    }

}
