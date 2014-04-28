package com.ch.leyu.ui;

import org.apache.http.Header;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.leyu.R;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.RegisterResponse;
import com.ch.leyu.utils.CommonUtil;
import com.ch.leyu.utils.Constant;

public class A extends BaseActivity {
	private ImageView mImgCover;
	private TextView mTxtArea, mTxtScore, mTxtTotalNumber;
	private RadioButton a, b, c, d;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rb_main_account:
			CommonUtil.switchToFragment(mContext, R.id.fragment_content, new B(), "");
			break;

		case R.id.rb_main_find:
			CommonUtil.switchToFragment(mContext, R.id.fragment_content, new C(), "");
			break;

		case R.id.rb_main_my:
			CommonUtil.switchToFragment(mContext, R.id.fragment_content, new D(), "");
			break;
		case R.id.rb_main_setting:
			CommonUtil.switchToFragment(mContext, R.id.fragment_content, new E(), "");
			break;
		default:
			break;
		}
	}

	@Override
	protected void findViewById() {
		// mImgCover = (ImageView) findViewById(R.id.img_login_cover);
		// mTxtArea = (TextView) findViewById(R.id.txt_login_area);
		// mTxtScore = (TextView) findViewById(R.id.txt_login_score);
		// mTxtTotalNumber = (TextView)
		// findViewById(R.id.txt_login_totalnumber);

		a = (RadioButton) findViewById(R.id.rb_main_account);
		b = (RadioButton) findViewById(R.id.rb_main_find);
		c = (RadioButton) findViewById(R.id.rb_main_my);
		d = (RadioButton) findViewById(R.id.rb_main_setting);
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_old_main);

	}

	@Override
	protected void processLogic() {
		JHttpClient.getFromServer(mContext, Constant.A_URL, null, RegisterResponse.class, new DataCallback<RegisterResponse>() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, RegisterResponse data) {
				Toast.makeText(mContext, data.getCount() + "", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onStart() {

			}

			@Override
			public void onFinish() {

			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

			}

		});

		CommonUtil.switchToFragment(mContext, R.id.fragment_content, new B(), "");
	}

	@Override
	protected void setListener() {
		a.setOnClickListener(this);
		b.setOnClickListener(this);
		c.setOnClickListener(this);
		d.setOnClickListener(this);
	}

	@Override
	protected void getExtraParams() {
		// TODO Auto-generated method stub

	}

}
