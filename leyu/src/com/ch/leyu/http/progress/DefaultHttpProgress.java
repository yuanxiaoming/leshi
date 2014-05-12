package com.ch.leyu.http.progress;

import android.content.Context;

import com.ch.leyu.view.ProgressDialog;

public class DefaultHttpProgress implements HttpProgress {
	private ProgressDialog mProgressDialog ;
	private String mTitle ;
	
	public DefaultHttpProgress(Context context , String title){
		mProgressDialog = new ProgressDialog(context);
		mTitle = title ;
	}
	
	@Override
	public void show() {
		if(mProgressDialog != null)
		{
			mProgressDialog.show();
			mProgressDialog.setTitle(mTitle);
		}
	}

	@Override
	public void dismiss() {
		if(mProgressDialog != null)
		{
			mProgressDialog.dismiss();
		}
	}
}
