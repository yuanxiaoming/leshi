package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.R.id;
import com.ch.leyu.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public abstract class BaseFragment extends Fragment implements OnClickListener {
	public View mContentView;
	public LayoutInflater mInflater;
	private int resId = 0;
	private LinearLayout mFragmentContent; // 所有子类的布局都要加载到这个View里面
	protected ViewStub mHttpLoading;
	protected ViewStub mHttpError;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.mInflater = inflater;

		// 加载基类的布局文件
		mFragmentContent = (LinearLayout) inflater.inflate(R.layout.fragment_base, container, false);
		mHttpLoading = (ViewStub) mFragmentContent.findViewById(R.id.viewstub_http_loading);
		mHttpError = (ViewStub) mFragmentContent.findViewById(R.id.viewstub_http_error);

		getExtraParams();
		loadViewLayout();
		if (resId != 0) {
			mContentView = inflater.inflate(resId, container, false);
			// 将子类的布局加载进来
			mFragmentContent.addView(mContentView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			findViewById();
			processLogic();
			setListener();
			return mFragmentContent;
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public void setContentView(int resId) {
		this.resId = resId;
	}

	// public void setContentView(String layoutId) {
	// this.resId = ResourceUtil.getLayoutId(getActivity(), layoutId);
	// }
	//
	// public View findViewById(View contentView ,String resId){
	// return contentView.findViewById(ResourceUtil.getId(getActivity(),
	// resId));
	// }

	public View findViewById(int resId) {
		return mContentView.findViewById(resId);
	}

	protected abstract void findViewById();

	protected abstract void loadViewLayout();

	protected abstract void processLogic();

	protected abstract void setListener();

	protected abstract void getExtraParams();

	// @Override
	// public void setUserVisibleHint(boolean isVisibleToUser) {
	//
	// super.setUserVisibleHint(isVisibleToUser);
	// }
}
