package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.LoginResponse;
import com.ch.leyu.utils.Constant;

import org.apache.http.Header;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseActivity {
    
    private EditText mUser ;
    
    private EditText mPassWord;
    
    private Button mLogin;

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

    }

    @Override
    protected void setListener() {
        mLogin.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                String name = mUser.getText().toString();
                String passWord = mPassWord.getText().toString();
                requestData(name, passWord);
            }
        });
    }

    @Override
    protected void processLogic() {
      
    }
    
    private void requestData(String nickName,String passWord){
        RequestParams params = new RequestParams();
        params.put(Constant.NICKNAME, nickName);
        params.put(Constant.PASSWORD, passWord);
        JHttpClient.post(this, Constant.LOGIN, params , LoginResponse.class, new DataCallback<LoginResponse>() {

            @Override
            public void onStart() {
                
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, LoginResponse data) {
                if(data!=null){
                    Intent intent = new Intent(LoginActivity.this, MyZoneActivity.class);
                    intent.putExtra(Constant.DATA, data);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                    Exception exception) {
                
            }

            @Override
            public void onFinish() {
                
            }
        });
    }

    @Override
    protected void reload() {

    }

}
