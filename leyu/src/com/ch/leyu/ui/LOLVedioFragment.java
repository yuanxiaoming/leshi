
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.GridViewAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.VideoBankResponse;
import com.ch.leyu.utils.Constant;

import org.apache.http.Header;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

/**
 * LOL视频库
 * 
 * @author L
 */
public class LOLVedioFragment extends BaseFragment {

    private GridView mGridView;

    private GridViewAdapter mAdapter;

    private Bundle mBundle;

    private int position;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void getExtraParams() {
        mBundle = getArguments();
        if (mBundle != null) {
            position = mBundle.getInt(Constant.POSITION);
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_lol_vedio);
    }

    @Override
    protected void findViewById() {
        mGridView = (GridView) findViewById(R.id.lolvedio_gridview);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        String url = Constant.URL + Constant.VEDIO_URL;
        if (position == 0) {
            requestData(21, null, url);
        }
        if (position == 1) {

            requestData(21, null, Constant.LOL_HOT);
        }
        if (position == 2) {
            String keyWord = "精彩,教学,原创";
            requestData(21, keyWord, url);
        }

        if (position == 3) {
            String keyWord = "解说,搞笑,排位";
            requestData(21, keyWord, url);
        }

    }

    private void requestData(int gameId, String keyWord, String url) {
        RequestParams mParams = new RequestParams();
        mParams.put(Constant.GMAE_ID, gameId);
        mParams.put(Constant.KEYWORD, keyWord);
        JHttpClient.get(getActivity(), url, mParams, VideoBankResponse.class,new DataCallback<VideoBankResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, VideoBankResponse data) {
                        if (data != null) {
                            mAdapter = new GridViewAdapter(data.getVideoList(), getActivity());
                            mGridView.setAdapter(mAdapter);

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
