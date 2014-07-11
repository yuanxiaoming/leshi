
package com.ch.leyu.ui;

import org.apache.http.Header;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.ch.leyu.R;
import com.ch.leyu.html.LeWebviewClient;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.NewDetailResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.utils.TimeUtils;

public class NewsDetailActivity extends BaseActivity {
    /** 标题 */
    private TextView mTitle;

    /** 投稿人 */
    private TextView mAuthor;

    /** 时间 */
    private TextView mTime;

    /** 正文 */
    private WebView mContent;

    private String mCid;

    private String mShareTitle ;

    private String mShareUrl ="http://www.legames.cn/";

    private WebSettings settings;
    
    private String mPhoneModel="";


    @Override
    protected void getExtraParams() {
        Intent intent = getIntent();
        if(intent!=null){
            mCid= intent.getStringExtra(Constant.CID);
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_newsdetail);
        mPhoneModel = android.os.Build.MODEL; 
    }

    @Override
    protected void loadfindViewById() {
        mTitle = (TextView) findViewById(R.id.activity_newsdetail_title);
        mAuthor = (TextView) findViewById(R.id.activity_newsdetail_author);
        mTime = (TextView) findViewById(R.id.activity_newsdetail_time);
        mContent = (WebView) findViewById(R.id.activity_newsdetail_content);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        requestData();
    }

    private void requestData() {
        RequestParams params = new RequestParams();
        params.put("id", mCid);
        JHttpClient.get(this, Constant.URL+Constant.ALL_NEWS+Constant.VIDEO_DETAIL, params , NewDetailResponse.class, new DataCallback<NewDetailResponse>() {

            @Override
            public void onStart() {
            	 mHttpErrorView.setVisibility(View.GONE);
                mHttpLoadingView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, NewDetailResponse data) {
                if(data!=null){
                    mShareTitle = data.getInfo().getTitle();
                    mShareUrl  = data.getInfo().getLinkUrl();
                    settings = mContent.getSettings();
                    settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                    settings.setJavaScriptEnabled(true); // 设置支持javascript脚本
                    settings.setAllowFileAccess(true); // 允许访问文件
                    settings.setRenderPriority(RenderPriority.HIGH);
                    settings.setBuiltInZoomControls(false); // 设置显示缩放按钮
                    settings.setSupportZoom(false); // 支持缩放
                    settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
                    settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
                    settings.setTextSize(TextSize.NORMAL );
                    if(mPhoneModel.equals("MI 3W")||mPhoneModel.equals("MI 3C")||mPhoneModel.equals("MI 3")){
                        settings.setUseWideViewPort(true); 
                        settings.setLoadWithOverviewMode(true); 
                        settings.setTextSize(TextSize.LARGEST );
                    }
                    
                    final int textSize = (int)getResources().getDimension(R.dimen.web_textsize);
                    String  webTextContext = "<html><body style="+"background-color:"+"#f0f0f0;"+"line-height:30px"+";font-size:"+textSize+"px>" +data.getInfo().getContent()
                            + "</body></html>";
                    mContent.setBackgroundColor(Color.parseColor("#F0F0F0"));
                    mContent.loadDataWithBaseURL("file:///", webTextContext, "text/html","UTF-8", "");
                    mContent.setWebViewClient(new LeWebviewClient());
                    mTitle.setText(data.getInfo().getTitle());
                    mTime.setText(TimeUtils.toDate(data.getInfo().getCreateTime()));

                    String s = "<font color=\"#8F8F8F\">感谢</font> ";
                    String s1 = "<font color=\"#CA4D4D\">"+ data.getInfo().getAuthor() +"</font> ";
                    String s2 = "<font color=\"#8F8F8F\">"+"的投递"+"</font> ";
                    mAuthor.setText(Html.fromHtml(s+s1+s2));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                    Exception exception) {
            	mHttpErrorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                mHttpLoadingView.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                showShare();
                break;

            case android.R.id.home:
                finish();
                return true ;
            default:
                break;
        }

        return true;
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字
        oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(mShareUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mShareTitle+"地址:"+mShareUrl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//      oks.setImagePath("/sdcard/test.jpg");
        oks.setImageUrl("http://www.legames.cn/templates/index2014/images/logo.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(mShareUrl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//      oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(mShareUrl);

        // 启动分享GUI
        oks.show(this);
   }


    @Override
    protected void reload() {
    	requestData();
    }


    @Override
    protected void onStart() {
        super.onStart();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("新闻资讯");
        actionBar.setLogo(R.drawable.legames_back);
        actionBar.setHomeButtonEnabled(true);
        }
    }
