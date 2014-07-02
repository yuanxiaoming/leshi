package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.html.LeWebviewClient;

import android.graphics.Color;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;

public class EventActivity extends BaseActivity {
    
    private WebView mWebView;
    private WebSettings settings;

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_event);
    }

    @Override
    protected void findViewById() {
        mWebView = (WebView) findViewById(R.id.activity_event);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        settings = mWebView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setJavaScriptEnabled(true); // 设置支持javascript脚本
//        settings.setTextSize(TextSize.LARGEST);
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setRenderPriority(RenderPriority.HIGH);
        settings.setBuiltInZoomControls(false); // 设置显示缩放按钮
        settings.setSupportZoom(false); // 支持缩放
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setBackgroundColor(Color.parseColor("#F0F0F0"));
        mWebView.setWebViewClient(new LeWebviewClient());
        mWebView.loadUrl("http://www.legames.cn/news-1947.html");

    }

    @Override
    protected void reload() {

    }

}
