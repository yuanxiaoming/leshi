
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

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


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
    }

    @Override
    protected void processLogic() {
        mAdapter.setNumColumns(2);
        mXlistView.setAdapter(mAdapter);
        doDataRefresh(23, mKeyword);

    }

    public void doDataRefresh(int gameId, String keyword) {
        RequestParams params = new RequestParams();
        params.put(Constant.GMAE_ID, gameId);
        params.put(Constant.KEYWORD, keyword);
        JHttpClient.get(getActivity(), Constant.URL+Constant.VIDEO_URL, params, VideoBankResponse.class,new DataCallback<VideoBankResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, VideoBankResponse data) {
                        if (data != null) {
                            mBankResponse = data;
                            mAdapter.chargeArrayList(data.getVideoList());
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
    public void onGridItemClicked(View v, int position, long itemId) {
        if(mBankResponse!=null){
            String uId = mBankResponse.getVideoList().get(position).getId();
            Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                intent.putExtra(Constant.CID , uId);
                startActivity(intent);
        }
        
    }
}
