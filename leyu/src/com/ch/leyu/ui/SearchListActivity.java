package com.ch.leyu.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

	/** 最多 */
	private RadioButton mHottest;

	/** 默认显示的listview */
	private XListView mXListView_default;

	/** 显示最多上传的listview */
	private XListView mXListView_hot;

	/** 搜索结果 */
	private TextView mResult;

	/** 搜索内容 */
	private EditText mEditText;

	/** 搜索按钮 */
	private Button mSearch;

	private RelativeLayout mLayout;

	private VideoSearchResponse mResponse;

	private String mKeyWord;

	/**默认列表适配器*/
	private ListChangeGridAdapter mAdapter_default;

	/**最多播放列表适配器*/
	private ListChangeGridAdapter mAdapter_hot;

	private int mPage_default = 1;

	private int mPage_hot = 1;

	private int mTotalPage;

	private SimpleDateFormat mSimpleDateFormat;

	private String mTotal = "0";

	private RequestParams mRequestParams;

	private static final String S1 = "<font color=\"#3F74A7\">" + "共有"+ "</font> <font color=\"#C73030\">";

	private static final String S2 = "</font> <font color=\"#3F74A7\">"+ "个搜索结果" + "</font> ";

	private ArrayList<Property> mNewList =null;

	private ArrayList<Property> mHotList = null;


	@Override
	protected void getExtraParams() {
		Intent intent = getIntent();
		if (intent != null) {
			mResponse = (VideoSearchResponse) intent.getSerializableExtra("result");
			mKeyWord = intent.getStringExtra(Constant.KEYWORD);
			mTotal = mResponse.getTotal();
			mNewList = mResponse.getVideoList();
			mPage_default = 2 ;
			mTotalPage = mResponse.getTotalPage();
			requestParams(0);
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
		mXListView_default = (XListView) findViewById(R.id.act_searchlist_gd);
		mXListView_hot = (XListView) findViewById(R.id.act_searchlist_gd_hot);
		mResult = (TextView) findViewById(R.id.act_searchlist_tv_count);
		mEditText = (EditText) mLayout.findViewById(R.id.search_head_et_detail);
		mSearch = (Button) mLayout.findViewById(R.id.search_head_bt_commit);
		mXListView_default.setXListViewListener(mIXListViewListenerImp);
		mXListView_hot.setXListViewListener(mIXListViewListenerImp);
	}

	private int a =0;

	@Override
	protected void setListener() {
		mSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			    a = 1 ;
				mPage_default = 1;
				mPage_hot=1;
				mKeyWord = mEditText.getText().toString();
				mXListView_default.setVisibility(View.VISIBLE);
				mXListView_default.setSelection(0);
				mXListView_hot.setVisibility(View.GONE);
				requestParams(0);
				requestData(mPage_default, 0);
				hidden();
			}
		});
		mNewst.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mXListView_default.setVisibility(View.VISIBLE);
				mXListView_hot.setVisibility(View.GONE);

			}
		});
		mHottest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mXListView_default.setVisibility(View.GONE);
				mXListView_hot.setVisibility(View.VISIBLE);
				requestParams(1);
				if(mPage_hot==1){
					mXListView_hot.setSelection(0);
					requestData(mPage_hot,1);
				}

			}
		});
		mAdapter_default.setOnGridClickListener(new GridItemClickListener() {

			@Override
			public void onGridItemClicked(View v, int position, long itemId) {
				Property item = mAdapter_default.getArrayList().get(position);
				if (item != null) {
					String videoId = item.getId();
					Intent intent = new Intent(SearchListActivity.this,VideoPlayActivity.class);
					intent.putExtra(Constant.CID, videoId);
					startActivity(intent);
				}

			}
		});

		mAdapter_hot.setOnGridClickListener(new GridItemClickListener() {

			@Override
			public void onGridItemClicked(View v, int position, long itemId) {
				Property item = mAdapter_hot.getArrayList().get(position);
				if (item != null) {
					String videoId = item.getId();
					Intent intent = new Intent(SearchListActivity.this,VideoPlayActivity.class);
					intent.putExtra(Constant.CID, videoId);
					startActivity(intent);
				}

			}
		});

	}

	@Override
	protected void processLogic() {
		mEditText.setText(mKeyWord);
		mResult.setText(Html.fromHtml(S1 + mTotal + S2));
		mXListView_default.setPullLoadEnable(true);
		mXListView_default.setPullRefreshEnable(true);
		mAdapter_default = new ListChangeGridAdapter(mNewList, this);
		mAdapter_default.setNumColumns(2);
		mXListView_default.setAdapter(mAdapter_default);

		mXListView_hot.setPullLoadEnable(true);
		mXListView_hot.setPullRefreshEnable(true);
		mAdapter_hot = new ListChangeGridAdapter(mHotList, this);
		mAdapter_hot.setNumColumns(2);
		mXListView_hot.setAdapter(mAdapter_hot);

	}

	@Override
	protected void reload() {

	}



	private void requestData(int page , final int listViewTag){
		Log.d("tag",JHttpClient.getUrlWithQueryString(Constant.URL+ Constant.SEARCH, mRequestParams));
		JHttpClient.get(this, Constant.URL + Constant.SEARCH, mRequestParams,VideoSearchResponse.class, new DataCallback<VideoSearchResponse>() {

			@Override
			public void onStart() {
				if(listViewTag==0){
					if (mXListView_default!= null) {
						onLoad();
					}
				}
				else if (listViewTag==1) {
					if (mXListView_hot!= null) {
						onLoad();
					}
					if(mPage_hot==1){
						 showProgressDialog();
					}
				}

				if(mTotalPage==1){
					mXListView_default.setPullLoadEnable(false);
				}
				
				if(a==1&&mPage_default==1){
				    showProgressDialog();
				}

			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,VideoSearchResponse data) {
				if(data!=null){
					mTotalPage = data.getTotalPage();
					mTotal = data.getTotal();
					mResult.setText(Html.fromHtml(S1 + mTotal + S2));
					if(listViewTag==0){
						mNewst.setChecked(true);
						if (mPage_default == 1) {
							mAdapter_default.chargeArrayList(data.getVideoList());
						} else {
							mAdapter_default.addArrayList(data.getVideoList());
						}
						mPage_default++;
						if (mPage_default > mTotalPage) {
							mXListView_default.setPullLoadEnable(false);
						} else {
							mXListView_default.setPullLoadEnable(true);
						}
					}
					else if (listViewTag==1) {
						mHottest.setChecked(true);
						if (mPage_hot == 1) {
							mAdapter_hot.chargeArrayList(data.getVideoList());
						} else {
							mAdapter_hot.addArrayList(data.getVideoList());
						}

						mPage_hot++;
						if (mPage_hot > mTotalPage) {
							mXListView_hot.setPullLoadEnable(false);
						} else {
							mXListView_hot.setPullLoadEnable(true);
						}
					}

				}else {
                    if(a==1){
                        Toast.makeText(SearchListActivity.this, R.string.search_hint_null, Toast.LENGTH_LONG).show();
                        a=0;
                    }
                }
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Exception exception) {

			}

			@Override
			public void onFinish() {
				closeProgressDialog();
			}
		});
	}

	private void requestParams(int listViewTag){
		mRequestParams = new RequestParams();
		if(listViewTag==0){
			mRequestParams.put(Constant.KEYWORD, mKeyWord);
			mRequestParams.put(Constant.PAGE, mPage_default);
		}
		else if(listViewTag==1){
			mRequestParams.put(Constant.KEYWORD, mKeyWord);
			mRequestParams.put(Constant.PAGE, mPage_hot);
			mRequestParams.put(Constant.SORT, "click");
		}
	}


	private XListView.IXListViewListener mIXListViewListenerImp = new IXListViewListener() {
		@Override
		public void onRefresh() {
			if(mNewst.isChecked()){
				mNewList.clear();
				mPage_default = 1;
				requestParams(0);
				requestData(mPage_default,0);
			}
			else  if(mHottest.isChecked()){
				mPage_hot = 1;
				requestParams(1);
				requestData(mPage_hot,1);
			}

		}

		@Override
		public void onLoadMore() {
			if(mNewst.isChecked()){
				requestParams(0);
				requestData(mPage_default,0);
			}
			else if(mHottest.isChecked()){
				requestParams(1);
				requestData(mPage_hot,1);
			}
		}
	};


	// 加载中时间监听
	@SuppressLint("SimpleDateFormat")
	private void onLoad() {
		mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if(mNewst.isChecked()){
			mXListView_default.stopRefresh();
			mXListView_default.stopLoadMore();
			mXListView_default.setRefreshTime(mSimpleDateFormat.format(new Date()));
		}
		if (mHottest.isChecked()) {
			mXListView_hot.stopRefresh();
			mXListView_hot.stopLoadMore();
			mXListView_hot.setRefreshTime(mSimpleDateFormat.format(new Date()));
		}
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
