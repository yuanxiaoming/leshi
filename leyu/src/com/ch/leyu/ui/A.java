
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.utils.CommonUtil;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class A extends BaseActivity {
    private ImageView mImgCover;

    private TextView mTxtArea, mTxtScore, mTxtTotalNumber;

    private RadioButton a, b, c, d;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.rb_main_account:
                CommonUtil.switchToFragment(mContext, R.id.fragment_content, new HSFragment(), "");
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
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_old_main);

    }

    @Override
    protected void findViewById() {

        a = (RadioButton) findViewById(R.id.rb_main_account);
        b = (RadioButton) findViewById(R.id.rb_main_find);
        c = (RadioButton) findViewById(R.id.rb_main_my);
        d = (RadioButton) findViewById(R.id.rb_main_setting);
    }

    @Override
    protected void setListener() {
        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {

        CommonUtil.switchToFragment(mContext, R.id.fragment_content, new HSFragment(), "");
    }

}
