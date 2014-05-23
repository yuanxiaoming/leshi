
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.VideoDetailResponse;
import com.ch.leyu.responseparse.VideoPlayResponse;
import com.ch.leyu.utils.Constant;

import org.apache.http.Header;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * 视频播放界面--详情
 *
 * @author Administrator
 */
public class DetailFragment extends BaseFragment {

    private TextView mTitle;

    private TextView mName;

    private TextView mCount;

    private TextView mLabel;

    private TextView mIntro;

    private String mId ;

    @Override
    protected void getExtraParams() {
        Bundle arguments = getArguments();
        if(arguments!=null){
            mId = arguments.getString(Constant.UID);
            Log.d("tag", "mId::--"+mId);
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_detail);
    }

    @Override
    protected void findViewById() {
        mTitle = (TextView) findViewById(R.id.fragment_detail_title);
        mName = (TextView) findViewById(R.id.fragment_detail_name);
        mCount = (TextView) findViewById(R.id.fragment_detail_count);
        mLabel = (TextView) findViewById(R.id.fragment_detail_game);
        mIntro = (TextView) findViewById(R.id.fragment_detail_content);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        requestData(mId, Constant.URL+Constant.VIDEO_URL+Constant.VIDEO_DETAIL);


    }

    private void requestData(String mid,String url){
        RequestParams params = new RequestParams();
        params.put("id",mid);
        JHttpClient.get(getActivity(), url, params, VideoPlayResponse.class, new DataCallback<VideoPlayResponse>() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, VideoPlayResponse data) {
                if(data!=null){
                    VideoDetailResponse videoDetailResponse = data.getVideoInfo();
                    if(data.getVideoInfo()!=null){
                        mTitle.setText(videoDetailResponse.getTitle());
                        mName.setText(videoDetailResponse.getNickname());
                        mCount.setText(videoDetailResponse.getClick());
                        mLabel.setText(videoDetailResponse.getTag());
                        mIntro.setText(videoDetailResponse.getDetail());
                    }
                    Log.d("tag", data.getVideoInfo().getTitle()+"-----------");

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                    Exception exception) {

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
