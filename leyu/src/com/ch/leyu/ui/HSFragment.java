
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.HSResponse;
import com.ch.leyu.responseparse.RegisterResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.view.CircleFlowIndicator;
import com.ch.leyu.view.LYGridView;
import com.ch.leyu.view.ViewFlow;

import org.apache.http.Header;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/***
 * 炉石传说首页
 * 
 * @author Administrator
 */
public class HSFragment extends BaseFragment {

    /** 滚动新闻 */
    private ViewFlow mViewFlow;

    /** ViewFlow指示器 */
    private CircleFlowIndicator mIndicator;

    /** 新闻 */
    private ListView mNewsListView;

    /** 大图小编推荐 */
    private ImageView mBigImg1, mBigImg2;

    /** 四张小图小编推荐 */
    private LYGridView mRecommendGrid;

    private LYGridView mHotGrid;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void findViewById() {
        mViewFlow = (ViewFlow) findViewById(R.id.hs_viewflow);
        mIndicator = (CircleFlowIndicator) findViewById(R.id.hs_viewflowindic);
        mNewsListView = (ListView) findViewById(R.id.hs_listview_news);
        mBigImg1 = (ImageView) findViewById(R.id.hs_img_bigRecommend1);
        mBigImg2 = (ImageView) findViewById(R.id.hs_img_bigRecommend2);
        mRecommendGrid = (LYGridView) findViewById(R.id.hs_gridview_recommend);
        mHotGrid = (LYGridView) findViewById(R.id.hs_gridview_hot);

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_hs);

    }

    @Override
    protected void processLogic() {
        JHttpClient.get(getActivity(), Constant.HS_URL, null, HSResponse.class,
                new DataCallback<HSResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, HSResponse data) {
                        Toast.makeText(getActivity(), "---" + data.getCode(), 1000).show();
                    }

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onFinish() {
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,
                            Exception exception) {

                    }

                });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void getExtraParams() {

    }

}
