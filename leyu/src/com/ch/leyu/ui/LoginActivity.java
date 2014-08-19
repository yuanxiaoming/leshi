
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.LoginResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.utils.SharedPreferencesUtil;

import org.apache.http.Header;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private EditText mUser;

    private EditText mPassWord;

    private Button mLogin;

    private SharedPreferencesUtil mPreferencesUtil;

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void loadfindViewById() {
        mUser = (EditText) findViewById(R.id.act_et_user);
        mPassWord = (EditText) findViewById(R.id.act_et_password);
        mLogin = (Button) findViewById(R.id.act_btn_login);
        mPreferencesUtil = SharedPreferencesUtil.getInstance(this);
    }

    @Override
    protected void setListener() {
        mLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = mUser.getText().toString();
                String passWord = mPassWord.getText().toString();
                if (name.equals("") || passWord.equals("")) {
                    Toast.makeText(LoginActivity.this, R.string.login_null, Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                requestData(name, passWord);
            }
        });
    }

    @Override
    protected void processLogic() {

    }

    private void requestData(final String nickName, final String passWord) {
        RequestParams params = new RequestParams();
        params.put(Constant.NICKNAME, nickName);
        params.put(Constant.PASSWORD, passWord);
        JHttpClient.post(this, Constant.LOGIN, params, LoginResponse.class,
                new DataCallback<LoginResponse>() {

                    @Override
                    public void onStart() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, LoginResponse data) {
                        hidden();
                        if (data != null) {
                            mPreferencesUtil.putString(Constant.USER, nickName);
                            mPreferencesUtil.putString(Constant.PASSWORD, passWord);
                            mPreferencesUtil.putBoolean(Constant.TAG, true);
                            mPreferencesUtil.putString(Constant.LOGIN_UID, data.getUserInfo()
                                    .getLoginUid());
                            mPreferencesUtil.putString(Constant.AUTH, data.getUserInfo().getAuth());
                            mPreferencesUtil.putString(Constant.PASS_STR, data.getUserInfo()
                                    .getPassStr());
                            mPreferencesUtil.putBoolean(Constant.STAR_TAG, true);
                            Intent intent = new Intent(LoginActivity.this, MyZoneActivity.class);
                            intent.putExtra(Constant.DATA, data);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.login_error,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,
                            Exception exception) {

                    }

                    @Override
                    public void onFinish() {
                        closeProgressDialog();
                    }
                });
    }

    @Override
    protected void reload() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.login);
        actionBar.setLogo(R.drawable.legames_back);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                break;
        }

        return true;
    }

}
