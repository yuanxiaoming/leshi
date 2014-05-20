package com.ch.leyu.http.work;

import com.ch.leyu.http.cacheimpl.DefaultHttpCache;
import com.ch.leyu.http.cacheinterface.HttpCache;
import com.ch.leyu.http.cacheservice.DBOpenHelperManager;
import com.ch.leyu.http.cacheservice.ServerDataCache;
import com.ch.leyu.http.httplibrary.AsyncHttpClient;
import com.ch.leyu.http.httplibrary.AsyncHttpResponseHandler;
import com.ch.leyu.http.httplibrary.JAsyncHttpResponseHandler;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.parserinterface.BaseParser;
import com.ch.leyu.http.utils.NetWorkUtil;

import org.apache.http.client.CookieStore;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Http get请求缓存数据
 *
 * @Time 2014-4-21 上午10:00:21
 */
public class JHttpClient {
    public static final Long NOT_EXPIRED = Long.MAX_VALUE;

    public static final String GET = "get";

    public static final String POST = "post";

    public static AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();

    private static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        mAsyncHttpClient.post(url, params, responseHandler);
    }

    private static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        mAsyncHttpClient.get(url, params, responseHandler);
    }

    public static void setCookieStore(CookieStore cookieStore) {
        mAsyncHttpClient.setCookieStore(cookieStore);
    }

    private static String getUrlWithQueryString(String url, RequestParams params) {
        return AsyncHttpClient.getUrlWithQueryString(true, url, params);
    }

    private static <T> void getServerData(Context context, String url, RequestParams params, BaseParser<T> baseParser, String requestMethod, HttpCache httpCache, final DataCallback<T> dataCallback) {
        String cacheUrl = JHttpClient.getUrlWithQueryString(url, params).concat(requestMethod);
        // The  onlys URL
        if (baseParser == null) {
            throw new RuntimeException("BaseParse equals to null ,Please send a data parser");
        }
        if(!NetWorkUtil.isConnected(context)){
            Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
            getCache(context, baseParser, dataCallback, cacheUrl);
            return;
        }
        if (httpCache != null&&httpCache.getHttpCache(cacheUrl)) {
            getCache(context, baseParser, dataCallback, cacheUrl);
        }else {
            if (requestMethod != null && requestMethod.equals(GET)) {
                JHttpClient.get(url, params, new JAsyncHttpResponseHandler<T>( baseParser, httpCache, cacheUrl,dataCallback));
            } else if (requestMethod != null && requestMethod.equals(POST)) {
                JHttpClient.post(url, params, new JAsyncHttpResponseHandler<T>(baseParser, httpCache, cacheUrl,dataCallback));
            }
        }

    }

    private static <T> void getCache(Context context, BaseParser<T> baseParser, final DataCallback<T> dataCallback, String cacheUrl) {
        ServerDataCache cacheData = DBOpenHelperManager.getInstance(context).findCacheByUrl(cacheUrl);
        if(cacheData != null){
            try {
                T parse = baseParser.parse(cacheData.getServerData());
                dataCallback.onSuccess(200, null, parse);
                Log.d(JHttpClient.class.getSimpleName(), "Data taken from the cacheData");
            } catch (Exception e) {
                Log.d(JHttpClient.class.getSimpleName(), e.getLocalizedMessage());
                dataCallback.onFailure(200, null, cacheData.getServerData(), new JSONException("josn解析异常"));
            }
        }else{
            dataCallback.onFailure(500, null, "没有缓存", new JSONException("josn解析异常"));
            Log.d(JHttpClient.class.getSimpleName(), "has not cacheData");
        }
    }


    /****************************************************************************************************************************/

    /**
    * 自定义缓存请求（post）
    *
    * @param context
    * @param url
    * @param params
    * @param parser
    * @param httpCache
    * @param dataCallback
    */
    public static <T> void post(Context context, String url, RequestParams params, BaseParser<T> parser, HttpCache httpCache, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, parser, JHttpClient.POST, httpCache, dataCallback);
    }

    /**
    * 自定义缓存请求（get）
    *
    * @param context
    * @param url
    * @param params
    * @param parser
    * @param httpCache
    * @param callback
    */
    public static <T> void get(Context context, String url, RequestParams params, BaseParser<T> parser, HttpCache httpCache, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, parser, JHttpClient.GET, httpCache, dataCallback);
    }

    /**
    * post 请求有缓存
    *
    * @param context
    * @param url
    * @param params
    * @param parser
    * @param callback
    */
    public static <T> void post(Context context, String url, RequestParams params, BaseParser<T> parser, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, parser, JHttpClient.POST, new DefaultHttpCache(context), dataCallback);
    }

    /**
    * get 請求有缓存
    *
    * @param context
    * @param url
    * @param params
    * @param parser
    * @param callback
    */
    public static <T> void get(Context context, String url, RequestParams params, BaseParser<T> parser, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, parser, JHttpClient.GET, new DefaultHttpCache(context), dataCallback);
    }

    /**
    * get 请求 没有缓存
    *
    * @param context
    * @param url
    * @param params
    * @param parser
    * @param callback
    */
    public static <T> void getFromServer(Context context, String url, RequestParams params, BaseParser<T> parser, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, parser, JHttpClient.GET, null, dataCallback);
    }

    /**
    * post 请求 没有缓存
    *
    * @param context
    * @param url
    * @param params
    * @param parser
    * @param callback
    */
    public static <T> void postFromServer(Context context, String url, RequestParams params, BaseParser<T> parser, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, parser, JHttpClient.POST, null, dataCallback);
    }

    /****************************************************************************************************************************/

    /**
    * 自定义缓存请求（post）
    *
    * @param context
    * @param url
    * @param params
    * @param parser
    * @param httpCache
    * @param callback
    */
    public static <T> void post(Context context, String url, RequestParams params, Class<T> clazz, HttpCache httpCache, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, new JacksonParser<T>(clazz), JHttpClient.POST, httpCache, dataCallback);
    }

    /**
    * 自定义缓存请求（get）
    *
    * @param context
    * @param url
    * @param params
    * @param parser
    * @param httpCache
    * @param callback
    */
    public static <T> void get(Context context, String url, RequestParams params, Class<T> clazz, HttpCache httpCache, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, new JacksonParser<T>(clazz), JHttpClient.GET, httpCache, dataCallback);
    }

    /**
    * post 請求有缓存
    *
    * @param context
    * @param url
    * @param params
    * @param parser
    * @param callback
    */
    public static <T> void post(Context context, String url, RequestParams params, Class<T> clazz, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, new JacksonParser<T>(clazz), JHttpClient.POST, new DefaultHttpCache(context), dataCallback);
    }

    /**
    * get 請求有缓存
    *
    * @param context
    * @param url
    * @param params
    * @param parser
    * @param callback
    */
    public static <T> void get(Context context, String url, RequestParams params, Class<T> clazz, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, new JacksonParser<T>(clazz), JHttpClient.GET, new DefaultHttpCache(context), dataCallback);
    }

    /**
    * get 请求 没有缓存
    *
    * @param context
    * @param url
    * @param params
    * @param parser
    * @param callback
    */
    public static <T> void getFromServer(Context context, String url, RequestParams params, Class<T> clazz, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, new JacksonParser<T>(clazz), JHttpClient.GET, null, dataCallback);
    }

    /**
    * post 请求 没有缓存
    *
    * @param context
    * @param url
    * @param params
    * @param parser
    * @param callback
    */
    public static <T> void postFromServer(Context context, String url, RequestParams params, Class<T> clazz, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, new JacksonParser<T>(clazz), JHttpClient.POST, null, dataCallback);
    }

}
