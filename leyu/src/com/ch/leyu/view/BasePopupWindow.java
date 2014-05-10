package com.ch.leyu.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import com.ch.leyu.utils.CommonUtil;

public abstract class BasePopupWindow extends PopupWindow implements View.OnClickListener{
	private Context mContext ;
	private View mContentView  ;
	public BasePopupWindow(Context context) {
		super(context);
		init(context);
	}
//
//	public BasePopupWindow(Context context ,View contentView, int width, int height) {
//		super(contentView, width, height, true);
//		init(context);
//	}

	private void init(Context context) {
		mContext = context ;
		setFocusable(true);
		setOutsideTouchable(true);
//		setAnimationStyle(R.style.popwindow_anim_style);
		setBackgroundDrawable(new ColorDrawable());
		setTouchable(true);
		loadViewLayout();
		
		setWidth(CommonUtil.getWidthMetrics(context));
		setHeight(LayoutParams.WRAP_CONTENT);
		
		findViewById();
		processLogic();
		setListener();
	}
	
	public void setContentView(int resId) {
		mContentView = LayoutInflater.from(mContext).inflate(resId, null);
		super.setContentView(mContentView);
	}
	
	public abstract void loadViewLayout();
	
	public abstract void findViewById();

	public abstract void setListener();

	public abstract void processLogic();
	
	public View findViewById(int viewId) {
		return mContentView.findViewById(viewId);
	}

//	public View findViewById(String id) {
//		return mContentView.findViewById(ResourceUtil.getId(mContext, id));
//	}
	
}
