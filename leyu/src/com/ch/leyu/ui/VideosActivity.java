
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.PopGridViewAdapter;
import com.ch.leyu.adapter.VideobankPagerAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.TagResponse;
import com.ch.leyu.responseparse.VideoBankResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.view.LYViewPager;
import com.ch.leyu.view.PagerSlidingTabStrip;

import org.apache.http.Header;

import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/***
 * 炉石传说首页--视频库
 * 
 * @author L
 */
public class VideosActivity extends BaseActivity implements OnClickListener {
    private PagerSlidingTabStrip mSlideTabIndicator;

    private LYViewPager mViewPager;

    private Button mButton;

    private VideobankPagerAdapter mVideobankPagerAdapter;

    private ArrayList<TagResponse> mTitleList;
    
    private PopGridViewAdapter mPopAdapter = null;

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
        mButton.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {
        RequestParams params = new RequestParams();
        params.put(Constant.GMAE_ID, 23);
        JHttpClient.get(this, Constant.URL + Constant.VIDEO_URL, params, VideoBankResponse.class,new DataCallback<VideoBankResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers,final VideoBankResponse data) {
                        if (data != null) {
                            mVideobankPagerAdapter = new VideobankPagerAdapter(getSupportFragmentManager(), data);
                            mViewPager.setAdapter(mVideobankPagerAdapter);
                            mSlideTabIndicator.setViewPager(mViewPager);
                            mSlideTabIndicator.setTextSize(24);

                            mTitleList = data.getTags();
                        }
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,Exception exception) {

                    }
                });

    }

    public void showPop() {
        final View popView = LayoutInflater.from(this).inflate(R.layout.popupwindow, null);
        GridView gridView = (GridView) popView.findViewById(R.id.pop_gridview);
      
        if (mTitleList != null) {
            mPopAdapter = new PopGridViewAdapter(mTitleList, this);
        }
        gridView.setAdapter(mPopAdapter);

        final PopupWindow popWindow = new PopupWindow(popView);
        popWindow.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        popWindow.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setFocusable(true);
        popWindow.setTouchable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.showAsDropDown(mButton);
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewPager.setCurrentItem(position);
                popWindow.dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {
        showPop();
        
    }
}
