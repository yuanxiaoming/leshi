package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.ListAsGridBaseAdapter.GridItemClickListener;
import com.ch.leyu.adapter.ListChangeGridAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.VideoBankResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.xlistview.XListView;
import com.ch.leyu.widget.xlistview.XListView.IXListViewListener;
import com.ch.leyu.widget.xlistview.XXListView;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * LOL视频库
 *
 * @author L
 */
public class LOLVideoFragment extends BaseFragment implements GridItemClickListener {

    private Bundle mBundle;

    private int position;

    private XXListView mXListView;

    private ListChangeGridAdapter mAdapter;

    private int mPage = 1;

    /** 总页数 */
    private int mTotalPage;

    private boolean mFlag = false;

    private SimpleDateFormat mSimpleDateFormat;

    private String url = Constant.URL + Constant.VIDEO_URL;

    @Override
    protected void getExtraParams() {
        mBundle = getArguments();
        if (mBundle != null) {
            position = mBundle.getInt(Constant.POSITION);
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_lol_video);
    }

    @Override
    protected void loadfindViewById() {
        mXListView = (XXListView) findViewById(R.id.lolvideo_xlistview);
        mXListView.setViewPager(LOLFragment.mfocusViewPager);
    }

    @Override
    protected void setListener() {
        mAdapter.setOnGridClickListener(this);
        mXListView.setXListViewListener(mIXListViewListenerImp);
    }

    @Override
    protected void processLogic() {
        mAdapter = new ListChangeGridAdapter(null, getActivity());
        mAdapter.setNumColumns(2);
        mXListView.setPullLoadEnable(true);
        mXListView.setPullRefreshEnable(false);
        mXListView.setAdapter(mAdapter);

        // 全部视频
        if (position == 0) {
            requestData("21", null, url, mPage);
        }
        // 本周热门
        if (position == 1) {

            requestData("21", null, Constant.LOL_HOT, mPage);
        }
        // 教学
        if (position == 2) {
            String keyWord = "精彩,教学,原创";
            requestData("21", keyWord, url, mPage);
        }
        // 解说
        if (position == 3) {
            String keyWord = "解说,搞笑,排位";
            requestData("21", keyWord, url, mPage);
        }

    }

    private void requestData(String gameId, String keyWord, String url, int page) {
        RequestParams mParams = new RequestParams();
        mParams.put(Constant.GMAE_ID, gameId);
        mParams.put(Constant.KEYWORD, keyWord);
        mParams.put(Constant.PAGE, page);
        JHttpClient.get(getActivity(), url, mParams, VideoBankResponse.class,new DataCallback<VideoBankResponse>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, VideoBankResponse data) {
                if (data != null) {
                    mTotalPage = data.getTotalPage();
                    if (mPage == 1) {
                        mAdapter.chargeArrayList(data.getVideoList());
                    } else {
                        mAdapter.addArrayList(data.getVideoList());
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
                if (mPage == 1 && mFlag == false) {
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

    @Override
    public void onGridItemClicked(View v, int position, long itemId) {

        if(mAdapter!=null&&mAdapter.getArrayList()!=null&&mAdapter.getArrayList().size()>0){
            Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
            String videoId = mAdapter.getArrayList().get(position).getId();
            intent.putExtra(Constant.CID, videoId);
            startActivity(intent);
        }

    }

    private XListView.IXListViewListener mIXListViewListenerImp = new IXListViewListener() {
        // 下拉刷新
        @Override
        public void onRefresh() {
            mPage = 1;
            mFlag = true;
            // 全部视频
            if (position == 0) {
                requestData("21", null, url, mPage);
            }
            // 本周热门
            if (position == 1) {

                requestData("21", null, Constant.LOL_HOT, mPage);
            }
            // 教学
            if (position == 2) {
                String keyWord = "精彩,教学,原创";
                requestData("21", keyWord, url, mPage);
            }
            // 解说
            if (position == 3) {
                String keyWord = "解说,搞笑,排位";
                requestData("21", keyWord, url, mPage);
            }
        }

        // 上拉加载
        @Override
        public void onLoadMore() {
            // 全部视频
            if (position == 0) {
                requestData("21", null, url, mPage);
            }
            // 本周热门
            if (position == 1) {

                requestData("21", null, Constant.LOL_HOT, mPage);
            }
            // 教学
            if (position == 2) {
                String keyWord = "精彩,教学,原创";
                requestData("21", keyWord, url, mPage);
            }
            // 解说
            if (position == 3) {
                String keyWord = "解说,搞笑,排位";
                requestData("21", keyWord, url, mPage);
            }
        }

    };

    // 加载中时间监听
    private void onLoad() {
        mXListView.stopRefresh();
        mXListView.stopLoadMore();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        mXListView.setRefreshTime(mSimpleDateFormat.format(new Date()));
    }

    @Override
    protected void reload() {
        // 全部视频
        if (position == 0) {
            requestData("21", null, url, mPage);
        }
        // 本周热门
        if (position == 1) {

            requestData("21", null, Constant.LOL_HOT, mPage);
        }
        // 教学
        if (position == 2) {
            String keyWord = "精彩,教学,原创";
            requestData("21", keyWord, url, mPage);
        }
        // 解说
        if (position == 3) {
            String keyWord = "解说,搞笑,排位";
            requestData("21", keyWord, url, mPage);
        }
    }

}
