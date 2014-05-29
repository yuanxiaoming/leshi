
package com.ch.leyu.http.work;

import org.apache.http.Header;

public interface DataCallback<T> {

    /**
     * 请求开始
     */
    public void onStart();

    /**
     * 请求成功回调
     *
     * @param statusCode
     * @param headers
     * @param data
     */
    public void onSuccess(int statusCode, Header[] headers, T data);

    /**
     * 请求失败
     *
     * @param statusCode
     * @param headers
     * @param responseString
     * @param exception
     */
    public void onFailure(int statusCode, Header[] headers, String responseString, Exception exception);

    /**
     * 请求结束
     */
    public void onFinish();
}
