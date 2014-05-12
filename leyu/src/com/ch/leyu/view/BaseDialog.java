package com.ch.leyu.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ch.leyu.R;
import com.ch.leyu.utils.CommonUtil;

public abstract class BaseDialog extends Dialog implements android.view.View.OnClickListener{
	private boolean mCancelable ;

	public BaseDialog(Context context,boolean cancelable) {
		super(context, R.style.MyDialogStyle);
		mCancelable = cancelable ;
	}

	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		initView();
		Window window = getWindow();
		window.setBackgroundDrawableResource(android.R.color.transparent);
		WindowManager.LayoutParams params = window.getAttributes(); 
		params.width = CommonUtil.getWidthMetrics(getContext());
		params.gravity = Gravity.CENTER; 
		window.setAttributes(params); 
		setCanceledOnTouchOutside(mCancelable);
	}

	private void initView() {
		loadLayout();
		findViewById();
		processLogic();
		setListener();
	}
	
	public void setContentView(int layoutResID){
		super.setContentView(layoutResID);
	}
	
	public View findViewById(int resID){
		return super.findViewById(resID);
	}
	
	protected abstract void findViewById();
	
	protected abstract void processLogic();
	
	protected abstract void setListener();
	
	protected abstract void loadLayout();
	
}
