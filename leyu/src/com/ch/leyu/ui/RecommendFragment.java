
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.RecommendListAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.Property;
import com.ch.leyu.responseparse.VideoDetailResponse;
import com.ch.leyu.responseparse.VideoSearchResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.xlistview.XListView;
import com.ch.leyu.widget.xlistview.XListView.IXListViewListener;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 视频播放界面--相关推荐
 * 
 * @author L
 */
public class RecommendFragment extends BaseFragment implements OnItemClickListener {
   
    private XListView mXListView;

    private VideoDetailResponse mVideoDetailResponse = null;
    
    private RecommendListAdapter mListAdapter;
    
    private SimpleDateFormat mSimpleDateFormat;
    
    private String mKeyWord;
    
    private int mPage = 1;

    private int mTotalPage;

    private boolean mFlag;

    @Override
    protected void getExtraParams() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            mVideoDetailResponse = (VideoDetailResponse) bundle.getSerializable(Constant.DATA);
        }

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_recommend);
    }

    @Override
    protected void findViewById() {
        mXListView = (XListView) findViewById(R.id.fragment_recommend_xistview);
        mListAdapter=new RecommendListAdapter(null, getActivity());
    }

    @Override
    protected void setListener() {
        mXListView.setOnItemClickListener(this);
    }

    @Override
    protected void processLogic() {
        mXListView.setPullRefreshEnable(true);
        mXListView.setPullLoadEnable(true);
        mXListView.setXListViewListener(mIXListViewListenerImp);
        mXListView.setAdapter(mListAdapter);
        if(mVideoDetailResponse!=null){
            mKeyWord = "["+mVideoDetailResponse.getTag()+","+mVideoDetailResponse.getGame()+"]";
            requestData(mKeyWord,mPage);
        }
       
    }

    
    private void requestData(String keyWord,int page){
        String url = Constant.URL+Constant.SEARCH;
        RequestParams params = new RequestParams();
        params.put(Constant.KEYWORD, keyWord);
        params.put(Constant.PAGE, page);
        JHttpClient.get(getActivity(), url, params , VideoSearchResponse.class, callBack);
    }
    
    
    
    private DataCallback<VideoSearchResponse> callBack = new DataCallback<VideoSearchResponse>() {

        @Override
        public void onStart() {
            if(mPage==1&&mFlag==false){
                mHttpLoadingView.setVisibility(View.VISIBLE);
            }
           
            if (mXListView != null) {
                onLoad();
            }
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, VideoSearchResponse data) {
            if(data!=null){
                mTotalPage = data.getTotalPage();
                if (mPage == 1) {
                    mListAdapter.chargeArrayList(data.getVideoList());
                } else {
                    mListAdapter.addArrayList(data.getVideoList());
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
        public void onFailure(int statusCode, Header[] headers, String responseString,Exception exception) {
            
        }

        @Override
        public void onFinish() {
            mHttpLoadingView.setVisibility(View.GONE);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      Property item = (Property) parent.getAdapter().getItem(position);
      if(item!=null){
          Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
          String videoId = item.getId();
          intent.putExtra(Constant.CID, videoId);
          startActivity(intent);
          getActivity().finish();
      }
        
    }

    @Override
    protected void reload() {
        
    }
    
    private XListView.IXListViewListener mIXListViewListenerImp = new IXListViewListener() {
        
        @Override
        public void onRefresh() {
            mFlag = true;
            mPage = 1;
            requestData(mKeyWord,mPage);
        }

        @Override
        public void onLoadMore() {
            requestData(mKeyWord,mPage);

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
