package com.ch.leyu.http.httplibrary;

import com.ch.leyu.http.cacheinterface.HttpCache;
import com.ch.leyu.http.cacheservice.ServerDataCache;
import com.ch.leyu.http.parserinterface.BaseParser;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;

import org.apache.http.Header;

import android.content.Context;
import android.util.Log;

public class JAsyncHttpResponseHandler<T> extends AsyncHttpResponseHandler {
    private Context mContext;
    private BaseParser<T> mBaseParser;
    private String mCacheUrl;
    private HttpCache mHttpCache;
    private DataCallback<T> mDataCallback ;

    public JAsyncHttpResponseHandler(Context context, BaseParser<T> baseParse,HttpCache httpCache, String cacheUrl, DataCallback<T> callback) {
        this.mContext = context;
        this.mBaseParser = baseParse;
        this.mCacheUrl = cacheUrl;
        this.mHttpCache = httpCache;
        this.mDataCallback = callback ;
    }

    @Override
    public void onStart() {
        if(mDataCallback != null){
            mDataCallback.onStart();
        }
    }

    @Override
    public void onFinish() {
        if(mDataCallback != null){
            mDataCallback.onFinish();
        }
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        if (statusCode == 200) {
                try {
                    String parseString = new String(responseBody,"UTF-8");
                    mDataCallback.onSuccess(statusCode, headers, mBaseParser.parse(parseString));
                    // Successfully returned to save the server data
                    ServerDataCache cache = new ServerDataCache(mCacheUrl, parseString, System.currentTimeMillis());
                    if (mHttpCache != null) {
                        if (mHttpCache.isNotExpried()) {
                            cache.setTime(JHttpClient.NOT_EXPIRED);
                        }
                        // insert httpcache
                        mHttpCache.putHttpCache(cache);
                    }
                } catch (Exception e) {
                    Log.d("JAsyncHttpResponseHandler", "statusCode="+statusCode+"---"+e.getLocalizedMessage());
                    onFailure(200, headers, responseBody,e);
                }
            }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.d("JAsyncHttpResponseHandler", "statusCode="+statusCode, error);
        // 请求失败 返回缓存数据
           if (mHttpCache != null) {
               JHttpClient.getCache(mContext, mBaseParser, mDataCallback, mCacheUrl);
               //这里调用onFinish()方法hide 进度条操作
               mDataCallback.onFinish();
           }else{
                if(mDataCallback != null){
                    mDataCallback.onFailure(statusCode, headers, null, new Exception(error));
                }
           }
    }

}
