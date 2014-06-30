
package com.ch.leyu.ui;

import org.apache.http.Header;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.api.FrontiaAuthorization.MediaType;
import com.baidu.frontia.api.FrontiaSocialShare;
import com.baidu.frontia.api.FrontiaSocialShare.FrontiaTheme;
import com.baidu.frontia.api.FrontiaSocialShareContent;
import com.baidu.frontia.api.FrontiaSocialShareListener;
import com.ch.leyu.R;
import com.ch.leyu.adapter.VideoDetailPagerAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.VideoPlayResponse;
import com.ch.leyu.utils.CommonUtil;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.ch.leyu.widget.view.LYViewPager;
import com.ch.leyu.widget.view.PagerSlidingTabStrip;
import com.letv.playutils.PlayUtils;
import com.nostra13.universalimageloader.core.ImageLoader;





/***
 * 视频播放
 *
 * @author L
 */
public class VideoPlayActivity extends BaseActivity {

	private LYViewPager mViewPager;

	private PagerSlidingTabStrip mSlideTabIndicator;

	private ImageView mImg;

	private String mId;

	private VideoDetailPagerAdapter mAdapter;

	//百度分享
	private FrontiaSocialShare mSocialShare;

	private FrontiaSocialShareContent mImageContent = new FrontiaSocialShareContent();

	private String vu ;

	private String title;

	private String mShareImg ="";

	private String mUrl = "http://www.legames.cn/";


	@Override
	protected void onStart() {
		super.onStart();
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("乐娱视频");
		actionBar.setLogo(R.drawable.legames_back);
		actionBar.setHomeButtonEnabled(true);
	}

	@Override
	protected void getExtraParams() {
		Intent intent = getIntent();
		if (intent != null) {
			mId = intent.getStringExtra(Constant.CID);
		}

	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_videodetail);
	}

	@Override
	protected void findViewById() {
		mImg = (ImageView) findViewById(R.id.act_videodetail_img);
		mViewPager = (LYViewPager) findViewById(R.id.act_videodetail_viewpager);
		mSlideTabIndicator = (PagerSlidingTabStrip) findViewById(R.id.act_videodetail_pagertab);
	}

	@Override
	protected void setListener() {
		mImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/**
				 * @param context
				 * @param userKey 用户私钥
				 * @param userUnique 用户ID
				 * @param videoUnique 视频ID
				 * @param videoName 视频名称
				 */
				PlayUtils.playVideo(VideoPlayActivity.this, "c24462c6cd50d25c57a8e8ec32f597ae", "20c3de8a2e", vu, title);

			}
		});

		mImg.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mImg.setAlpha(200);
					break;
				case MotionEvent.ACTION_UP:
					mImg.setAlpha(255);
					break;

				default:
					break;
				}

				return false;
			}
		});

	}

	@Override
	protected void processLogic() {
		mImg.setLayoutParams(new RelativeLayout.LayoutParams(CommonUtil.getWidthMetrics(mContext) / 1, CommonUtil.getWidthMetrics(mContext) / 2));
		mImg.setScaleType(ScaleType.FIT_XY);
		final int textSize = (int)getResources().getDimension(R.dimen.tab_title_size);
		mSlideTabIndicator.setTextSize(textSize);
		requestData(mId, Constant.URL + Constant.VIDEO_URL + Constant.VIDEO_DETAIL);
		baiduShareConfig();
	}

	private void requestData(String mid, String url) {
		RequestParams params = new RequestParams();
		params.put("id", mid);
		JHttpClient.get(this, url, params, VideoPlayResponse.class,new DataCallback<VideoPlayResponse>() {

			@Override
			public void onStart() {
				mHttpErrorView.setVisibility(View.GONE);
				mHttpLoadingView.setVisibility(View.VISIBLE);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, VideoPlayResponse data) {
				if (data != null) {
					vu = data.getVideoInfo().getVu();
					title = data.getVideoInfo().getTitle();
					mShareImg = data.getVideoInfo().getImageSrc();
					mUrl = data.getVideoInfo().getLinkUrl();
					mAdapter = new VideoDetailPagerAdapter(getSupportFragmentManager(),data.getVideoInfo(),mId);
					mViewPager.setAdapter(mAdapter);
					mSlideTabIndicator.setViewPager(mViewPager);
					ImageLoader.getInstance().displayImage(data.getVideoInfo().getImageSrc(), mImg,
							ImageLoaderUtil.getImageLoaderOptions());
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
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_share:
			baiduShare();
			break;

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

	private void baiduShare() {
		mImageContent.setContent(title);
		mImageContent.setLinkUrl(mUrl);
		mImageContent.setImageUri(Uri.parse(mShareImg));

		mSocialShare.show(getWindow().getDecorView(),mImageContent, FrontiaTheme.LIGHT,  new ShareListener());
	}

	private void baiduShareConfig() {
		Frontia.init(this, "ZFkbingwMIo36LV2YrjkCThu");
		mSocialShare = Frontia.getSocialShare();
		mSocialShare.setContext(this);
		mSocialShare.setClientId(MediaType.SINAWEIBO.toString(), "1098403121");
		mSocialShare.setClientId(MediaType.QZONE.toString(), "101069451");
		mSocialShare.setClientId(MediaType.QQFRIEND.toString(), "101069451");
		mSocialShare.setClientId(MediaType.QQWEIBO.toString(), "801517958");
		mSocialShare.setClientId(MediaType.WEIXIN.toString(), "wx3822d16c9c071ef2");
		mSocialShare.setClientName(MediaType.QQFRIEND.toString(), "乐娱互动");
	}

	private class ShareListener implements FrontiaSocialShareListener {

		@Override
		public void onSuccess() {
			Log.d("Test","share success");
		}

		@Override
		public void onFailure(int errCode, String errMsg) {
			Log.d("Test","share errCode "+errCode);
		}

		@Override
		public void onCancel() {
			Log.d("Test","cancel ");
		}

	}

	@Override
	protected void reload() {
		requestData(mId, Constant.URL + Constant.VIDEO_URL + Constant.VIDEO_DETAIL);

	}

}
