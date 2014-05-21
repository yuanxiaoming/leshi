
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.SearchAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.SearchResponse;
import com.ch.leyu.responseparse.VideoSearchResponse;
import com.ch.leyu.utils.Constant;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/***
 * 搜索activity
 *
 * @author L
 */
public class SearchActivity extends BaseActivity {

    /** 删除历史搜索内容 */
    private ImageView mDelete;

    /** 输入内容 */
    private EditText mDetail;

    /** 热门搜索，历史记录 */
    private GridView mHots, mHistory;

    /** 没有搜索结果 */
    private TextView mResult;

    /** 搜索 */
    private Button mSearch;

    private  String mKeyWord="";

    private Context mContext;

    @Override
    protected void getExtraParams() {
        mContext=this;

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void findViewById() {
        mHistory = (GridView) findViewById(R.id.act_search_gd_history);
        mHots = (GridView) findViewById(R.id.act_search_gd_hots);
        mDetail = (EditText) findViewById(R.id.act_search_et_detail);
        mDelete = (ImageView) findViewById(R.id.act_seacrh_img_del);
        mResult = (TextView) findViewById(R.id.act_search_tv_result);
        mSearch = (Button) findViewById(R.id.act_search_bt_search);
    }

    @Override
    protected void setListener() {
        mDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });

        mSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mKeyWord = mDetail.getText().toString();
                if(TextUtils.isEmpty(mKeyWord)){
                    Toast.makeText(mContext, "请输入搜索关键字", Toast.LENGTH_LONG).show();
                }else{
                    RequestParams params = new RequestParams();
                    params.put(Constant.KEYWORD, mKeyWord);
                    JHttpClient.get(mContext, Constant.URL + Constant.SEARCH, params,VideoSearchResponse.class,mSearchDataCallback );
                }
            }
        });
        mHots.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }


        });
        mHistory.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



            }



        });
    }

    @Override
    protected void processLogic() {

        JHttpClient.get(mContext, Constant.URL + Constant.HOT_SEARCH, null, SearchResponse.class,mHotSearchDataCallback);
    }



    /*
     * 关键字搜索
     */
    DataCallback<VideoSearchResponse> mSearchDataCallback=new DataCallback<VideoSearchResponse>(){

        @Override
        public void onStart() {
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, VideoSearchResponse data) {

            if (data != null) {
                Intent intent = new Intent(mContext,SearchListActivity.class);
                intent.putExtra("result", data);
                intent.putExtra(Constant.KEYWORD, mKeyWord);
                startActivity(intent);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Exception exception) {


        }

        @Override
        public void onFinish() {

            mHistory.setVisibility(View.GONE);
            mResult.setVisibility(View.VISIBLE);

        }


    };



    /**
     *热门搜索
     */
    DataCallback<SearchResponse> mHotSearchDataCallback=new DataCallback<SearchResponse>(){

        @Override
        public void onStart() {
            mHttpLoadingView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, SearchResponse data) {
            if(data!=null){
                mHots.setAdapter(new SearchAdapter(mContext, data.getHot()));
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Exception exception) {
        }

        @Override
        public void onFinish() {
            mHttpLoadingView.setVisibility(View.GONE);
        }


    };

}
