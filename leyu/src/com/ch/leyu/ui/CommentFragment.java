
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.CommentListAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.CommentResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.view.ClearEditText;
import com.ch.leyu.widget.xlistview.XListView;

import org.apache.http.Header;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * 视频播放--评论界面
 *
 * @author L
 */
public class CommentFragment extends BaseFragment {

    private XListView mListView;

    private CommentListAdapter mAdapter;

    private String mId;

    // ListView 要加入的头部View
    private View mListViewHeaderView;

    /** 发表回复 */
    private Button mPublish;

    /** 回复内容 */
    private ClearEditText mDetail;

    /** 小贴士 */
    private RelativeLayout mTips;

    @Override
    protected void getExtraParams() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mId = bundle.getString(Constant.GMAE_ID);
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_comment);
        mListViewHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.comment_head,
                null);
    }

    @Override
    protected void findViewById() {
        mListView = (XListView) findViewById(R.id.fragment_comment_xistview);
        mPublish = (Button) mListViewHeaderView.findViewById(R.id.comment_head_bt_commit);
        mDetail = (ClearEditText) mListViewHeaderView.findViewById(R.id.comment_head_et_detail);
        mTips = (RelativeLayout) findViewById(R.id.fragment_comment_tips);
        mAdapter = new CommentListAdapter(null, getActivity());
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        mListView.setAdapter(mAdapter);
        mListView.addHeaderView(mListViewHeaderView);
        RequestParams params = new RequestParams();
        params.put("cid", mId);
        JHttpClient.get(getActivity(), Constant.COMMENT_LIST, params, CommentResponse.class,new DataCallback<CommentResponse>() {

                    @Override
                    public void onStart() {
                        mHttpLoadingView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, CommentResponse data) {
                        if (data != null) {
                            mListView.setVisibility(View.VISIBLE);
                            mAdapter.addArrayList(data.getComment());

                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,
                            Exception exception) {
                        mTips.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        mHttpLoadingView.setVisibility(View.GONE);
                    }
                });
    }

}
