
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.CommentListAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.CommentDetail;
import com.ch.leyu.responseparse.CommentResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.view.ClearEditText;
import com.ch.leyu.widget.xlistview.XListView;

import org.apache.http.Header;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * 视频播放--评论界面
 *
 * @author L
 */
public class CommentFragment extends BaseFragment {

    /** 用户id.暂时为0 */
    public static final String UID = "uid";

    /** 用户名 */
    public static final String NICKNAME = "nickname";

    /** 类型 2 */
    public static final String TYPE = "type";

    /** 视频id */
    public static final String CID = "cid";

    /** 评论内容 */
    public static final String COMMENT = "comment";

    private XListView mListView;

    private CommentListAdapter mAdapter;

    private String mCid;

    private String nickName="";

    /** ListView 要加入的头部View */
    private View mListViewHeaderView;

    /** 有评论时发表回复 */
    private Button mPublish;

    /** 有评论时回复内容 */
    private ClearEditText mDetail;

    /** 无评论时发表回复 */
    private Button mPublish_EmptyView;

    /** 无评论时回复内容 */
    private ClearEditText mDetail_EmptyView;

    /** 小贴士 */
    private View mTipsLayout;

    @Override
    protected void getExtraParams() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCid = bundle.getString(Constant.GMAE_ID);
            nickName = bundle.getString(Constant.NICKNAME);
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_comment);
        mListViewHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.comment_head,null);

    }

    @Override
    protected void findViewById() {
        mListView = (XListView) findViewById(R.id.fragment_comment_xistview);
        mPublish = (Button) mListViewHeaderView.findViewById(R.id.comment_head_bt_commit);
        mDetail = (ClearEditText) mListViewHeaderView.findViewById(R.id.comment_head_et_detail);
        mPublish_EmptyView = (Button)findViewById(R.id.comment_head_bt_commit);
        mDetail_EmptyView = (ClearEditText)findViewById(R.id.comment_head_et_detail);
        mTipsLayout = findViewById(R.id.comment_emptyview_layout);
        mListView.setEmptyView(mTipsLayout);
        mListView.addHeaderView(mListViewHeaderView);
        mListView.setHeaderDividersEnabled(false);
        mAdapter = new CommentListAdapter(null, getActivity());
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        mPublish.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                hidden();
                if (!TextUtils.isEmpty(mDetail.getText().toString())) {
                    String  comment = mDetail.getText().toString();
                    CommentDetail  commentDetail = new CommentDetail();
                    commentDetail.setUid(mCid);
                    commentDetail.setComment(comment);
                    commentDetail.setCreateTime(System.currentTimeMillis());
                    commentDetail.setNickname(nickName);
                    publishComment(0,commentDetail);
                    mDetail.setText("");
                }else {
                    Toast.makeText(getActivity(), R.string.comment_toast_tips, Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
        mPublish_EmptyView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                hidden();
                if (!TextUtils.isEmpty(mDetail_EmptyView.getText().toString())) {
                    String  comment = mDetail_EmptyView.getText().toString();
                    CommentDetail  commentDetail = new CommentDetail();
                    commentDetail.setUid(mCid);
                    commentDetail.setComment(comment);
                    commentDetail.setCreateTime(System.currentTimeMillis());
                    commentDetail.setNickname(nickName);
                    publishComment(0,commentDetail);
                    mDetail_EmptyView.setText("");
                }else {
                    Toast.makeText(getActivity(), R.string.comment_toast_tips, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    @Override
    protected void processLogic() {
        RequestParams params = new RequestParams();
        params.put("cid", mCid);
        JHttpClient.get(getActivity(), Constant.COMMENT_LIST, params, CommentResponse.class,new DataCallback<CommentResponse>() {

            @Override
            public void onStart() {
                mHttpLoadingView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, CommentResponse data) {
                if (data != null) {
                    mAdapter.addArrayList(data.getComment());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                    Exception exception) {

            }

            @Override
            public void onFinish() {
                mHttpLoadingView.setVisibility(View.GONE);
            }
        });
    }

    /**
    * @param uid 用户id
    * @param nickname 视频名字
    * @param cid 视频id
    * @param comment 评论内容
    * @param index 1代表无评论时，2代表有评论时
    */
    private void publishComment(int uid, final CommentDetail  commentDetail) {
        RequestParams params = new RequestParams();
        params.put(UID, uid);
        params.put(NICKNAME, commentDetail.getNickname());
        params.put(TYPE, 2);
        params.put(CID, mCid);
        params.put(COMMENT, commentDetail.getComment());
        JHttpClient.post(getActivity(), Constant.COMMENT_PUBLISH, params, CommentResponse.class,new DataCallback<CommentResponse>() {

            @Override
            public void onStart() {
                mHttpLoadingView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, CommentResponse data) {
                ArrayList<CommentDetail> mDetailsList = new ArrayList<CommentDetail>();
                mDetailsList.add(commentDetail);
                mAdapter.addArrayList(mDetailsList);
                Toast.makeText(getActivity(), "评论成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Exception exception) {
                Toast.makeText(getActivity(), "评论失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                mHttpLoadingView.setVisibility(View.GONE);
            }

        });
    }

}
