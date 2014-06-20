
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.application.ExitAppUtils;
import com.ch.leyu.widget.view.CustomProgressDialog;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public abstract class BaseActivity extends ActionBarActivity{
    protected FragmentActivity mContext;

    protected ActionBar mActionBar;

    private LinearLayout mActivityContent; // 将子类的布局加载进来

    private View mChildView;

    protected ViewStub mHttpLoading, mHttpError;
    protected View mHttpLoadingView=null;
    protected View mHttpErrorView=null;
    protected CustomProgressDialog mProgressDialog;

    public Button mButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        mActivityContent = (LinearLayout) super.findViewById(R.id.activity_content_base);
        mHttpLoading = (ViewStub) super.findViewById(R.id.viewstub_http_loading);
        mHttpError = (ViewStub) super.findViewById(R.id.viewstub_http_error);
        mButton = (Button) mHttpError.findViewById(R.id.loading_lose_btn);
        if(mHttpLoading!=null){
            mHttpLoadingView=mHttpLoading.inflate();
            mHttpLoadingView.setVisibility(View.GONE);
        }
        if(mHttpError!=null){
            mHttpErrorView=mHttpError.inflate();
            mHttpErrorView.setVisibility(View.GONE);
        }
        mContext = this;
        mActionBar = getSupportActionBar();
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


    public String getResourceString(int resId) {
        return getResources().getString(resId);
    }

    public void hidden(){
      //隐藏键盘
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(mContext.getCurrentFocus().
                        getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 参数传递
     */
    protected abstract void getExtraParams();

    /**
     * 加载布局
     */
    protected abstract void loadViewLayout();
    /**
     * 初始化控件
     */
    protected abstract void findViewById();

    /**
     * 设置监听
     */
    protected abstract void setListener();

    /**
     * 处理逻辑
     */
    protected abstract void processLogic();




    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitAppUtils.getInstance().delActivity(this);

    }

    /**
     * @return void 返回类型
     * @Title: showProgressDialog(设置进度条)
     * @author xiaoming.yuan
     * @data 2013-7-12 下午10:09:36
     */
    protected void showProgressDialog() {
        if ((!isFinishing()) && this.mProgressDialog == null) {
            this.mProgressDialog = new CustomProgressDialog(this);
        }
        // 设置ProgressDialog 的进度条style
        this.mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // this.mProgressDialog.setTitle("提示");
        this.mProgressDialog.setMessage("加载中...");
        // 设置ProgressDialog 的进度条是否不明确
        this.mProgressDialog.setIndeterminate(false);
        // 设置ProgressDialog 的进度条是否不明确
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.setCanceledOnTouchOutside(false);
        this.mProgressDialog.show();
    }

    /**
     * @return void 返回类型
     * @Title: closeProgressDialog(关闭进度条)
     * @author xiaoming.yuan
     * @data 2013-7-12 下午10:09:30
     */
    protected void closeProgressDialog() {
        if (this.mProgressDialog != null && this.mProgressDialog.isShowing())
            this.mProgressDialog.dismiss();
    }

}
