package com.ch.leyu.http.work;

import org.apache.http.Header;

import com.ch.leyu.http.progress.HttpProgress;

public abstract class ProgressDataCallback<T> implements DataCallback<T> {
    private HttpProgress mProgress;

    public ProgressDataCallback(HttpProgress progress) {
        mProgress = progress;
    }

    @Override
    public void onStart() {
        if (mProgress != null) {
            mProgress.show();
        }
    }
    @Override
    public void onSuccess(int statusCode, Header[] headers, T data) {

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Exception throwable) {
        if (mProgress != null) {
            mProgress.dismiss();
        }
    }

    @Override
    public void onFinish() {
        if (mProgress != null) {
            mProgress.dismiss();
        }
    }

}
