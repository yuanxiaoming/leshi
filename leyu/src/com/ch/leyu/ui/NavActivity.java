
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.utils.CommonUtil;


import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

/***
 * 导航页
 *
 * @author Administrator
 */
public class NavActivity extends BaseActivity implements OnClickListener {

    private RadioButton mHs, mLol, mStar, mNews;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_nav_rb_hs:
                CommonUtil.switchToFragment(mContext, R.id.fragment_content, new HSFragment(), "");
                break;

            case R.id.act_nav_rb_lol:
                CommonUtil.switchToFragment(mContext, R.id.fragment_content, new LOLFragment(), "");
                break;

            case R.id.act_nav_rb_star:
                CommonUtil.switchToFragment(mContext, R.id.fragment_content,
                        new StarGirefFragment(), "");
                break;
            case R.id.act_nav_rb_news:
                CommonUtil
                        .switchToFragment(mContext, R.id.fragment_content, new NewsFragment(), "");
                break;
            default:
                break;
        }
    }

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_nav);

    }

    @Override
    protected void findViewById() {

        mHs = (RadioButton) findViewById(R.id.act_nav_rb_hs);
        mLol = (RadioButton) findViewById(R.id.act_nav_rb_lol);
        mStar = (RadioButton) findViewById(R.id.act_nav_rb_star);
        mNews = (RadioButton) findViewById(R.id.act_nav_rb_news);
    }

    @Override
    protected void setListener() {
        mHs.setOnClickListener(this);
        mLol.setOnClickListener(this);
        mStar.setOnClickListener(this);
        mNews.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {

        CommonUtil.switchToFragment(mContext, R.id.fragment_content, new HSFragment(), "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_feedback:
                intent = new Intent(this,FeedbackActivity.class);
                startActivity(intent);
                break;

            case R.id.action_search:
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
