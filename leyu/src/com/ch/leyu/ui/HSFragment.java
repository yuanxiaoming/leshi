
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.NewsListAdapter;
import com.ch.leyu.adapter.RecommendGridAdapter;
import com.ch.leyu.adapter.ViewFlowAdapter;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.HSResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.ch.leyu.view.CircleFlowIndicator;
import com.ch.leyu.view.LYGridView;
import com.ch.leyu.view.ViewFlow;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

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

    /** Hot */
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
                        mViewFlow.setmSideBuffer(data.getData().getFocus().size());
                        mViewFlow.setFlowIndicator(mIndicator);
                        mViewFlow.setTimeSpan(4000);
                        mViewFlow.setSelection(3 * 1000); // 设置初始位置
                        mViewFlow.startAutoFlowTimer(); // 启动自动播放
                        mViewFlow.setAdapter(new ViewFlowAdapter(getActivity(), data.getData()
                                .getFocus()));
                        mNewsListView.setAdapter(new NewsListAdapter(data.getData().getNews(),
                                getActivity()));
                        mRecommendGrid.setAdapter(new RecommendGridAdapter(data.getData()
                                .getRecommend(), getActivity()));
                        mHotGrid.setAdapter(new RecommendGridAdapter(data.getData().getHot(),
                                getActivity()));

                        ImageLoader.getInstance().displayImage(
                                data.getData().getBigRecommend().get(0).getImageSrc(), mBigImg1,
                                ImageLoaderUtil.getImageLoaderOptions());
                        ImageLoader.getInstance().displayImage(
                                data.getData().getBigRecommend().get(1).getImageSrc(), mBigImg2,
                                ImageLoaderUtil.getImageLoaderOptions());

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
    protected void setListener() {
    }

    @Override
    protected void getExtraParams() {

    }

}
