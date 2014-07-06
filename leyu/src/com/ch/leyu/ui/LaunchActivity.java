
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.preference.Settings;
import com.ch.leyu.utils.VersionUtil;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LaunchActivity extends BaseActivity {

    private Button mStartAPPbtn;
    private TextView mAppVersion;

    @Override
    protected void getExtraParams() {

        //  TODO处理版本更新
    }
    @Override
    protected void loadViewLayout() {
    }

    @Override
    protected void loadfindViewById() {
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processLogic() {
        getSupportActionBar().hide();
        int oldVersion = Settings.getVersionCode();
        int currentVersion = VersionUtil.getVersionCode();
        if (oldVersion == 0) {
            onFirstLaunch();
        } else if (oldVersion < currentVersion) {
            onUpgrade(oldVersion, currentVersion);
        } else {
            gotoMainActivity();
            finish();
        }
    }

    private void onFirstLaunch() {
        showWelcomePage();
        new Thread(){
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Settings.setVersionCode(VersionUtil.getVersionCode());
                gotoMainActivity();
                finish();
            };
        }.start();
    }

    private void showWelcomePage() {
        setContentView(R.layout.launch_layout);
        mStartAPPbtn=(Button) findViewById(R.id.start_app_btn);
//        mAppVersion=(TextView) findViewById(R.id.app_version);

        mStartAPPbtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Settings.getVersionCode() == 0) {
                    // 第一次安装记录激活，升级安装不记录激活

                }
                Settings.setVersionCode(VersionUtil.getVersionCode());
                gotoMainActivity();
                finish();

            }
        });


//        mAppVersion.setText(getResources().getString(R.string.launch_app_version_txt)+VersionUtil.getVersionName());

    }

    private void onUpgrade(int oldVersion, int currentVersion) {
        switch (currentVersion) {
            case 1:
                showWelcomePage();

                break;
            default:
                Settings.setVersionCode(VersionUtil.getVersionCode());
                gotoMainActivity();
                finish();
                break;
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


}

