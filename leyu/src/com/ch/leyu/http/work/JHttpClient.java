package com.ch.leyu.http.work;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

    private static <T> void getServerData(Context context, String url, RequestParams params, BaseParser<T> parser, String requestMethod, HttpCache httpCache, final DataCallback<T> dataCallback) {
        String cacheUrl = JHttpClient.getUrlWithQueryString(url, params).concat(requestMethod); // The
        // only
        // URL
        if (parser == null) {
            try {
                throw new RuntimeException("BaseParse equals to null ,Please send a data parser");
            } catch (Exception e) {
                Log.i(JHttpClient.class.getSimpleName(), e.toString());
            }
        } else {
            if(!NetWorkUtil.isConnected(context)){
              Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
              getCache(context, parser, dataCallback, cacheUrl);
              return;
            }
            boolean hasCache = false;
            if (httpCache != null) {
                hasCache = httpCache.getHttpCache(cacheUrl);
            }
            if (hasCache) {
                getCache(context, parser, dataCallback, cacheUrl);
            } else {
                if (requestMethod != null && requestMethod.equals(GET)) {
                    JHttpClient.get(url, params, httpCacheResponseHandler(context, parser, httpCache, dataCallback, cacheUrl));
                } else if (requestMethod != null && requestMethod.equals(POST)) {
                    JHttpClient.post(url, params, httpCacheResponseHandler(context, parser, httpCache, dataCallback, cacheUrl));
                } else {
                    try {
                        throw new RuntimeException("requestMethod equals to null ,Please specify a request method name");
                    } catch (Exception e) {
                        Log.i(JHttpClient.class.getSimpleName(), e.toString());
                    }
                }
            }
        }
    }

    private static <T> void getCache(Context context, BaseParser<T> parser, final DataCallback<T> dataCallback, String cacheUrl) {
        ServerDataCache cache = DBOpenHelperManager.getInstance(context).findCacheByUrl(cacheUrl);
        if(cache != null){
            T data = null;
            try {
                data = parser.parse(cache.getServerData());
                dataCallback.onSuccess(200, null, data);
                Log.i(JHttpClient.class.getSimpleName(), "Data taken from the cache");
            } catch (Exception e) {
                dataCallback.onFailure(200, null, cache.getServerData(), new JSONException("josn解析异常"));
            }

        }else{
            dataCallback.onFailure(500, null, "没有缓存", new JSONException("josn解析异常"));
            Log.i(JHttpClient.class.getSimpleName(), "has not cache");
        }
    }

    private static <T> JAsyncHttpResponseHandler<T> httpCacheResponseHandler(final Context context, final BaseParser<T> parser, final HttpCache httpCache, final DataCallback<T> dataCallback,
            final String cacheUrl) {
        return new JAsyncHttpResponseHandler<T>(context, parser, cacheUrl, httpCache) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, T data) {
                if (statusCode == 200) {
                    dataCallback.onSuccess(statusCode, headers, data);
                    Log.i(JHttpClient.class.getSimpleName(), "Data taken from the server");
                }
            }

            @Override
            public void onStart() {
                dataCallback.onStart();
            }

            @Override
            public void onFinish() {
                dataCallback.onFinish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Exception e) {
                // 请求失败 返回缓存数据
                if (httpCache != null) {
                    getCache(context, parser, dataCallback, cacheUrl);
                }else{
                    dataCallback.onFailure(statusCode, headers, responseBody.toString(), e);

                }
            }
        };
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
    public static <T> void post(Context context, String url, RequestParams params, BaseParser<T> parser, HttpCache httpCache, final DataCallback<T> callback) {
        getServerData(context, url, params, parser, JHttpClient.POST, httpCache, callback);
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
    public static <T> void get(Context context, String url, RequestParams params, BaseParser<T> parser, HttpCache httpCache, final DataCallback<T> callback) {
        getServerData(context, url, params, parser, JHttpClient.GET, httpCache, callback);
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
    public static <T> void post(Context context, String url, RequestParams params, BaseParser<T> parser, final DataCallback<T> callback) {
        getServerData(context, url, params, parser, JHttpClient.POST, new DefaultHttpCache(context), callback);
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
    public static <T> void get(Context context, String url, RequestParams params, BaseParser<T> parser, final DataCallback<T> callback) {
        getServerData(context, url, params, parser, JHttpClient.GET, new DefaultHttpCache(context), callback);
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
    public static <T> void getFromServer(Context context, String url, RequestParams params, BaseParser<T> parser, final DataCallback<T> callback) {
        getServerData(context, url, params, parser, JHttpClient.GET, null, callback);
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
    public static <T> void postFromServer(Context context, String url, RequestParams params, BaseParser<T> parser, final DataCallback<T> callback) {
        getServerData(context, url, params, parser, JHttpClient.POST, null, callback);
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
    public static <T> void post(Context context, String url, RequestParams params, Class<T> clazz, HttpCache httpCache, final DataCallback<T> callback) {
        getServerData(context, url, params, new JacksonParser<T>(clazz), JHttpClient.POST, httpCache, callback);
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
    public static <T> void get(Context context, String url, RequestParams params, Class<T> clazz, HttpCache httpCache, final DataCallback<T> callback) {
        getServerData(context, url, params, new JacksonParser<T>(clazz), JHttpClient.GET, httpCache, callback);
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
    public static <T> void post(Context context, String url, RequestParams params, Class<T> clazz, final DataCallback<T> callback) {
        getServerData(context, url, params, new JacksonParser<T>(clazz), JHttpClient.POST, new DefaultHttpCache(context), callback);
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
    public static <T> void get(Context context, String url, RequestParams params, Class<T> clazz, final DataCallback<T> callback) {
        getServerData(context, url, params, new JacksonParser<T>(clazz), JHttpClient.GET, new DefaultHttpCache(context), callback);
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
    public static <T> void getFromServer(Context context, String url, RequestParams params, Class<T> clazz, final DataCallback<T> callback) {
        getServerData(context, url, params, new JacksonParser<T>(clazz), JHttpClient.GET, null, callback);
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
    public static <T> void postFromServer(Context context, String url, RequestParams params, Class<T> clazz, final DataCallback<T> callback) {
        getServerData(context, url, params, new JacksonParser<T>(clazz), JHttpClient.POST, null, callback);
    }

}
