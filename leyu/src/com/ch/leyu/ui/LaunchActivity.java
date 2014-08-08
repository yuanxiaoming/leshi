
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.utils.VersionUtil;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LaunchActivity extends BaseActivity {

    private TextView mAppVersion;

    private ImageView mImg1, mImg2, mImg3, mImg4, mImg5;

    private ArrayList<View> views = new ArrayList<View>();

    @Override
    protected void getExtraParams() {

        // TODO处理版本更新
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.launch_layout);
    }

    @Override
    protected void loadfindViewById() {
        mImg1 = (ImageView) findViewById(R.id.first_page_bg);
        mImg2 = (ImageView) findViewById(R.id.c_page_bg);
        mImg3 = (ImageView) findViewById(R.id.t_page_bg);
        mImg4 = (ImageView) findViewById(R.id.four_page_bg);
        mImg5 = (ImageView) findViewById(R.id.fire_page_bg);
        mAppVersion = (TextView) findViewById(R.id.txt_version);
        views.add(mImg1);
        views.add(mImg2);
        views.add(mImg3);
        views.add(mImg4);
        views.add(mImg5);

    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processLogic() {
        getSupportActionBar().hide();
        mAppVersion.setText("出品" + getResources().getString(R.string.launch_app_version_txt)+ VersionUtil.getVersionName());
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                    gotoMainActivity();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();

        for (int i = 0; i < views.size(); i++) {
            handleAlphaEffect(i);
        }

    }

    private void gotoMainActivity() {
        Intent intent = getIntent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void reload() {

    }

    /*
     * 监听是否播放状态
     */
    class EffectAnimationListener implements AnimationListener {
        @Override
        public void onAnimationEnd(Animation animation) {
            
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }
    }

    private void handleAlphaEffect(int position) {
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.alpha);
        anim.setAnimationListener(new EffectAnimationListener());
//        anim.setStartOffset(position*1000);
        views.get(position).startAnimation(anim);
    }

}
