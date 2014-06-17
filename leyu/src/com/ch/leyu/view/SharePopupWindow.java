package com.ch.leyu.view;

import android.content.Context;
import android.view.View;
import android.widget.GridView;

import com.ch.leyu.R;
import com.ch.leyu.adapter.ShareGridViewAdapter;

public class SharePopupWindow extends BasePopupWindow {

	private GridView mGvShare ;
	
	public SharePopupWindow(Context context, int width) {
		super(context, width);
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void loadViewLayout() {
		setContentView(R.layout.play_popopwindow);
	}

	@Override
	public void findViewById() {
		mGvShare = (GridView) findViewById(R.id.menu_pop_gridview);
	}

	@Override
	public void setListener() {
	}

	@Override
	public void processLogic() {
		ShareGridViewAdapter shareGridViewAdapter = new ShareGridViewAdapter(mContext); 
		mGvShare.setAdapter(shareGridViewAdapter);
		
	}
}
