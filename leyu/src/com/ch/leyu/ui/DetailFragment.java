
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.responseparse.VideoDetailResponse;
import com.ch.leyu.utils.Constant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
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
    
    private VideoDetailResponse mVideoDetailResponse = null;

    @Override
    protected void getExtraParams() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            mVideoDetailResponse = (VideoDetailResponse) bundle.getSerializable(Constant.DATA);
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_detail);
    }

    @Override
    protected void loadfindViewById() {
        mTitle = (TextView) findViewById(R.id.fragment_detail_title);
        mName = (TextView) findViewById(R.id.fragment_detail_name);
        mCount = (TextView) findViewById(R.id.fragment_detail_count);
        mLabel = (TextView) findViewById(R.id.fragment_detail_game);
        mIntro = (TextView) findViewById(R.id.fragment_detail_content);
    }

    @Override
    protected void setListener() {
        mName.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StarDetailActivity.class);
                intent.putExtra(Constant.UID, mVideoDetailResponse.getUid());
                intent.putExtra("name", mVideoDetailResponse.getNickname());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void processLogic() {

        if (mVideoDetailResponse != null) {
            mTitle.setText(mVideoDetailResponse.getTitle());
            mName.setText(mVideoDetailResponse.getNickname());
            mCount.setText(mVideoDetailResponse.getClick());
            mLabel.setText(mVideoDetailResponse.getTag());
            mIntro.setText(mVideoDetailResponse.getDetail());
        }

    }

    @Override
    protected void reload() {
        
    }

}
