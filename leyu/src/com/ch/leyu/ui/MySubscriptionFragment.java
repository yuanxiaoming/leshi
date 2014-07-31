package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.SubscriptionListAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.Info;
import com.ch.leyu.responseparse.SubscriptionResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.xlistview.XListView;
import com.ch.leyu.widget.xlistview.XListView.IXListViewListener;

import org.apache.http.Header;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MySubscriptionFragment extends BaseFragment {
    
    private XListView mXListView;
    
    private String mLoginUid = "";
    
    private String mAuth = "";
    
    private String mPassStr = "";
    
    private SubscriptionListAdapter mSubscriptionListAdapter;
    
    private SimpleDateFormat mSimpleDateFormat;

    private int mPage = 1;

    private int mTotalPage;

    private boolean mFlag;
    
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
        setContentView(R.layout.fragment_my_subscription);
    }

    @Override
    protected void loadfindViewById() {
        mXListView = (XListView) findViewById(R.id.fragment_subscription_xlistview);
    }

    @Override
    protected void setListener() {
        mXListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Info info = (Info) parent.getAdapter().getItem(position);
                if (info != null) {
                    String uid = info.getUid();
                    String name = info.getNickname();
                    Intent intent = new Intent(getActivity(), StarDetailActivity.class);
                    intent.putExtra(Constant.UID, uid);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void processLogic() {
        mXListView.setPullRefreshEnable(true);
        mXListView.setPullLoadEnable(true);
        mXListView.setXListViewListener(mIXListViewListenerImp);
        mSubscriptionListAdapter = new SubscriptionListAdapter(getActivity(), null,mLoginUid,mAuth,mPassStr);
        mXListView.setAdapter(mSubscriptionListAdapter);
        requestData(mPage);
    }

    @Override
    protected void reload() {
        requestData(mPage);
    }
    
    private void requestData(int page){
        RequestParams params = new RequestParams();
        params.put("action", "mySubscribe");
        params.put(Constant.AUTH,mAuth);
        params.put(Constant.LOGIN_UID,mLoginUid);
        params.put(Constant.PASS_STR,mPassStr);
        params.put(Constant.PAGE,page);
        Log.d("tag", JHttpClient.getUrlWithQueryString(Constant._URL, params));
        JHttpClient.getFromServer(getActivity(), Constant._URL, params, SubscriptionResponse.class, new DataCallback<SubscriptionResponse>() {

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
            public void onSuccess(int statusCode, Header[] headers, SubscriptionResponse data) {
                if(data!=null){
                    mTotalPage = data.getTotalPage();
                    if (mPage == 1) {
                        mSubscriptionListAdapter.chargeArrayList(data.getUserInfo());
                    } else {
                        mSubscriptionListAdapter.addArrayList(data.getUserInfo());
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
            public void onFailure(int statusCode, Header[] headers, String responseString,
                    Exception exception) {
                if(mPage<=1){
                    mHttpErrorView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFinish() {
                mHttpLoadingView.setVisibility(View.GONE);
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

    // 加载中时间监听
    private void onLoad() {
        mXListView.stopRefresh();
        mXListView.stopLoadMore();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        mXListView.setRefreshTime(mSimpleDateFormat.format(new Date()));
    }


}
