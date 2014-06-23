
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.StarListAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.Info;
import com.ch.leyu.responseparse.StarResponse;
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

/***
 * 首页--明星解说
 * 
 * @author L
 */
public class StarGirefFragment extends BaseFragment {

    private XListView mXListView;

    private StarListAdapter mAdapter;
    
    private SimpleDateFormat mSimpleDateFormat;

    private int mPage = 1;

    private int mTotalPage;

    private boolean mFlag;

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_stargiref);

    }

    @Override
    protected void findViewById() {
        mXListView = (XListView) findViewById(R.id.star_xistview);
        mAdapter = new StarListAdapter(getActivity(), null);

    }

    @Override
    protected void setListener() {
        mXListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Info info = (Info) parent.getAdapter().getItem(position);
                if (info != null) {
                    String uid = info.getUid();
                    Intent intent = new Intent(getActivity(), StarDetailActivity.class);
                    intent.putExtra(Constant.UID, uid);
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
        mXListView.setAdapter(mAdapter);
        requestData(mPage);

    }

    private void requestData(int page) {
        RequestParams mParams = new RequestParams();
        mParams.put(Constant.PAGE, page);
        JHttpClient.get(getActivity(), Constant.URL + Constant.STAR_URL, mParams, StarResponse.class, new DataCallback<StarResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, final StarResponse data) {
                        if (data != null) {
                            mTotalPage = data.getTotalPage();
                            if (mPage == 1) {
                                mAdapter.chargeArrayList(data.getUserInfo());
                            } else {
                                mAdapter.addArrayList(data.getUserInfo());
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
