
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.application.ExitAppUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public abstract class BaseActivity extends ActionBarActivity implements OnClickListener {
    protected FragmentActivity mContext;

    protected ActionBar mActionBar;

    private LinearLayout mActivityContent; // 将子类的布局加载进来

    private View mChildView;

    protected ViewStub mViewStubProgress, mViewStubError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.setContentView(R.layout.activity_base);
        mActivityContent = (LinearLayout) super.findViewById(R.id.activity_content_base);
        mViewStubProgress = (ViewStub) super.findViewById(R.id.viewstub_http_loading);
        mViewStubError = (ViewStub) super.findViewById(R.id.viewstub_http_error);
        mContext = this;
        mActionBar = getSupportActionBar();
        mActionBar.setIcon(android.R.drawable.sym_action_chat);
        initView();
        ExitAppUtils.getInstance().addActivity(this);

    }

    private void initView() {
        getExtraParams();
        loadViewLayout();
        findViewById();
        processLogic();
        setListener();

    }

    /**
     * 要将子类的布局文件加载进BaseActivity布局文件中
     */
    public void setContentView(int layoutId) {
        mChildView = LayoutInflater.from(this).inflate(layoutId, null);
        mActivityContent.addView(mChildView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }

    public View findViewById(int viewId) {
        return mChildView.findViewById(viewId);
    }

    /**
     * 初始化控件
     */
    protected abstract void findViewById();

    /**
     * 加载布局
     */
    protected abstract void loadViewLayout();

    /**
     * 处理逻辑
     */
    protected abstract void processLogic();

    /**
     * 设置监听
     */
    protected abstract void setListener();

    /**
     * 参数传递
     */
    protected abstract void getExtraParams();

    public String getResourceString(int resId) {
        return getResources().getString(resId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitAppUtils.getInstance().delActivity(this);

    }

}
