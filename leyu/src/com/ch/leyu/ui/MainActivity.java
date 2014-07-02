package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.application.CLYApplication;
import com.ch.leyu.application.ExitAppUtils;
import com.ch.leyu.utils.CommonUtil;
import com.umeng.update.UmengUpdateAgent;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/***
 * 导航页
 *
 * @author L
 */
public class MainActivity extends BaseActivity implements OnClickListener {

	private RadioButton mHs, mLol, mStar, mNews;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_nav_rb_hs:
			CommonUtil.switchToFragment(mContext, R.id.fragment_content,
					new HSFragment(), "");
			break;

		case R.id.act_nav_rb_lol:
			CommonUtil.switchToFragment(mContext, R.id.fragment_content,
					new LOLFragment(), "");
			break;

		case R.id.act_nav_rb_star:
			CommonUtil.switchToFragment(mContext, R.id.fragment_content,
					new StarGirefFragment(), "");
			break;
		case R.id.act_nav_rb_news:
			((CLYApplication) this.getApplication()).setmFlag(true);
			CommonUtil.switchToFragment(mContext, R.id.fragment_content,
					new NewsFragment(), "");
			break;
		default:
			break;
		}
	}

	public void setFoucs(boolean isChecked) {
		mNews.setChecked(isChecked);
	}

	@Override
	protected void getExtraParams() {
		mActionBar.setDisplayShowHomeEnabled(false);
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

		CommonUtil.switchToFragment(mContext, R.id.fragment_content,
				new HSFragment(), "");
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
		case R.id.action_search:
			intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
			break;

		case R.id.action_feedback:
			intent = new Intent(this, FeedbackActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void reload() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			exitBy2Click();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean sExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (sExit == false) {
			sExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					sExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			ExitAppUtils.getInstance().exit();
		}
	}
}