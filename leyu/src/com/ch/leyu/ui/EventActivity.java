package com.ch.leyu.ui;

import org.apache.http.Header;

import com.ch.leyu.R;
import com.ch.leyu.html.LeWebviewClient;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.NewDetailResponse;
import com.ch.leyu.utils.Constant;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebSettings.TextSize;
import android.widget.TextView;

public class EventActivity extends BaseActivity {

    private WebView mWebView;
    private TextView mTextView;
    private WebSettings settings;
    private String mId;
    private String mTitle ;

    @Override
    protected void getExtraParams() {
    	Intent intent = getIntent();
    	if(intent!=null){
    		mId = intent.getStringExtra(Constant.ID);
    		mTitle = intent.getStringExtra(Constant.TITLE);
    	}
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("赛事专栏");
        actionBar.setLogo(R.drawable.legames_back);
        actionBar.setHomeButtonEnabled(true);
        }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_event);
    }

    @Override
    protected void findViewById() {
        mWebView = (WebView) findViewById(R.id.act_event_web);
        mTextView = (TextView)findViewById(R.id.act_event_title);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
    	mTextView.setText(mTitle);
    	requestData();
    }

    private void requestData(){
            RequestParams params = new RequestParams();
            params.put("id", mId);
            JHttpClient.get(this, Constant.URL+Constant.ALL_NEWS+Constant.VIDEO_DETAIL, params , NewDetailResponse.class, new DataCallback<NewDetailResponse>() {

				@Override
				public void onStart() {
					 mHttpErrorView.setVisibility(View.GONE);
					 mHttpLoadingView.setVisibility(View.VISIBLE);
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,NewDetailResponse data) {
					 if(data!=null){
		                    settings = mWebView.getSettings();
		                    settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		                    settings.setJavaScriptEnabled(true); // 设置支持javascript脚本
		                    settings.setTextSize(TextSize.NORMAL);
//		                    settings.setTextZoom(30);
		                    settings.setAllowFileAccess(true); // 允许访问文件
		                    settings.setRenderPriority(RenderPriority.HIGH);
		                    settings.setBuiltInZoomControls(false); // 设置显示缩放按钮
		                    settings.setSupportZoom(false); // 支持缩放
		                    settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
		                    settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		                    final int textSize = (int)getResources().getDimension(R.dimen.web_textsize);
		                    String  webTextContext = "<html><body style="+"background-color:"+"#f0f0f0;"+"line-height:30px"+";font-size:"+textSize+"px>" +data.getInfo().getContent()
		                            + "</body></html>";
		                    mWebView.setBackgroundColor(Color.parseColor("#F0F0F0"));
		                    mWebView.setWebViewClient(new LeWebviewClient());
		                    mWebView.loadDataWithBaseURL("file:///", webTextContext, "text/html","UTF-8", "");

					 }

				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Exception exception) {
					 mHttpErrorView.setVisibility(View.VISIBLE);
				}

				@Override
				public void onFinish() {
					 mHttpLoadingView.setVisibility(View.GONE);
				}

            });
    }



    @Override
    protected void reload() {
    	requestData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
                return true;

            default:
                break;
        }

        return true;
    }

}
