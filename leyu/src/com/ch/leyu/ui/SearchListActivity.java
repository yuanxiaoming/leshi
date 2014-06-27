package com.ch.leyu.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ch.leyu.R;
import com.ch.leyu.adapter.ListAsGridBaseAdapter.GridItemClickListener;
import com.ch.leyu.adapter.ListChangeGridAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.Property;
import com.ch.leyu.responseparse.VideoSearchResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.xlistview.XListView;
import com.ch.leyu.widget.xlistview.XListView.IXListViewListener;

/***
 * 搜索结果列表显示 暂未处理没有结果时的显示情况
 *
 * @author L
 */
public class SearchListActivity extends BaseActivity {

    /** 最新 */
    private RadioButton mNewst;

    /** 最热 */
    private RadioButton mHottest;

    private XListView mXListView;

    /** 搜索结果 */
    private TextView mResult;

    /** 搜索内容 */
    private EditText mEditText;

    /** 搜索按钮 */
    private Button mSearch;

    private VideoSearchResponse mResponse;

    private String mKeyWord;

    private ListChangeGridAdapter mAdapter;

    private RelativeLayout mLayout;

    private int mPage = 1;

    private int mTotalPage;

    private SimpleDateFormat mSimpleDateFormat;

    private String mTotal = "0";

    private RequestParams mRequestParams;

    private static final String S1 = "<font color=\"#3F74A7\">" + "共有"+ "</font> <font color=\"#C73030\">";

    private static final String S2 = "</font> <font color=\"#3F74A7\">"+ "个搜索结果" + "</font> ";

    @Override
    protected void getExtraParams() {
        Intent intent = getIntent();
        if (intent != null) {
            mResponse = (VideoSearchResponse) intent.getSerializableExtra("result");
            mKeyWord = intent.getStringExtra(Constant.KEYWORD);
            mTotal = mResponse.getTotal();
            mRequestParams = new RequestParams();
            mRequestParams.put(Constant.KEYWORD, mKeyWord);
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_searchlist);
    }

    @Override
    protected void findViewById() {
        mLayout = (RelativeLayout) findViewById(R.id.searchlist_include);
        mNewst = (RadioButton) findViewById(R.id.act_search_rb_news);
        mHottest = (RadioButton) findViewById(R.id.act_search_rb_hots);
        mXListView = (XListView) findViewById(R.id.act_searchlist_gd);
        mResult = (TextView) findViewById(R.id.act_searchlist_tv_count);
        mEditText = (EditText) mLayout.findViewById(R.id.search_head_et_detail);
        mSearch = (Button) mLayout.findViewById(R.id.search_head_bt_commit);

    }

