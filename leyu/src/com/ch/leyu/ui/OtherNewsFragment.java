
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.CLYAdapter;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.AllNewResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.xlistview.XListView;

import org.apache.http.Header;

import android.view.View;

/***
 * 其他新闻资讯
 *
 * @author L
 */
public class OtherNewsFragment extends BaseFragment {

    private XListView mListView;

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

    }

    @Override
    protected void setListener() {
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        JHttpClient.get(getActivity(), Constant.URL+Constant.ALL_NEWS+Constant.RESTS_NEWS, null, AllNewResponse.class,new DataCallback<AllNewResponse>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, AllNewResponse data) {
              if(data!=null){
                  mListView.setAdapter(new CLYAdapter(getActivity(), data.getNewsList()));
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

    @Override
    protected void processLogic() {

    }

}
