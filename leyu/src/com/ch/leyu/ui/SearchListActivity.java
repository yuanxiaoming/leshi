
package com.ch.leyu.ui;

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

import org.apache.http.Header;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * 搜索结果列表显示 暂未处理没有结果时的显示情况
 * 
 * @author L
 */
public class SearchListActivity extends BaseActivity implements OnClickListener, GridItemClickListener{

    /** 最新 */
    private RadioButton mNewst;

    /** 最热 */
    private RadioButton mHottest;

    private RadioGroup mGroup;

    private XListView mXListView;

    /** 搜索结果 */
    private TextView mResult;

    /** 搜索内容 */
    private EditText mEditText;

    private Button mSearch;

    private VideoSearchResponse mResponse;

    private String keyWord;

    private ListChangeGridAdapter mAdapter;

    private RelativeLayout mLayout;

    private int mPage = 1;

    private int mTotalPage;

    private SimpleDateFormat mSimpleDateFormat;
    
    private boolean mFlag = false;
    
    private String mTotal = "0" ;
    
    

    @Override
    protected void getExtraParams() {
        Intent intent = getIntent();
        if (intent != null) {
            mResponse = (VideoSearchResponse) intent.getSerializableExtra("result");
            keyWord = intent.getStringExtra(Constant.KEYWORD);
            mTotal = mResponse.getTotal();
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_searchlist);
    }

    @Override
    protected void findViewById() {
        mLayout = (RelativeLayout) findViewById(R.id.searchlist_include);
        mGroup = (RadioGroup) findViewById(R.id.search_radiogroup);
        mNewst = (RadioButton) findViewById(R.id.act_search_rb_news);
        mHottest = (RadioButton) findViewById(R.id.act_search_rb_hots);
        mXListView = (XListView) findViewById(R.id.act_searchlist_gd);
        mResult = (TextView) findViewById(R.id.act_searchlist_tv_count);
        mEditText = (EditText) mLayout.findViewById(R.id.search_head_et_detail);
        mSearch = (Button) mLayout.findViewById(R.id.search_head_bt_commit);
        mAdapter = new ListChangeGridAdapter(null, this);
    }

    @Override
    protected void setListener() {
        mSearch.setOnClickListener(this);
        mNewst.setOnClickListener(this);
        mHottest.setOnClickListener(this);
        mAdapter.setOnGridClickListener(this);
//        mXListView.setXListViewListener(mIXListViewListenerImp);
    }

    @Override
    protected void processLogic() {
        String s1 = "<font color=\"#3F74A7\">"+"共有"+"</font> ";
        String s2 = "<font color=\"#C73030\">"+mTotal+"</font> ";
        String s3 = "<font color=\"#3F74A7\">"+"个搜索结果"+"</font> ";
        mResult.setText(Html.fromHtml(s1+s2+s3));
        mAdapter.setNumColumns(2);
        mXListView.setPullLoadEnable(true);
        mXListView.setPullRefreshEnable(true);
        mXListView.setAdapter(mAdapter);
        mAdapter.chargeArrayList(mResponse.getVideoList());
    }

    @Override
    public void onClick(View v) {
        RequestParams params = null;
        if (v.getId() == R.id.search_head_bt_commit) {
            keyWord = mEditText.getText().toString();
            params = new RequestParams();
            params.put(Constant.KEYWORD, keyWord);
        }
        if (v.getId() == R.id.act_search_rb_news) {
            params = new RequestParams();
            params.put(Constant.SORT, "click");
            params.put(Constant.KEYWORD, keyWord);
        }
        if (v.getId() == R.id.act_search_rb_hots) {
            params = new RequestParams();
            params.put(Constant.KEYWORD, keyWord);
        }
        JHttpClient.get(this, Constant.URL + Constant.SEARCH, params, VideoSearchResponse.class,
                new DataCallback<VideoSearchResponse>() {

                    @Override
                    public void onStart() {
                        showProgressDialog();
                        if (mXListView != null) {
                            onLoad();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, VideoSearchResponse data) {
                        if (data != null) {
                            mTotal = data.getTotal();
                            mTotalPage = data.getTotalPage();
                            if (mPage == 1) {
                                mAdapter.chargeArrayList(data.getVideoList());
                            } else {
                                mAdapter.addArrayList(data.getVideoList());
                            }
                            
                            mPage++;
                            if (mPage > mTotalPage) {
                                mXListView.setPullLoadEnable(false);
                            } else {
                                mXListView.setPullLoadEnable(true);
                            }
                            
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,
                            Exception exception) {
                        if(mPage<=1){
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
    
//    private XListView.IXListViewListener mIXListViewListenerImp = new IXListViewListener() {
//        // 下拉刷新
//        @Override
//        public void onRefresh() {
//            mFlag = true ;
//            mPage = 1;
//            requestData(mPage);
//        }
//
//        // 上拉加载
//        @Override
//        public void onLoadMore() {
//            requestData(mPage);
//
//        }
//    };

    // 加载中时间监听
    private void onLoad() {
        mXListView.stopRefresh();
        mXListView.stopLoadMore();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        mXListView.setRefreshTime(mSimpleDateFormat.format(new Date()));
    }

    @Override
    public void onGridItemClicked(View v, int position, long itemId) {
        Property item = mResponse.getVideoList().get(position);
        if (item != null) {
            String videoId = item.getId();
            Intent intent = new Intent(this, VideoPlayActivity.class);
            intent.putExtra(Constant.CID, videoId);
            startActivity(intent);
        }
    }
}
