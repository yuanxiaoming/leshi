
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.GridViewAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.VideoSearchResponse;
import com.ch.leyu.utils.Constant;

import org.apache.http.Header;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

/***
 * 搜索结果列表显示
 *
 * 暂未处理没有结果时的显示情况
 *
 * @author L
 */
public class SearchListActivity extends BaseActivity implements OnClickListener {

    /** 最新 */
    private Button mNewst;

    /** 最热 */
    private Button mHottest;

    private GridView mGridView;

    /** 搜索结果 */
    private TextView mResult;

    /** 搜索内容 */
    private EditText mEditText;

    private Button mSearch;

    private VideoSearchResponse mResponse;

    private String keyWord;

    @Override
    public void onClick(View v) {
        RequestParams params = null;

        if (v.getId() == R.id.act_searchlist_bt_search) {
            keyWord = mEditText.getText().toString();
            params = new RequestParams();
            params.put(Constant.KEYWORD, keyWord);
        }
        if (v.getId() == R.id.act_searchlist_bt_hots) {
            params = new RequestParams();
            params.put(Constant.SORT, "click");
            params.put(Constant.KEYWORD, keyWord);
        }
        if (v.getId() == R.id.act_searchlist_bt_latest) {
            params = new RequestParams();
            params.put(Constant.KEYWORD, keyWord);
        }
        JHttpClient.get(this, Constant.URL + Constant.SEARCH, params, VideoSearchResponse.class,
                new DataCallback<VideoSearchResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, VideoSearchResponse data) {
                        mGridView.setAdapter(new GridViewAdapter(data.getVideoList(),
                                SearchListActivity.this));
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
    protected void getExtraParams() {
        Intent intent = getIntent();
        if (intent != null) {
            mResponse = (VideoSearchResponse) intent.getSerializableExtra("result");
            keyWord = intent.getStringExtra(Constant.KEYWORD);
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_searchlist);
    }

    @Override
    protected void findViewById() {
        mNewst = (Button) findViewById(R.id.act_searchlist_bt_latest);
        mHottest = (Button) findViewById(R.id.act_searchlist_bt_hots);
        mGridView = (GridView) findViewById(R.id.act_searchlist_gd);
        mResult = (TextView) findViewById(R.id.act_searchlist_tv_count);
        mEditText = (EditText) findViewById(R.id.act_searchlist_et_detail);
        mSearch = (Button) findViewById(R.id.act_searchlist_bt_search);
    }

    @Override
    protected void setListener() {
        mSearch.setOnClickListener(this);
        mNewst.setOnClickListener(this);
        mHottest.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {
        mGridView.setAdapter(new GridViewAdapter(mResponse.getVideoList(), this));
    }

}
