package com.ch.leyu.widget.xlistview;

import com.ch.leyu.adapter.eventbus.XListViewTouchEventBus;
import com.ch.leyu.utils.CommonUtil;

import de.greenrobot.event.EventBus;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class XXListView extends XListView {
	public static final String TAG = "XXListView";

	public XXListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public XXListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public XXListView(Context context) {
		super(context);
	}
	
	 private float mPreY ;
	    
	    @Override
	    public boolean dispatchTouchEvent(MotionEvent ev) {
	    	
	    	switch (ev.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				mPreY = ev.getRawY();
				System.out.println("mpey = "+mPreY);
				break;

			case MotionEvent.ACTION_MOVE:
				float curY = ev.getRawY();
				System.out.println("curY = "+mPreY);
				float deltaY = curY - mPreY ;
				System.out.println("deltaY = "+deltaY);
				//XListView滑动的时候 禁止掉界面布局的移动
				EventBus.getDefault().post(new XListViewTouchEventBus(deltaY,this));
				mPreY = ev.getRawY();
				break ;
			default:
				break;
			}
	    	//这里返回false 没作用？
	    	return CommonUtil.getAppliction(getContext()).isScroll() ? super.dispatchTouchEvent(ev) : true ;
	    }
}
