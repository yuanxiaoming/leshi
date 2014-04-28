package com.ch.leyu.http.work;

import org.apache.http.Header;

public interface DataCallback<T> {

	public void onSuccess(int statusCode, Header[] headers, T data);

	public void onStart();

	public void onFinish();

	public void onFailure(int statusCode, Header[] headers, String responseString, Exception exception);
}