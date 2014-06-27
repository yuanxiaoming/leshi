
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

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/***
 * 炉石传说 视频库
 *
 * @author L
 */
public class VideoBankFragment extends BaseFragment implements GridItemClickListener {

    private XListView mXlistView;

    private Bundle mBundle;

    private String mKeyword;

    private ListChangeGridAdapter mAdapter;

    private VideoBankResponse mBankResponse;

    private int mPage = 1;

    /** 总页数 */
    private int mTotalPage;

    private SimpleDateFormat mSimpleDateFormat;
    
    private boolean mFlag = false;

    @Override
    protected void getExtraParams() {
        mBundle = getArguments();
        if (mBundle != null) {
            mKeyword = mBundle.getString(Constant.POSITION);
        }

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_hs_vedio);
    }

    @Override
    protected void findViewById() {
        mXlistView = (XListView) findViewById(R.id.hsvideo_xlistview);
        mAdapter = new ListChangeGridAdapter(null, getActivity());

    }

    @Override
    protected void setListener() {
        mAdapter.setOnGridClickListener(this);
        mXlistView.setXListViewListener(mIXListViewListenerImp);
    }

    @Override
    protected void processLogic() {
        mAdapter.setNumColumns(2);
        mXlistView.setPullLoadEnable(true);
        mXlistView.setPullRefreshEnable(true);
        mXlistView.setAdapter(mAdapter);
        requestData("23", mKeyword, mPage);

    }

    public void requestData(String gameId, String keyword, int page) {
        RequestParams params = new RequestParams();
        params.put(Constant.GMAE_ID, gameId+"");
        params.put(Constant.KEYWORD, keyword);
        params.put(Constant.PAGE, page);
        JHttpClient.get(getActivity(), Constant.URL + Constant.VIDEO_URL, params,VideoBankResponse.class, new DataCallback<VideoBankResponse>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, VideoBankResponse data) {
                if (data != null) {
                    mBankResponse = data;
                    mTotalPage = data.getTotalPage();
                    if (mPage == 1) {
                        mAdapter.chargeArrayList(data.getVideoList());
                    } else {
                        mAdapter.addArrayList(data.getVideoList());
                    }
                    
                    mPage++;
                    if (mPage > mTotalPage) {
                        mXlistView.setPullLoadEnable(false);
                    } else {
                        mXlistView.setPullLoadEnable(true);
                    }
                  
                }

            }

            @Override
            public void onStart() {
                mHttpErrorView.setVisibility(View.GONE);
                if(mPage==1&&mFlag==false){
                    mHttpLoadingView.setVisibility(View.VISIBLE);
                    
                }
                if (mXlistView != null) {
                    onLoad();
                }
            }

            @Override
            public void onFinish() {
                mHttpLoadingView.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Exception exception) {
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
            mFlag = true ;
            mPage = 1;
            requestData("23", mKeyword, mPage);
        }

        // 上拉加载
        @Override
        public void onLoadMore() {
            requestData("23", mKeyword, mPage);

        }
    };

    // 加载中时间监听
    private void onLoad() {
        mXlistView.stopRefresh();
        mXlistView.stopLoadMore();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",Locale.CHINA);
        mXlistView.setRefreshTime(mSimpleDateFormat.format(new Date()));
    }

    @Override
    protected void reload() {
       requestData("23",  mKeyword, mPage);
        
    }
}
