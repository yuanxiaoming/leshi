
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.AutoScrollerPagerAdapter;
import com.ch.leyu.adapter.NewsListAdapter;
import com.ch.leyu.adapter.RecommendGridAdapter;
import com.ch.leyu.adapter.ViewFlowAdapter;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.HSResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.ch.leyu.view.AutoScrollViewPager;
import com.ch.leyu.view.CircleFlowIndicator;
import com.ch.leyu.view.CircleLoopPageIndicator;
import com.ch.leyu.view.LYGridView;
import com.ch.leyu.view.LYViewFlow;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

/***
 * 首页--炉石传说 
 * 
 * 
 * @author L
 */
public class HSFragment extends BaseFragment {

    /** 滚动新闻 */
    private LYViewFlow mViewFlow;

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
    
    private AutoScrollViewPager mAtuoScrollViewPager ;
    
    private CircleLoopPageIndicator mCircleLoopPageIndicator ;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void findViewById() {
        mNewsListView = (ListView) findViewById(R.id.hs_listview_news);
        mBigImg1 = (ImageView) findViewById(R.id.hs_img_bigRecommend1);
        mBigImg2 = (ImageView) findViewById(R.id.hs_img_bigRecommend2);
        mRecommendGrid = (LYGridView) findViewById(R.id.hs_gridview_recommend);
        mHotGrid = (LYGridView) findViewById(R.id.hs_gridview_hot);
        
        mAtuoScrollViewPager = (AutoScrollViewPager) findViewById(R.id.hs_auto_scroll_viewpager);
        mCircleLoopPageIndicator = (CircleLoopPageIndicator) findViewById(R.id.hs_cirle_pageindicator);

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_hs);

    }

    @Override
    protected void processLogic() {

        JHttpClient.get(getActivity(), Constant.URL + Constant.HS_URL, null, HSResponse.class,
                new DataCallback<HSResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, HSResponse data) {
                    	
                    	mAtuoScrollViewPager.startAutoScroll(2000);
                    	AutoScrollerPagerAdapter adapter = new AutoScrollerPagerAdapter(getActivity() , data.getFocus());
                    	mAtuoScrollViewPager.setAdapter(adapter);
                    	mAtuoScrollViewPager.setCurrentItem(data.getFocus().size()*10000);
                    	mCircleLoopPageIndicator.setPageCount(data.getFocus().size());
                    	mCircleLoopPageIndicator.setViewPager(mAtuoScrollViewPager);
                        
                        mNewsListView.setAdapter(new NewsListAdapter(data.getNews(), getActivity()));
                        mRecommendGrid.setAdapter(new RecommendGridAdapter(data.getRecommend(),
                                getActivity()));
                        mHotGrid.setAdapter(new RecommendGridAdapter(data.getHot(), getActivity()));

                        ImageLoader.getInstance().displayImage(
                                data.getBigRecommend().get(0).getImageSrc(), mBigImg1,
                                ImageLoaderUtil.getImageLoaderOptions());
                        ImageLoader.getInstance().displayImage(
                                data.getBigRecommend().get(1).getImageSrc(), mBigImg2,
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
