
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.VideobankPagerAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.VideoBankResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.view.LYViewPager;
import com.ch.leyu.view.PagerSlidingTabStrip;

import org.apache.http.Header;

import android.widget.Button;

/***
 * 炉石传说首页--视频库
 * 
 * @author L
 */
public class VideosActivity extends BaseActivity {
    private PagerSlidingTabStrip mSlideTabIndicator;

    private LYViewPager mViewPager;

    private Button mButton;

    private VideobankPagerAdapter mVideobankPagerAdapter;

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_videos);
    }

    @Override
    protected void findViewById() {
        mSlideTabIndicator = (PagerSlidingTabStrip) findViewById(R.id.act_videos_tabstrip);
        mViewPager = (LYViewPager) findViewById(R.id.act_videos_viewpager);
        mButton = (Button) findViewById(R.id.act_videos_bt);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        RequestParams params = new RequestParams();
        params.put(Constant.GMAE_ID, 23);
        JHttpClient.get(this, Constant.URL + Constant.VEDIO_URL, params, VideoBankResponse.class,
                new DataCallback<VideoBankResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                            final VideoBankResponse data) {
                        if (data != null) {
                            mVideobankPagerAdapter = new VideobankPagerAdapter(
                                    getSupportFragmentManager(), data);
                            mViewPager.setAdapter(mVideobankPagerAdapter);

                            mSlideTabIndicator.setViewPager(mViewPager);
                            mSlideTabIndicator.setTextSize(24);

                        }
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,
                            Exception exception) {

                    }
                });

    }

}
