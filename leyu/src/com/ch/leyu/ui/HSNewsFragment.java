package com.ch.leyu.ui;

import org.apache.http.Header;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.ch.leyu.R;
import com.ch.leyu.adapter.FouceNewsPagerAdapter;
import com.ch.leyu.adapter.HSNewsGridViewAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.AllNewResponse;
import com.ch.leyu.responseparse.Property;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.view.AutoScrollViewPager;
import com.ch.leyu.widget.view.CircleLoopPageIndicator;
import com.ch.leyu.widget.view.CustomScrollView;
import com.ch.leyu.widget.view.LYGridView;

/***
 * 新闻资讯--炉石攻略
 *
 * @author Administrator
 */
public class HSNewsFragment extends BaseFragment implements OnItemClickListener {

	private LYGridView mGridView;

	private AutoScrollViewPager mViewPager;

	private CircleLoopPageIndicator mPageIndicator;

	private View layout;

	private HSNewsGridViewAdapter maAdapter;

	private CustomScrollView mCustomScrollView;

	public static FragmentActivity activity;

	public static boolean sRemove = false;

	public static boolean sAddToBackStack = false;

	@Override
	protected void getExtraParams() {
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.fragment_hsnews);
	}

	@Override
	protected void loadfindViewById() {
		mGridView = (LYGridView) findViewById(R.id.fragment_hsnews_grid);
		layout = findViewById(R.id.fragment_hsnews_include);
		mViewPager = (AutoScrollViewPager) layout.findViewById(R.id.all_auto_scroll_viewpager);
		mPageIndicator = (CircleLoopPageIndicator) layout.findViewById(R.id.all_cirle_pageindicator);
		mCustomScrollView = (CustomScrollView) findViewById(R.id.customscrollview);
	}

	@Override
	protected void setListener() {
		mGridView.setOnItemClickListener(this);
	}

	@Override
	protected void processLogic() {
		mCustomScrollView.setAutoScrollViewPager(mViewPager);
		requestData();
	}

	private void requestData() {
		RequestParams params = new RequestParams();
		params.put(Constant.GMAE_ID, "23");
		JHttpClient.get(getActivity(), Constant.URL + Constant.ALL_NEWS,params, AllNewResponse.class,
				new DataCallback<AllNewResponse>() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,AllNewResponse data) {
						if (data != null) {
							maAdapter = new HSNewsGridViewAdapter(data.getCareer(), getActivity());
							mGridView.setAdapter(maAdapter);
							mViewPager.startAutoScroll(2000);
							mViewPager.setInterval(4000);
							mViewPager.setAdapter(new FouceNewsPagerAdapter(getActivity(), data.getFocus()));
							mViewPager.setCurrentItem(data.getFocus().size() * 10000);
							mPageIndicator.setPageCount(data.getFocus().size());
							mPageIndicator.setViewPager(mViewPager);

						}

					}

					@Override
					public void onStart() {
						mHttpErrorView.setVisibility(View.GONE);
						mHttpLoadingView.setVisibility(View.VISIBLE);
					}

					@Override
					public void onFinish() {
						mHttpLoadingView.setVisibility(View.GONE);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,String responseString, Exception exception) {
						mHttpErrorView.setVisibility(View.VISIBLE);
					}
				});

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		Property item = (Property) parent.getAdapter().getItem(position);
		if (item != null) {
			Intent intent = new Intent(getActivity(), HSNewsDetailActivity.class);
			intent.putExtra(Constant.CID, item.getCid());
			intent.putExtra(Constant.TITLE, item.getTitle());
			getActivity().startActivity(intent);
		}
	}

	@Override
	protected void reload() {
		requestData();
	}

}
