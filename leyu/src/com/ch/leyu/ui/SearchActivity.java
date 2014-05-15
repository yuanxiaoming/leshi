
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

import android.view.View;
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
public class SearchActivity extends BaseActivity implements OnItemClickListener {

    /** 删除历史搜索内容 */
    private ImageView mDelete;

    /** 输入内容 */
    private EditText mDetail;

    /** 热门搜索，历史搜索 */
    private GridView mHots, mHistory;

    /** 没有搜索结果 */
    private TextView mResult;

    /** 搜索 */
    private Button mSearch;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_seacrh_img_del:

                break;
            case R.id.act_search_bt_search:
                String keyWord = mDetail.getText().toString();
                RequestParams params = new RequestParams();
                params.put(Constant.KEYWORD, keyWord);

                JHttpClient.get(this, Constant.URL + Constant.SEARCH, params,VideoSearchResponse.class, new DataCallback<VideoSearchResponse>() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers,VideoSearchResponse data) {
                                if(data!=null){
                                    Toast.makeText(mContext, "搜索到"+data.getTotalPage()+"相关的资料", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(mContext, "搜索不到相关的资料", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onFinish() {

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers,String responseString, Exception exception) {
                                Toast.makeText(mContext, "搜索不到相关的资料", Toast.LENGTH_LONG).show();
                            }
                        });
                break;
        }
    }

    @Override
    protected void getExtraParams() {

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
        mDelete.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mHots.setOnItemClickListener(this);
        mHistory.setOnItemClickListener(this);
    }

    @Override
    protected void processLogic() {

        JHttpClient.get(this, Constant.URL + Constant.HOT_SEARCH, null, SearchResponse.class,
                new DataCallback<SearchResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, SearchResponse data) {
                        mHots.setAdapter(new SearchAdapter(SearchActivity.this, data.getHot()));
                    }

                    @Override
                    public void onStart() {
                        mHttpLoadingView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        mHttpLoadingView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,
                            Exception exception) {

                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub

    }

}
