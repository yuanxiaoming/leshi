
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.CommentListAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.CommentResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.xlistview.XListView;

import org.apache.http.Header;

import android.os.Bundle;
import android.view.View;

/**
 * 视频播放--评论界面
 * 
 * @author L
 */
public class CommentFragment extends BaseFragment {

    private XListView mListView;
    
    private CommentListAdapter mAdapter;
    
    private String mId ;
    
    @Override
    protected void getExtraParams() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            mId = bundle.getString(Constant.GMAE_ID);
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_comment);
    }

    @Override
    protected void findViewById() {
        mListView = (XListView) findViewById(R.id.fragment_comment_xistview);
        mAdapter = new CommentListAdapter(null, getActivity());
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        mListView.setAdapter(mAdapter);
        String url = Constant.URL+Constant.COMMENT_LIST;
        RequestParams params = new RequestParams();
        params.put("cid", mId);
        
        JHttpClient.get(getActivity(), url, params , CommentResponse.class, new DataCallback<CommentResponse>() {

            @Override
            public void onStart() {
                mHttpLoadingView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, CommentResponse data) {
               if(data!=null){
                   mAdapter.addArrayList(data.getComment());
               }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                    Exception exception) {
                
            }

            @Override
            public void onFinish() {
                mHttpLoadingView.setVisibility(View.GONE);
            }
        });
    }

}
