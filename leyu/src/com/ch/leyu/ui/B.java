
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.CLYAdapter;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.RegisterResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.xlistview.XListView;
import com.ch.leyu.widget.xlistview.XListView.IXListViewListener;

import org.apache.http.Header;

import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

public class B extends BaseFragment {
    private XListView mListView;

    private CLYAdapter adapter;

    private SimpleDateFormat mSimpleDateFormat;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void findViewById() {
        // TODO Auto-generated method stub
        mListView = (XListView) findViewById(R.id.listview_b_cly);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(mIXListViewListenerImp);
        mListView.setAdapter(adapter);
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        mListView.setRefreshTime(mSimpleDateFormat.format(new Date()));
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_b);

    }

    @Override
    protected void processLogic() {
        JHttpClient.get(getActivity(), Constant.A_URL, null, RegisterResponse.class,new DataCallback<RegisterResponse>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, RegisterResponse data) {
                adapter = new CLYAdapter(getActivity(), data.getList());
                mListView.setAdapter(adapter);
                System.out.println("onSuccess");
            }

            @Override
            public void onStart() {
                System.out.println("onStart");
                mHttpLoadingView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFinish() {
                mHttpLoadingView.setVisibility(View.GONE);
                System.out.println("onFinish");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                            Exception exception) {

                System.out.println("onFailure");
            }

        });
    }

    @Override
    protected void setListener() {

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

    @Override
    protected void getExtraParams() {
        // TODO Auto-generated method stub

    }

}
