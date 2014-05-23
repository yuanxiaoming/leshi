
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.GridViewAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.StarDetailResponse;
import com.ch.leyu.utils.Constant;

import org.apache.http.Header;

import android.view.View;
import android.widget.GridView;

/**
 * 明星视频--最热播放
 *
 * @author L
 */
public class HotsVideoFragment extends BaseFragment {

    private GridView mGridView;

    private String uid;

    private GridViewAdapter mAdapter;


    @Override
    protected void getExtraParams() {
        uid = ((StarDetailActivity) getActivity()).getUid();
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_newvideo);
    }

    @Override
    protected void findViewById() {
        mGridView = (GridView) findViewById(R.id.newviedo_fragment_gridview);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        RequestParams params = new RequestParams();
        params.put(Constant.UID, uid);
        // 如果是最多播放添加如下参数
        params.put(Constant.SORT, "click");
        JHttpClient.get(getActivity(), Constant.URL + Constant.STAR_DETAIL, params,
                StarDetailResponse.class, new DataCallback<StarDetailResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, StarDetailResponse data) {
                        if(data!=null){
                            mAdapter = new GridViewAdapter(data.getVideoList().getData(), getActivity());
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
