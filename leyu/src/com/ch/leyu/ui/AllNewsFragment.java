
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.CLYAdapter;
import com.ch.leyu.adapter.FouceNewsPagerAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.AllNewResponse;
import com.ch.leyu.responseparse.Property;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.view.AutoScrollViewPager;
import com.ch.leyu.widget.view.CircleLoopPageIndicator;
import com.ch.leyu.widget.xlistview.XListView;
import com.ch.leyu.widget.xlistview.XListView.IXListViewListener;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 新闻资讯---全部
 *
 * @author L
 */
@SuppressLint("SimpleDateFormat")
public class AllNewsFragment extends BaseFragment implements OnItemClickListener {

    private XListView mXListView;

    private CLYAdapter mAdapter;

    private SimpleDateFormat mSimpleDateFormat;

    // ListView 要加入的头部View
    private View mListViewHeaderView;

    private AutoScrollViewPager mAutoScrollViewPager;

    private CircleLoopPageIndicator mCircleLoopPageIndicator;

    private int mPage = 1;

    private int mTotalPage;

    private boolean mFlag;

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_all);
        mListViewHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.all_fragment_head, null);
    }

    @Override
    protected void loadfindViewById() {
        mAutoScrollViewPager = (AutoScrollViewPager) mListViewHeaderView.findViewById(R.id.all_auto_scroll_viewpager);
        mCircleLoopPageIndicator = (CircleLoopPageIndicator) mListViewHeaderView.findViewById(R.id.all_cirle_pageindicator);
        mXListView = (XListView) findViewById(R.id.all_listview_cly);
        mAdapter = new CLYAdapter(getActivity(), null);
    }

    @Override
    protected void setListener() {
        mXListView.setOnItemClickListener(this);
    }

    @Override
    protected void processLogic() {
        mXListView.addHeaderView(mListViewHeaderView);
        mXListView.setAdapter(mAdapter);
        mXListView.setPullRefreshEnable(true);
        mXListView.setPullLoadEnable(true);
        mXListView.setXListViewListener(mIXListViewListenerImp);
        requestData(mPage);

    }

    private void requestData(int page) {
        RequestParams mParams = new RequestParams();
        mParams.put(Constant.PAGE, page);
        JHttpClient.get(getActivity(), Constant.URL + Constant.ALL_NEWS, mParams, AllNewResponse.class, new DataCallback<AllNewResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, AllNewResponse data) {
                       if(data!=null){
                           mTotalPage = data.getTotalPage();
                           if (mPage == 1) {
                               mAutoScrollViewPager.startAutoScroll(2000);
                               mAutoScrollViewPager.setInterval(4000);
                               mAutoScrollViewPager.setAdapter(new FouceNewsPagerAdapter(getActivity(), data.getFocus()));
                               mAutoScrollViewPager.setCurrentItem(data.getFocus().size() * 10000);
                               mCircleLoopPageIndicator.setPageCount(data.getFocus().size());
                               mCircleLoopPageIndicator.setViewPager(mAutoScrollViewPager);
                               mXListView.setAutoScrollViewPager(mAutoScrollViewPager);

                               mAdapter.chargeArrayList(data.getNewsList());
                           } else {
                               mAdapter.addArrayList(data.getNewsList());
                           }
                           mPage++;
                           if (mPage > mTotalPage) {
                               mXListView.setPullLoadEnable(false);
                           } else {
                               mXListView.setPullLoadEnable(true);
                           }

                       }
                    }

                    @Override
                    public void onStart() {
                        mHttpErrorView.setVisibility(View.GONE);
                        if(mPage==1&&mFlag==false){
                            mHttpLoadingView.setVisibility(View.VISIBLE);
                        }

                        if (mXListView != null) {
                            onLoad();
                        }
                    }

                    @Override
                    public void onFinish() {
                        mHttpLoadingView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,
                            Exception exception) {
                       if(mPage<=1){
                           mHttpErrorView.setVisibility(View.VISIBLE);
                       }
                    }

                });
    }

    private XListView.IXListViewListener mIXListViewListenerImp = new IXListViewListener() {

        @Override
        public void onRefresh() {
            mFlag = true;
            mPage = 1;
            requestData(mPage);
        }

        @Override
        public void onLoadMore() {
            requestData(mPage);

        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Property item = (Property) parent.getAdapter().getItem(position);
        if(item!=null){
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra(Constant.CID, item.getId());
            startActivity(intent);
        }
    }

    // 加载中时间监听
    private void onLoad() {
        mXListView.stopRefresh();
        mXListView.stopLoadMore();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        mXListView.setRefreshTime(mSimpleDateFormat.format(new Date()));
    }

    @Override
    protected void reload() {
        requestData(mPage);
    }


}
