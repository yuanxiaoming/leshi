
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.LatestSearchAdapter;
import com.ch.leyu.adapter.SearchAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.provider.LatestSearch;
import com.ch.leyu.provider.LatestSearchManager;
import com.ch.leyu.responseparse.SearchResponse;
import com.ch.leyu.responseparse.VideoSearchResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.view.LYGridView;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/***
 * 搜索activity
 *
 * @author L
 */
public class SearchActivity extends BaseActivity {

    /** 输入内容 */
    private EditText mDetail;

    /** 热门搜索，历史记录 */
    private GridView mHots;

    /** 历史记录 */
    private LYGridView mHistory;

    /** 没有搜索结果 */
    private TextView mResult;

    /** 搜索 */
    private Button mSearch;

    private String mKeyWord = "";

    private Context mContext;

    private LatestSearchAdapter mLatestSearchAdapter;

    private SearchAdapter mSearchAdapter;

    private ArrayList<LatestSearch> mLatestSearchArrayList;

    private RelativeLayout mLayout;

    /** 删除历史搜索内容 */
    private RelativeLayout mDel;

    @Override
    protected void getExtraParams() {
        mContext = this;

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void findViewById() {
        mDel = (RelativeLayout) findViewById(R.id.search_del_include);
        mLayout = (RelativeLayout) findViewById(R.id.search_include);
        mHistory = (LYGridView) findViewById(R.id.act_search_gd_history);
        mHots = (GridView) findViewById(R.id.act_search_gd_hots);
        mDetail = (EditText) mLayout.findViewById(R.id.search_head_et_detail);
        mResult = (TextView) findViewById(R.id.act_search_tv_result);
        mSearch = (Button) mLayout.findViewById(R.id.search_head_bt_commit);
        mLatestSearchArrayList = LatestSearchManager.findLatestSearchAll();
        mLatestSearchAdapter = new LatestSearchAdapter(mLatestSearchArrayList);
        mHistory.setAdapter(mLatestSearchAdapter);
        if (mLatestSearchArrayList != null && mLatestSearchArrayList.size() != 0) {
            mHistory.setVisibility(View.VISIBLE);
            mResult.setVisibility(View.GONE);
            mDel.setVisibility(View.VISIBLE);
        } else {
            // 提示没有最近搜索记录
            mHistory.setVisibility(View.GONE);
            mResult.setVisibility(View.VISIBLE);
            mDel.setVisibility(View.GONE);
        }

    }

    @Override
    protected void setListener() {
        mDel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mLatestSearchAdapter != null) {
                    mLatestSearchAdapter.chargeArrayList(null);
                    LatestSearchManager.deleteSearch();
                    mHistory.setVisibility(View.GONE);
                    mResult.setVisibility(View.VISIBLE);
                    mDel.setVisibility(View.GONE);
                }

            }
        });

        mSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hidden();
                if (!TextUtils.isEmpty(mDetail.getText().toString())) {
                    mKeyWord = mDetail.getText().toString();
                    RequestParams params = new RequestParams();
                    params.put(Constant.KEYWORD, mKeyWord);
                    JHttpClient.get(mContext, Constant.URL + Constant.SEARCH, params,VideoSearchResponse.class, mSearchDataCallback);
                } else {
                    Toast.makeText(mContext, R.string.search_hint, Toast.LENGTH_LONG).show();
                }
            }
        });

        mHots.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mSearchAdapter.getArrayList()!=null&&mSearchAdapter.getArrayList().get(position)!=null&&!TextUtils.isEmpty(mSearchAdapter.getArrayList().get(position).getTitle())){
                    mKeyWord= mSearchAdapter.getArrayList().get(position).getTitle();
                    RequestParams params = new RequestParams();
                    params.put(Constant.KEYWORD, mKeyWord);
                    JHttpClient.get(mContext, Constant.URL + Constant.SEARCH, params,VideoSearchResponse.class, mSearchDataCallback);
                }else {
                    Toast.makeText(mContext, R.string.search_hint, Toast.LENGTH_LONG).show();
                }


            }

        });
        mHistory.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LatestSearchAdapter latestSearchAdapter = (LatestSearchAdapter) parent.getAdapter();
                if (latestSearchAdapter != null) {
                    LatestSearch latestSearch = (LatestSearch) (latestSearchAdapter.getArrayList().get(position));
                    Intent intent = new Intent(mContext, SearchListActivity.class);
                    intent.putExtra("result", latestSearch.getmVideoSearchResponse());
                    intent.putExtra(Constant.KEYWORD, latestSearch.getKeyword());
                    startActivity(intent);
                }

            }

        });
    }

    @Override
    protected void processLogic() {
       requestData();
    }

    private void requestData() {
        JHttpClient.get(mContext, Constant.URL + Constant.HOT_SEARCH, null, SearchResponse.class,mHotSearchDataCallback);
    }

    /**
     * 关键字搜索
     */
    DataCallback<VideoSearchResponse> mSearchDataCallback = new DataCallback<VideoSearchResponse>() {

        @Override
        public void onStart() {

            showProgressDialog();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, VideoSearchResponse data) {

            if (data != null) {
                LatestSearch latestSearch = new LatestSearch(mKeyWord, data);
                LatestSearchManager.insertOrUpdateSearch(latestSearch);
                if (mLatestSearchAdapter != null) {
                    if (mHistory.getVisibility() != View.VISIBLE) {
                        mDel.setVisibility(View.VISIBLE);
                        mHistory.setVisibility(View.VISIBLE);
                        mResult.setVisibility(View.GONE);
                    }
                    mLatestSearchAdapter.chargeArrayList(LatestSearchManager.findLatestSearchAll());
                }
                mDetail.setText(null);
                Intent intent = new Intent(mContext, SearchListActivity.class);
                intent.putExtra("result", data);
                intent.putExtra(Constant.KEYWORD, mKeyWord);
                startActivity(intent);

            } else {
                if(mLatestSearchArrayList == null || mLatestSearchArrayList.size() <= 0){
                    mDel.setVisibility(View.GONE);
                }
                mHistory.setVisibility(View.GONE);
                mResult.setVisibility(View.VISIBLE);
                mResult.setText(R.string.search_hint_null);
                Toast.makeText(SearchActivity.this, R.string.search_hint_null, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString,Exception exception) {
        }

        @Override
        public void onFinish() {
            closeProgressDialog();
        }

    };

    /**
     * 热门搜索
     */
    DataCallback<SearchResponse> mHotSearchDataCallback = new DataCallback<SearchResponse>() {

        @Override
        public void onStart() {
            mHttpErrorView.setVisibility(View.GONE);
            mHttpLoadingView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, SearchResponse data) {
            if (data != null) {
                mSearchAdapter = new SearchAdapter(mContext, data.getHot());
                mHots.setAdapter(mSearchAdapter);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString,Exception exception) {
            mHttpErrorView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFinish() {
            mHttpLoadingView.setVisibility(View.GONE);
        }

    };

    @Override
    protected void reload() {
    	requestData();
    }


    @Override
    protected void onStart() {
        super.onStart();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("视频搜索");
        actionBar.setLogo(R.drawable.legames_back);
        actionBar.setHomeButtonEnabled(true);
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
                return true;

            default:
                break;
        }

        return true;
    }


}
