
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

/**
 * LOL视频库
 *
 * @author L
 */
public class LOLVideoFragment extends BaseFragment implements GridItemClickListener {

//    private GridView mGridView;
//
//    private GridViewAdapter mAdapter;

    private Bundle mBundle;

    private int position;

    private XListView mXListView;
    
    private ListChangeGridAdapter mAdapter;
    
    private   VideoBankResponse mResponse;

    @Override
    protected void getExtraParams() {
        mBundle = getArguments();
        if (mBundle != null) {
            position = mBundle.getInt(Constant.POSITION);
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_lol_video);
    }

    @Override
    protected void findViewById() {
//        mGridView = (GridView) findViewById(R.id.lolvideo_gridview);
        mXListView = (XListView) findViewById(R.id.lolvideo_xlistview);
    }

    @Override
    protected void setListener() {
//        mGridView.setOnItemClickListener(this);
        mAdapter.setOnGridClickListener(this);
    }

    @Override
    protected void processLogic() {
//        mAdapter = new GridViewAdapter(null, getActivity());
        mAdapter = new ListChangeGridAdapter(null, getActivity());
//        mGridView.setAdapter(mAdapter);
        mAdapter.setNumColumns(2);
        mXListView.setAdapter(mAdapter);
        
        
        String url = Constant.URL + Constant.VIDEO_URL;
        //全部视频
        if (position == 0) {
            requestData(21, null, url);
        }
        //本周热门
        if (position == 1) {

            requestData(21, null, Constant.LOL_HOT);
        }
        //教学
        if (position == 2) {
            String keyWord = "精彩,教学,原创";
            requestData(21, keyWord, url);
        }
        //解说
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
                            mResponse = data;
//                            mAdapter.chargeArrayList(data.getVideoList());
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

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Property item = (Property) parent.getAdapter().getItem(position);
//        if(item!=null){
//            Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
//            String videoId = item.getId();
//            intent.putExtra(Constant.UID, videoId);
//            startActivity(intent);
//        }
//     
//
//    }

    @Override
    public void onGridItemClicked(View v, int position, long itemId) {
        if(mResponse!=null){
            Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
            String videoId = mResponse.getVideoList().get(position).getId();
            intent.putExtra(Constant.UID, videoId);
            startActivity(intent);
        }
        
    }

}
