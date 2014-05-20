
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.GridViewAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.VideoBankResponse;
import com.ch.leyu.utils.Constant;

import org.apache.http.Header;

import android.view.View;
import android.widget.GridView;

public class LOLVedioFragment extends BaseFragment {

    private GridView mGridView;

    private GridViewAdapter mAdapter;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void getExtraParams() {

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
        RequestParams mParams = new RequestParams();
        mParams.put("gameId", 21);
        mParams.put("page", 1);

        JHttpClient.get(getActivity(), Constant.URL + Constant.LOL_VEDIO_URL, mParams,
                VideoBankResponse.class, new DataCallback<VideoBankResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, VideoBankResponse data) {
                        mAdapter = new GridViewAdapter(data.getVideoList(), getActivity());
                        mGridView.setAdapter(mAdapter);

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
