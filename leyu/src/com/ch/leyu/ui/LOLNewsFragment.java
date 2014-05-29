
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.CLYAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.AllNewResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.xlistview.XListView;

import org.apache.http.Header;

import android.view.View;

/***
 * LOL新闻资讯
 *
 * @author L
 */
public class LOLNewsFragment extends BaseFragment {

    private XListView mListView;
    
    private CLYAdapter mAdapter;


    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_lolnews);
    }

    @Override
    protected void findViewById() {
        mListView = (XListView) findViewById(R.id.lolnews_listview_cly);
        mAdapter = new CLYAdapter(getActivity(), null);
    }

    @Override
    protected void setListener() {
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        
    }

    @Override
    protected void processLogic() {
        
        mListView.setAdapter(mAdapter);
        RequestParams params = new RequestParams();
        params.put(Constant.GMAE_ID, 21);
        JHttpClient.get(getActivity(), Constant.URL+Constant.ALL_NEWS+Constant.RESTS_NEWS, params, AllNewResponse.class,new DataCallback<AllNewResponse>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, AllNewResponse data) {
               if(data!=null){
                   mAdapter.addArrayList(data.getNewsList());
               }
            }

            @Override
            public void onStart() {
                mHttpLoadingView.setVisibility(View.VISIBLE);
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

}
