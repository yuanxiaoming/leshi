package com.ch.leyu.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ch.leyu.R;

public class ProgressDialog extends BaseDialog {
	private TextView mTxtProgressDialog ;
	private String mDialogContent = "正在加载...";
	
	public void setProgressDialogContent(String dialogContent) {
		mTxtProgressDialog.setText(dialogContent);
	}

	public ProgressDialog(Context context) {
		super(context, false);
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	protected void findViewById() {
		mTxtProgressDialog = (TextView) findViewById(R.id.txt_progress_dialog);
	}

	@Override
	protected void processLogic() {
		mTxtProgressDialog.setText(mDialogContent);
	}

	@Override
	protected void setListener() {
	}

	@Override
	protected void loadLayout() {
		setContentView(R.layout.dialog_progress);
	}
}