    @Override
    protected void setListener() {
        mSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPage = 1;
                mKeyWord = mEditText.getText().toString();
                mRequestParams = new RequestParams();
                mRequestParams.put(Constant.KEYWORD, mKeyWord);
                searchOnclick(mRequestParams);
            }
        });
        mNewst.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPage = 1;
                mRequestParams = new RequestParams();
                mRequestParams.put(Constant.SORT, "click");
                mRequestParams.put(Constant.KEYWORD, mKeyWord);
                searchOnclick(mRequestParams);
            }
        });
        mHottest.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPage = 1;
                mRequestParams = new RequestParams();
                mRequestParams.put(Constant.KEYWORD, mKeyWord);
                searchOnclick(mRequestParams);
            }
        });
        mAdapter.setOnGridClickListener(new GridItemClickListener() {

            @Override
            public void onGridItemClicked(View v, int position, long itemId) {
                Property item = mAdapter.getArrayList().get(position);
                if (item != null) {
                    String videoId = item.getId();
                    Intent intent = new Intent(SearchListActivity.this,VideoPlayActivity.class);
                    intent.putExtra(Constant.CID, videoId);
                    startActivity(intent);
                }

            }
        });

        mXListView.setXListViewListener(mIXListViewListenerImp);
    }

    @Override
    protected void processLogic() {

        mResult.setText(Html.fromHtml(S1 + mTotal + S2));
        mXListView.setPullLoadEnable(true);
        mXListView.setPullRefreshEnable(true);
        mAdapter = new ListChangeGridAdapter(mResponse.getVideoList(), this);
        mAdapter.setNumColumns(2);
        mXListView.setAdapter(mAdapter);
        mXListView.stopLoadMore();
        mPage = 2;
    }

    public void searchOnclick(RequestParams params) {

        JHttpClient.get(this, Constant.URL + Constant.SEARCH, params,
                VideoSearchResponse.class,
                new DataCallback<VideoSearchResponse>() {

                    @Override
                    public void onStart() {
                        showProgressDialog();
                        if (mXListView != null) {
                            onLoad();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                            VideoSearchResponse data) {
                        if (data != null) {
                            mTotal = data.getTotal();
                            mTotalPage = data.getTotalPage();
                            mAdapter = new ListChangeGridAdapter(data.getVideoList(), SearchListActivity.this);
                            mAdapter.setNumColumns(2);
                            mXListView.setAdapter(mAdapter);
                            mResult.setText(Html.fromHtml(S1 + mTotal + S2));
                            /* mAdapter.chargeArrayList(data.getVideoList()); */
                            if (mPage >= mTotalPage) {
                                mXListView.setPullLoadEnable(false);
                            } else {
                                mXListView.setPullLoadEnable(true);
                            }
                            mPage = 2;
                            mAdapter.setOnGridClickListener(new GridItemClickListener() {

                                @Override
                                public void onGridItemClicked(View v,int position, long itemId) {
                                    Property item = mAdapter.getArrayList().get(position);
                                    if (item != null) {
                                        String videoId = item.getId();
                                        Intent intent = new Intent(SearchListActivity.this,VideoPlayActivity.class);
                                        intent.putExtra(Constant.CID, videoId);
                                        startActivity(intent);
                                    }

                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                            String responseString, Exception exception) {
                        if (mPage <= 1) {
                            mHttpErrorView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFinish() {
                        closeProgressDialog();
                    }
                });

    }

    @Override
    protected void reload() {

    }

    protected void requestData(int page) {
        mRequestParams.put(Constant.PAGE, page);
        System.out.println(JHttpClient.getUrlWithQueryString(Constant.URL+ Constant.SEARCH, mRequestParams));
        JHttpClient.get(this, Constant.URL + Constant.SEARCH, mRequestParams,VideoSearchResponse.class,
                new DataCallback<VideoSearchResponse>() {

                    @Override
                    public void onStart() {
                        showProgressDialog();
                        if (mXListView != null) {
                            onLoad();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers,VideoSearchResponse data) {
                        if (data != null) {
                            mTotal = data.getTotal();
                            mTotalPage = data.getTotalPage();
                            if (mPage == 1) {
                                mAdapter.chargeArrayList(data.getVideoList());
                            } else {
                                mAdapter.addArrayList(data.getVideoList());
                            }
                            mResult.setText(Html.fromHtml(S1 + mTotal + S2));
                            mPage++;
                            if (mPage > mTotalPage) {
                                mXListView.setPullLoadEnable(false);
                            } else {
                                mXListView.setPullLoadEnable(true);
                            }

                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                            String responseString, Exception exception) {
                        if (mPage <= 1) {
                            mHttpErrorView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFinish() {
                        closeProgressDialog();
                    }
                });

    }

    private XListView.IXListViewListener mIXListViewListenerImp = new IXListViewListener() {
        // 下拉刷新
        @Override
        public void onRefresh() {
            mPage = 1;
            requestData(mPage);
        }

        // 上拉加载
        @Override
        public void onLoadMore() {
            requestData(mPage);

        }
    };

    // 加载中时间监听
    private void onLoad() {
        mXListView.stopRefresh();
        mXListView.stopLoadMore();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        mXListView.setRefreshTime(mSimpleDateFormat.format(new Date()));
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
                    Intent intent = new Intent(this, SearchActivity.class);
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
