package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.R.id;
import com.ch.leyu.R.layout;

import android.view.View;
import android.widget.TextView;

public class E extends BaseFragment {
	private TextView textView;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void findViewById() {
		// TODO Auto-generated method stub
		textView = (TextView) findViewById(R.id.textView1);
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.fragment_x);

	}

	@Override
	protected void processLogic() {
		// TODO Auto-generated method stub
		textView.setText("wo shi e");
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void getExtraParams() {
		// TODO Auto-generated method stub

	}

}
