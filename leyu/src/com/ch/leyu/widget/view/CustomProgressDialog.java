/**
 * @Title: CustomProgressDialog.java
 * @Package com.sanqi.android.sdk.widget
 * Copyright: Copyright (c) 2013
 * Company:上海三七玩网络科技有限公司
 * @author xiaoming.yuan
 * @date 2013-10-8 上午11:51:05
 * @version V1.0
 */

package com.ch.leyu.widget.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

/**
 * @ClassName: CustomProgressDialog
 * @author xiaoming.yuan
 * @date 2013-10-8 上午11:51:05
 */

public class CustomProgressDialog extends ProgressDialog {
    private static final String TAG = "CustomProgressDialog";

    private Context context;

    private ProgressBar mProgressBar;

    private FrameLayout.LayoutParams mLayoutParams;

    public CustomProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressBar = new ProgressBar(context);
        mProgressBar.setIndeterminate(false);
        mProgressBar.setInterpolator(context, android.R.anim.linear_interpolator);
        mLayoutParams = new FrameLayout.LayoutParams(android.widget.FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        setContentView(mProgressBar, mLayoutParams);
    }

}
