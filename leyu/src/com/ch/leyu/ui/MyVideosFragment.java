
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.ListAsGridBaseAdapter.GridItemClickListener;
import com.ch.leyu.adapter.ListChangeGridAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.StarDetailResponse;
import com.ch.leyu.responseparse.VideoListResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.xlistview.XListView;
import com.ch.leyu.widget.xlistview.XListView.IXListViewListener;

import org.apache.http.Header;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 个人中心-我的视频
 * @author Administrator
 *
 */
public class MyVideosFragment extends BaseFragment implements GridItemClickListener {

    private XListView mXListView;

    private ListChangeGridAdapter mAdapter;

    private VideoListResponse mResponse;

    private int mPage = 1;

    private int mTotalPage;

    private SimpleDateFormat mSimpleDateFormat;

    private boolean mFlag = false;

    private String mLoginUid = "";

    private String mAuth = "";

    private String mPassStr = "";

    @Override
    protected void getExtraParams() {
        if( ((MyZoneActivity) getActivity()).getmResponse()!=null){
            mLoginUid =  ((MyZoneActivity) getActivity()).getmResponse().getUserInfo().getLoginUid();
            mAuth =  ((MyZoneActivity) getActivity()).getmResponse().getUserInfo().getAuth();
            mPassStr =  ((MyZoneActivity) getActivity()).getmResponse().getUserInfo().getPassStr();
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_newvideo);
    }

    @Override
    protected void loadfindViewById() {
        mXListView = (XListView) findViewById(R.id.starviedo_fragment_xlistview);
        mAdapter = new ListChangeGridAdapter(null, getActivity());
    }

    @Override
    protected void setListener() {
        mAdapter.setOnGridClickListener(this);
        mXListView.setXListViewListener(mIXListViewListenerImp);
    }

    @Override
    protected void processLogic() {
        mAdapter.setNumColumns(2);
        mXListView.setPullLoadEnable(true);
        mXListView.setPullRefreshEnable(true);
        mXListView.setAdapter(mAdapter);
        requestData(mPage);
    }

    
    private void requestData(int page) {
        RequestParams params = new RequestParams();
        params.put(Constant.LOGIN_UID, mLoginUid);
        params.put(Constant.AUTH, mAuth);
        params.put(Constant.PASS_STR, mPassStr);
        params.put(Constant.PAGE, page);
        JHttpClient.get(getActivity(), Constant.MY_VIDEOS, params,StarDetailResponse.class, new DataCallback<StarDetailResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, StarDetailResponse data) {
                        if (data != null) {
                            mResponse = data.getVideoList();
                            mTotalPage = data.getVideoList().getTotalPage();
                            if (mPage == 1) {
                                mAdapter.chargeArrayList(data.getVideoList().getData());
                            } else {
                                mAdapter.addArrayList(data.getVideoList().getData());
                            }
                            
                            mPage++;
                            if (mPage > mTotalPage) {
                                mXListView.setPullLoadEnable(false);
                            } else {
                                mXListView.setPullLoadEnable(true);
                            }
                        }else {
                            Toast.makeText(getActivity(), "没有上传视频", Toast.LENGTH_LONG).show();
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
        // 下拉刷新
        @Override
        public void onRefresh() {
            mFlag = true ;
            mPage = 1;
            requestData(mPage);
        }

        // 上拉加载
        @Override
        public void onLoadMore() {
            requestData(mPage);

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
    public void onGridItemClicked(View v, int position, long itemId) {
        if (mResponse != null) {
            String cId = mResponse.getData().get(position).getId();
            Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
            intent.putExtra(Constant.CID, cId);
            startActivity(intent);
        }

    }

    @Override
    protected void reload() {
        requestData(mPage);
    }


}
