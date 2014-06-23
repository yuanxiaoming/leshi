
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.HSNewsDetailListAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.AllNewResponse;
import com.ch.leyu.responseparse.Property;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.xlistview.XListView;
import com.ch.leyu.widget.xlistview.XListView.IXListViewListener;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * 炉石传说新闻内页
 * 
 * @author Administrator
 */
public class HSNewsDetailFragment extends BaseFragment implements OnItemClickListener {
    private XListView mXListView;

    private HSNewsDetailListAdapter mAdapter;

    private int mPage = 1;

    private int mTotalPage;

    private SimpleDateFormat mSimpleDateFormat;
    
    private String mCid ;
    
    public static final String GAMEID="23";

    @Override
    protected void getExtraParams() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            mCid = bundle.getString(Constant.CID);
            Log.d("tag", mCid+"----");
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_hsnews_detail);
    }

    @Override
    protected void findViewById() {
        mXListView = (XListView) findViewById(R.id.hsnews_detail_listview_cly);
        mAdapter = new HSNewsDetailListAdapter(getActivity(), null);
    }

    @Override
    protected void setListener() {
        mXListView.setPullRefreshEnable(true);
        mXListView.setPullLoadEnable(true);
        mXListView.setOnItemClickListener(this);
        mXListView.setXListViewListener(mIXListViewListenerImp);
    }

    @Override
    protected void processLogic() {
        mXListView.setAdapter(mAdapter);
        requestData(mPage);
    }
    
    private void requestData(int page) {
        RequestParams params = new RequestParams();
        params.put(Constant.GMAE_ID, GAMEID);
        params.put(Constant.CID, mCid);
        params.put(Constant.PAGE, page);
        JHttpClient.get(getActivity(), Constant.URL+Constant.ALL_NEWS+Constant.RESTS_NEWS, params, AllNewResponse.class,new DataCallback<AllNewResponse>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, AllNewResponse data) {
              if(data!=null){
                  mTotalPage = data.getTotalPage();
                  if (mPage == 1) {
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
              
              Log.d("tag", "mTotalPage:+"+mTotalPage);
            }

            @Override
            public void onStart() {
                if(mPage==1){
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
            public void onFailure(int statusCode, Header[] headers, String responseString,Exception exception) {
           
            }
        });
        
    }
    
    
    /**
     * 上拉加载，下拉刷新
     */
    private XListView.IXListViewListener mIXListViewListenerImp = new IXListViewListener() {
        @Override
        public void onRefresh() {
            mPage = 1;
            requestData(mPage);
        }

        @Override
        public void onLoadMore() {
            requestData(mPage);

        }
    };

    /**
     * 时间监听
     */
    @SuppressLint("SimpleDateFormat")
    private void onLoad() {
        mXListView.stopRefresh();
        mXListView.stopLoadMore();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        mXListView.setRefreshTime(mSimpleDateFormat.format(new Date()));
    }
    

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Property item = (Property) parent.getAdapter().getItem(position);
        if(item!=null){
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra(Constant.CID, item.getId());
            startActivity(intent);
        }
        
    }

}
