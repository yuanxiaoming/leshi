
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.GridViewAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.Property;
import com.ch.leyu.responseparse.VideoBankResponse;
import com.ch.leyu.utils.Constant;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/***
 * 炉石传说 视频库
 *
 * @author L
 */
public class VideoBankFragment extends BaseFragment implements OnItemClickListener {

    private GridView mGridView;

    private Bundle mBundle;

    private String mKeyword;

    private GridViewAdapter mGridViewAdapter;


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
        mGridView = (GridView) findViewById(R.id.hsvedio_gridview);
        mGridViewAdapter = new GridViewAdapter(null, getActivity());
        mGridView.setAdapter(mGridViewAdapter);

    }

    @Override
    protected void setListener() {
        mGridView.setOnItemClickListener(this);
    }

    @Override
    protected void processLogic() {
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
                            mGridViewAdapter.chargeArrayList(data.getVideoList());
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Property item = (Property) parent.getAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
        if(item!=null){
            intent.putExtra(Constant.UID , item.getId());
            startActivity(intent);
        }
        
    }
}
