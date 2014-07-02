
package com.ch.leyu.ui;

import com.ch.leyu.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public abstract class BaseFragment extends Fragment {
    public View mContentView;

    public LayoutInflater mInflater;

    private int mResouce_id = 0;

    private LinearLayout mFragmentContent; // 所有子类的布局都要加载到这个View里面

    protected ViewStub mHttpLoading;

    protected ViewStub mHttpError;

    protected View mHttpLoadingView = null;

    protected View mHttpErrorView = null;

    private  Button mButton ;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = inflater;

        // 加载基类的布局文件
        mFragmentContent = (LinearLayout) inflater.inflate(R.layout.fragment_base, container, false);
        mHttpLoading = (ViewStub) mFragmentContent.findViewById(R.id.viewstub_http_loading);
        mHttpError = (ViewStub) mFragmentContent.findViewById(R.id.viewstub_http_error);
       
        if (mHttpLoading != null) {
            mHttpLoadingView = mHttpLoading.inflate();
            mHttpLoadingView.setVisibility(View.GONE);
        }
        if (mHttpError != null) {
            mHttpErrorView = mHttpError.inflate();
            mHttpErrorView.setVisibility(View.GONE);
        }
        mButton= (Button)mHttpErrorView.findViewById(R.id.loading_lose_btn);
        
        getExtraParams();
        loadViewLayout();
        if (mResouce_id != 0) {
            mContentView = inflater.inflate(mResouce_id, container, false);
            // 将子类的布局加载进来
            mFragmentContent.addView(mContentView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
            findViewById();
            processLogic();
            setListener();
            return mFragmentContent;
        }
       
        
        return super.onCreateView(inflater, container, savedInstanceState);
    }
 
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
       
        mButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                reload();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }
   

    public void setContentView(int resId) {
        this.mResouce_id = resId;
    }

    public void hidden() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen=imm.isActive();
        if(isOpen==true){
            ((InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    

    public View findViewById(int resId) {
        return mContentView.findViewById(resId);
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
    
    /***
     * 加载失败 重新加载
     */
    protected abstract void reload();

}
