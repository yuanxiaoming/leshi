package com.ch.leyu.adapter.eventbus;

import com.ch.leyu.widget.xlistview.XListView;

public class XListViewTouchEventBus {
	
	private float mDeltaY ;
	private XListView mXListView ;
	
	public float getDeltaY(){
		return mDeltaY ;
	}
	
	public XListView getListView()
	{
		return mXListView ;
	}
	
	public XListViewTouchEventBus(float deltaY, XListView xListView) {
		mDeltaY = deltaY ;
		mXListView = xListView ;
	}
}
