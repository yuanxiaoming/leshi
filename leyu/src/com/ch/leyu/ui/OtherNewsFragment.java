
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.CLYAdapter;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * 其他新闻资讯
 *
 * @author L
 */
public class OtherNewsFragment extends BaseFragment implements OnItemClickListener {

  private XListView mListView;
    
    private CLYAdapter mAdapter;

    private int mPage = 1;

    private int mTotalPage;

    private SimpleDateFormat mSimpleDateFormat;
    
    private boolean mFlag ;

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_othernews);
    }

    @Override
    protected void findViewById() {
        mListView = (XListView) findViewById(R.id.othernews_listview_cly);
        mAdapter = new CLYAdapter(getActivity(), null);
    }

    @Override
    protected void setListener() {
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setOnItemClickListener(this);
        mListView.setXListViewListener(mIXListViewListenerImp);
        
    }

    @Override
    protected void processLogic() {
        mListView.setAdapter(mAdapter);
        requestData(mPage);
        
    }
    
    private void requestData(int page) {
        RequestParams params = new RequestParams();
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
                      mListView.setPullLoadEnable(false);
                  } else {
                      mListView.setPullLoadEnable(true);
                  }
              }
            }

            @Override
            public void onStart() {
                if(mPage==1&&mFlag==false){
                    mHttpLoadingView.setVisibility(View.VISIBLE);
                }
                if (mListView != null) {
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Property item = (Property) parent.getAdapter().getItem(position);
        if(item!=null){
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra(Constant.CID, item.getId());
            startActivity(intent);
        }
        
    }
    
    /**
     * 上拉加载，下拉刷新
     */
    private XListView.IXListViewListener mIXListViewListenerImp = new IXListViewListener() {
        @Override
        public void onRefresh() {
            mFlag = true ;
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
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        mListView.setRefreshTime(mSimpleDateFormat.format(new Date()));
    }

}
