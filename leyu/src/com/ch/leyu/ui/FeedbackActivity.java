
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.FeedBackResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.view.ClearEditText;

import org.apache.http.Header;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/***
 * 反馈
 * 
 * @author L
 */
public class FeedbackActivity extends BaseActivity {

    public static final String TYPE = "type";

    public static final String DETAIL = "detail";

    public static final String CONTACT = "contact";

    private ClearEditText mDetail_Edit;

    private ClearEditText mPhoneModel_Edit;

    private ClearEditText mTel_Edit;

    private Button mCommit;
    
    private String mDetail;
    
    private String mTel ;
    
    private String mPhoneModel;

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_feedback);
    }

    @Override
    protected void findViewById() {
        mDetail_Edit = (ClearEditText) findViewById(R.id.act_feed_et_detail);
        mPhoneModel_Edit = (ClearEditText) findViewById(R.id.act_feed_et_phone);
        mTel_Edit = (ClearEditText) findViewById(R.id.act_feed_et_contact);
        mCommit = (Button) findViewById(R.id.act_feed_btn_commit);
    }

    @Override
    protected void setListener() {
        mCommit.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if(!mDetail_Edit.getText().toString().equals("")&&!mPhoneModel_Edit.getText().toString().equals("")&&
                        !mTel_Edit.getText().toString().equals("")){
                    mDetail = mDetail_Edit.getText().toString();
                    mPhoneModel = mPhoneModel_Edit.getText().toString();
                    mTel = mTel_Edit.getText().toString();
                    requestData(mPhoneModel, mDetail, mTel);
                }
                else if(mDetail_Edit.getText().toString().equals("")){
                    Toast.makeText(FeedbackActivity.this, R.string.feedback_detail_tips, Toast.LENGTH_SHORT).show();
                }
                else if(mPhoneModel_Edit.getText().toString().equals("")){
                    Toast.makeText(FeedbackActivity.this, R.string.feedback_model_tips, Toast.LENGTH_SHORT).show();
                }
                else if(mTel_Edit.getText().toString().equals("")){
                    Toast.makeText(FeedbackActivity.this, R.string.feedback_phone_tips, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void processLogic() {
        
    }

    public void requestData(String type,String detail,String contact){
        RequestParams params = new RequestParams();
        params.put("action", "add");
        params.put(TYPE, type);
        params.put(DETAIL, detail);
        params.put(CONTACT, contact);
        JHttpClient.post(this, Constant.FEED_BACK, params , FeedBackResponse.class, new DataCallback<FeedBackResponse>() {

            @Override
            public void onStart() {
                mHttpLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, FeedBackResponse data) {
                Toast.makeText(FeedbackActivity.this, R.string.feedback_commit_tips, Toast.LENGTH_LONG).show();
                mDetail_Edit.setText("");
                mPhoneModel_Edit.setText("");
                mTel_Edit.setText("");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                    Exception exception) {
                
            }

            @Override
            public void onFinish() {
                mHttpLoading.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void reload() {
        
    }
}
