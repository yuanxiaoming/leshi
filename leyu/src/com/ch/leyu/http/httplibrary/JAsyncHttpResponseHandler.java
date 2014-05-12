package com.ch.leyu.http.httplibrary;

import com.ch.leyu.http.cacheinterface.HttpCache;
import com.ch.leyu.http.cacheservice.ServerDataCache;
import com.ch.leyu.http.parserinterface.BaseParser;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;

import org.apache.http.Header;

import android.content.Context;
import android.util.Log;

public abstract class JAsyncHttpResponseHandler<T> extends AsyncHttpResponseHandler {
	private Context mContext;
	private BaseParser<T> mParser;
	private String mCacheUrl;
	private HttpCache mHttpCache;
	private DataCallback<T> mCallback ;

	public JAsyncHttpResponseHandler(Context context, BaseParser<T> baseParse, String cacheUrl, HttpCache httpCache,DataCallback<T> callback) {
		this.mContext = context;
		this.mParser = baseParse;
		mCacheUrl = cacheUrl;
		mHttpCache = httpCache;
		mCallback = callback ;
	}

	@Override
	public void onStart() {
		if(mCallback != null){
			mCallback.onStart();
		}
	}

	@Override
	public void onFinish() {
		if(mCallback != null){
			mCallback.onFinish();
		}
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
		if (statusCode == 200) {
			T parseJSON = null;
			if (mParser != null) {
				try {
					parseJSON = mParser.parse(new String(responseBody));
					onSuccess(statusCode, headers, parseJSON);
					// Successfully returned to save the server data
					ServerDataCache cache = new ServerDataCache(mCacheUrl, new String(responseBody), System.currentTimeMillis());
					if (mHttpCache != null) {
						if (mHttpCache.isNotExpried()) {
							cache.setTime(JHttpClient.NOT_EXPIRED);
						}
						// insert httpcache
						mHttpCache.putHttpCache(cache);
					}
				} catch (Exception e) {
					onFailure(200, headers, responseBody,e);
				}
			}
		}
	}

	@Override
	public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
		   Log.d("JAsyncHttpResponseHandler", "statusCode="+statusCode, error);
//		   onFailure( statusCode, headers, responseBody, new Exception(error));
		  
		   // 请求失败 返回缓存数据
           if (mHttpCache != null) {
               JHttpClient.getCache(mContext, mParser, mCallback, mCacheUrl);
               
               //这里调用onFinish()方法hide 进度条操作
               mCallback.onFinish();
           }else{
	        	   if(mCallback != null){
	   				mCallback.onFailure(statusCode, headers, null, new Exception(error));
	   			}
           }
	}

//	public abstract void onFailure(int statusCode,Header[] headers, byte[] responseBody,Exception e);


	public abstract void onSuccess(int statusCode, Header[] headers, T serverData);
}
