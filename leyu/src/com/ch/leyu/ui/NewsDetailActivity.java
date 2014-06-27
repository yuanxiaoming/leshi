
package com.ch.leyu.ui;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.api.FrontiaAuthorization.MediaType;
import com.baidu.frontia.api.FrontiaSocialShare;
import com.baidu.frontia.api.FrontiaSocialShare.FrontiaTheme;
import com.baidu.frontia.api.FrontiaSocialShareContent;
import com.baidu.frontia.api.FrontiaSocialShareListener;
import com.ch.leyu.R;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.NewDetailResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.utils.LeUtils;

import org.apache.http.Header;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import android.webkit.WebViewClient;
import android.widget.TextView;

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

    //百度分享
    private FrontiaSocialShare mSocialShare;

    private FrontiaSocialShareContent mImageContent = new FrontiaSocialShareContent();

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
    }

    @Override
    protected void findViewById() {
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
        baiduShareConfig();
        requestData();
    }

    private void requestData() {
        RequestParams params = new RequestParams();
        params.put("id", mCid);
        JHttpClient.get(this, Constant.URL+Constant.ALL_NEWS+Constant.VIDEO_DETAIL, params , NewDetailResponse.class, new DataCallback<NewDetailResponse>() {

            @Override
            public void onStart() {

                mHttpLoadingView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, NewDetailResponse data) {
                if(data!=null){
                    WebSettings settings = mContent.getSettings();
                    // 设置布局算法
                    settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
                    // 设置渲染等级
                    settings.setRenderPriority(RenderPriority.HIGH);
                    // 设置缓存模式
                    settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                    // 设置预览模式
                    settings.setLoadWithOverviewMode(true);
                    // 设置字体缩放级别
                    settings.setTextSize(TextSize.LARGEST);
                    // 设置支持js
                    settings.setJavaScriptEnabled(true);
                    // 设置处理客户端
                    settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
                    settings.setUseWideViewPort(true);
                    settings.setLoadWithOverviewMode(true);

                    mContent.setWebViewClient(new WebViewClient());
                    mContent.setBackgroundColor(Color.parseColor("#F0F0F0"));
                    mContent.loadDataWithBaseURL("", data.getInfo().getContent(), "text/html","UTF-8", "");
                    mTitle.setText(data.getInfo().getTitle());
                    mTime.setText(LeUtils.toDate(data.getInfo().getCreateTime()));


                    String s = "<font color=\"#8F8F8F\">感谢</font> ";
                    String s1 = "<font color=\"#CA4D4D\">"+ data.getInfo().getAuthor() +"</font> ";
                    String s2 = "<font color=\"#8F8F8F\">"+"的投递"+"</font> ";
                    mAuthor.setText(Html.fromHtml(s+s1+s2));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                    Exception exception) {

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
                baiduShare();
                break;
           
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

    private void baiduShare() {
        mSocialShare.show(getWindow().getDecorView(),mImageContent, FrontiaTheme.LIGHT,  new ShareListener());
    }

    private void baiduShareConfig() {
        Frontia.init(this, "ZFkbingwMIo36LV2YrjkCThu");
        mSocialShare = Frontia.getSocialShare();
        mSocialShare.setContext(this);
        mSocialShare.setClientId(MediaType.SINAWEIBO.toString(), "1098403121");
        mSocialShare.setClientId(MediaType.QZONE.toString(), "101069451");
        mSocialShare.setClientId(MediaType.QQFRIEND.toString(), "101069451");
        mSocialShare.setClientId(MediaType.QQWEIBO.toString(), "801517958");
        mSocialShare.setClientId(MediaType.WEIXIN.toString(), "wx3822d16c9c071ef2");
        mSocialShare.setClientName(MediaType.QQFRIEND.toString(), "乐娱互动");
        mImageContent.setTitle("乐娱互动");
        mImageContent.setContent("欢迎使用乐娱互动");
        mImageContent.setLinkUrl("http://www.legames.cn/");
        mImageContent.setImageUri(Uri.parse("http://resource.9377.com/images/cms_style_2012_new/game/hot/game_center_ly.jpg"));
    }

    private class ShareListener implements FrontiaSocialShareListener {

        @Override
        public void onSuccess() {
            Log.d("Test","share success");
        }

        @Override
        public void onFailure(int errCode, String errMsg) {
            Log.d("Test","share errCode "+errCode);
        }

        @Override
        public void onCancel() {
            Log.d("Test","cancel ");
        }

    }

    @Override
    protected void reload() {

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
