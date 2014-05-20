
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.GridViewAdapter;
import com.ch.leyu.adapter.VideobankPagerAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.VideoBankResponse;
import com.ch.leyu.utils.Constant;

import org.apache.http.Header;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

/***
 * 炉石传说 视频库
 *
 * @author L
 */
public class VideoBankFragment extends BaseFragment {

    private GridView mGridView;

    private  Bundle mBundle;

    private String mKeyword;

    private GridViewAdapter mGridViewAdapter;

    @Override
    public void onClick(View v) {


    }

    @Override
    protected void getExtraParams() {
        mBundle = getArguments();
        mKeyword = mBundle.getString(VideobankPagerAdapter.POSITION);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_hs_vedio);
    }

    @Override
    protected void findViewById() {
        mGridView = (GridView) findViewById(R.id.hsvedio_gridview);
        mGridViewAdapter=new GridViewAdapter(null, getActivity());
        mGridView.setAdapter(mGridViewAdapter);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        RequestParams params = new RequestParams();
        params.put(Constant.GMAE_ID, 23);
        params.put(Constant.KEYWORD, mKeyword);
        JHttpClient.get(getActivity(), Constant.LOL_VEDIO_URL, params,VideoBankResponse.class, new DataCallback<VideoBankResponse>() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final VideoBankResponse data) {
                if(data!=null){
                    mGridViewAdapter.chargeArrayList(data.getVideoList());
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                    Exception exception) {

            }


        });

    }
}