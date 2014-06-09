
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * 视频播放--评论界面
 * 
 * @author L
 */
public class CommentFragment extends BaseFragment implements OnClickListener {

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

    private String mId;

    private String nickName = "";

    private String comment = "";

    /** ListView 要加入的头部View */
    private View mListViewHeaderView;

    /** 有评论时发表回复 */
    private Button mPublish;

    /** 有评论时回复内容 */
    private ClearEditText mDetail;

    /** 小贴士 */
    private RelativeLayout mTipsLayout;

    /** 无评论时发表回复 */
    private Button mPublish_null;

    /** 无评论时回复内容 */
    private ClearEditText mDetail_null;

    private CommentDetail mCommentDetail;

    private ArrayList<CommentDetail> mDetailsList;

    @Override
    protected void getExtraParams() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mId = bundle.getString(Constant.GMAE_ID);
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
        mTipsLayout = (RelativeLayout) findViewById(R.id.fragment_comment_tips);
        mDetail_null = (ClearEditText)findViewById(R.id.comment_et_detail);
        mPublish_null = (Button)findViewById(R.id.comment_bt_commit);
        mAdapter = new CommentListAdapter(null, getActivity());
    }

    @Override
    protected void setListener() {
        mPublish.setOnClickListener(this);
        mPublish_null.setOnClickListener(this);
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
                            if(mDetailsList!=null){
                                mDetailsList.clear();
                            }
                            mDetailsList = data.getComment();
                            mTipsLayout.setVisibility(View.GONE);
                            mListView.setVisibility(View.VISIBLE);
                            mAdapter.addArrayList(mDetailsList);
                        } else {
                            mListView.setVisibility(View.GONE);
                            mTipsLayout.setVisibility(View.VISIBLE);
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
    private void publishComment(int uid, String nickname, String cid, String comment,final int index) {
        RequestParams params = new RequestParams();
        params.put(UID, uid);
        params.put(NICKNAME, nickname);
        params.put(TYPE, 2);
        params.put(CID, cid);
        params.put(COMMENT, comment);
        JHttpClient.post(getActivity(), Constant.COMMENT_PUBLISH, params, CommentResponse.class,
                new DataCallback<CommentResponse>() {

                    @Override
                    public void onStart() {
                        mHttpLoadingView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, CommentResponse data) {
                        if(index == 1)
                        {
                            mDetail_null.setText("");
                            mDetailsList = new ArrayList<CommentDetail>();
                            mDetailsList.add(mCommentDetail);
                            mAdapter.chargeArrayList(mDetailsList);
                            mTipsLayout.setVisibility(View.GONE);
                            mListView.setVisibility(View.VISIBLE);
                        }
                        if(index == 2)
                        {
                            mDetail.setText("");
                            mDetailsList.add(mCommentDetail);
                            mAdapter.chargeArrayList(mDetailsList);
                            mTipsLayout.setVisibility(View.GONE);
                            mListView.setVisibility(View.VISIBLE);
                        }

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

    @Override
    public void onClick(View v) {
        hidden();
        switch (v.getId()) {
            case R.id.comment_bt_commit:

                if (!mDetail_null.getText().toString().equals("")) {
                    comment = mDetail_null.getText().toString();
                } else {
                    Toast.makeText(getActivity(), R.string.comment_toast_tips, Toast.LENGTH_SHORT).show();
                    return;
                }
                mCommentDetail = new CommentDetail();
                mCommentDetail.setUid(mId);
                mCommentDetail.setComment(comment);
                mCommentDetail.setCreateTime(System.currentTimeMillis());
                mCommentDetail.setNickname(nickName);
                publishComment(0, nickName, mId, comment,1);
                break;
                
            case R.id.comment_head_bt_commit:

                if (!mDetail.getText().toString().equals("")) {
                    comment = mDetail.getText().toString();
                } else {
                    Toast.makeText(getActivity(), R.string.comment_toast_tips, Toast.LENGTH_SHORT).show();
                    return;
                }
                mCommentDetail = new CommentDetail();
                mCommentDetail.setUid(mId);
                mCommentDetail.setComment(comment);
                mCommentDetail.setCreateTime(System.currentTimeMillis());
                mCommentDetail.setNickname(nickName);
                publishComment(0, nickName, mId, comment,2);
                break;

            default:
                break;
        }

    }
}
