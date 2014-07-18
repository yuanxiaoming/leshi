
package com.ch.leyu.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.ch.leyu.R;
import com.ch.leyu.application.ExitAppUtils;
import com.ch.leyu.widget.view.CustomProgressDialog;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends ActionBarActivity {
	protected FragmentActivity mContext;

	protected ActionBar mActionBar;

	private LinearLayout mActivityContent; // 将子类的布局加载进来

	private View mChildView;

	protected ViewStub mHttpLoading, mHttpError;

	protected View mHttpLoadingView = null;

	protected View mHttpErrorView = null;

	protected CustomProgressDialog mProgressDialog;

	public Button mButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_base);
		mActivityContent = (LinearLayout) super.findViewById(R.id.activity_content_base);
		mHttpLoading = (ViewStub) super.findViewById(R.id.viewstub_http_loading);
		mHttpError = (ViewStub) super.findViewById(R.id.viewstub_http_error);

		if (mHttpLoading != null) {
			mHttpLoadingView = mHttpLoading.inflate();
			mHttpLoadingView.setVisibility(View.GONE);
		}
		if (mHttpError != null) {
			mHttpErrorView = mHttpError.inflate();
			mHttpErrorView.setVisibility(View.GONE);
		}
		mButton = (Button) mHttpErrorView.findViewById(R.id.loading_lose_btn);
		mContext = this;
		mActionBar = getSupportActionBar();
		ExitAppUtils.getInstance().pushActivity(this);

		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				reload();
			}
		});
		initView();

	}

	private void initView() {
		getExtraParams();
		loadViewLayout();
		loadfindViewById();
		processLogic();
		setListener();

	}

	/**
	 * 要将子类的布局文件加载进BaseActivity布局文件中
	 */
	public void setContentView(int layoutId) {
		mChildView = LayoutInflater.from(this).inflate(layoutId, null);
		mActivityContent.addView(mChildView, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	}

	public View findViewById(int viewId) {
		if(mChildView!=null){
			return mChildView.findViewById(viewId);
		}
		return super.findViewById(viewId);
	}

	public String getResourceString(int resId) {
		return getResources().getString(resId);
	}

	public void hidden() {
		if(isOpenKeyBoard()==true){
			// 隐藏键盘
			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
					mContext.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}

	}

	public boolean isOpenKeyBoard(){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		boolean isOpen=imm.isActive();
		return isOpen;
	}

	/**
	 * 参数传递
	 */
	protected abstract void getExtraParams();

	/**
	 * 加载布局
	 */
	protected abstract void loadViewLayout();

	/**
	 * 初始化控件
	 */
	protected abstract void loadfindViewById();

	/**
	 * 设置监听
	 */
	protected abstract void setListener();

	/**
	 * 处理逻辑
	 */
	protected abstract void processLogic();

	/***
	 * 加载失败 重新加载
	 */
	protected abstract void reload();

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SplashScreen"); //统计页面
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("SplashScreen"); // 保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ExitAppUtils.getInstance().removeActivity(this);

	}

	/**
	 * @return void 返回类型
	 * @Title: showProgressDialog(设置进度条)
	 * @author xiaoming.yuan
	 * @data 2013-7-12 下午10:09:36
	 */
	protected void showProgressDialog() {
		if ((!isFinishing()) && this.mProgressDialog == null) {
			this.mProgressDialog = new CustomProgressDialog(this);
		}
		// 设置ProgressDialog 的进度条style
		this.mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// this.mProgressDialog.setTitle("提示");
		this.mProgressDialog.setMessage("加载中...");
		// 设置ProgressDialog 的进度条是否不明确
		this.mProgressDialog.setIndeterminate(false);
		// 设置ProgressDialog 的进度条是否不明确
		this.mProgressDialog.setCancelable(false);
		this.mProgressDialog.setCanceledOnTouchOutside(false);
		this.mProgressDialog.show();
	}

	/**
	 * @return void 返回类型
	 * @Title: closeProgressDialog(关闭进度条)
	 * @author xiaoming.yuan
	 * @data 2013-7-12 下午10:09:30
	 */
	protected void closeProgressDialog() {
		if (this.mProgressDialog != null && this.mProgressDialog.isShowing())
			this.mProgressDialog.dismiss();
	}

}
