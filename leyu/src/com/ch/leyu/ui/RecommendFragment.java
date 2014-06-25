
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

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 视频播放界面--相关推荐
 * 
 * @author L
 */
public class RecommendFragment extends BaseFragment implements OnItemClickListener {
   
    private XListView mXListView;

    private VideoDetailResponse mVideoDetailResponse = null;
    
    private RecommendListAdapter mListAdapter;

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
        mXListView.setAdapter(mListAdapter);
        if(mVideoDetailResponse!=null){
            String keyWord = "["+mVideoDetailResponse.getTag()+","+mVideoDetailResponse.getGame()+"]";
            String url = Constant.URL+Constant.SEARCH;
            requestData(keyWord, url);
        }
       
    }

    
    private void requestData(String keyWord,String url){
        
        RequestParams params = new RequestParams();
        params.put(Constant.KEYWORD, keyWord);
        JHttpClient.get(getActivity(), url, params , VideoSearchResponse.class, callBack);
    }
    
    
    
    private DataCallback<VideoSearchResponse> callBack = new DataCallback<VideoSearchResponse>() {

        @Override
        public void onStart() {
            
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, VideoSearchResponse data) {
            if(data!=null){
                mListAdapter.chargeArrayList(data.getVideoList());
            }
            
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString,Exception exception) {
            
        }

        @Override
        public void onFinish() {
            
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
        // TODO Auto-generated method stub
        
    }
    
    
    
    
}
