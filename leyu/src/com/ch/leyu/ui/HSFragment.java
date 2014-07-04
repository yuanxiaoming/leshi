
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.BigImgGridAdapter;
import com.ch.leyu.adapter.HeadofAllFragmentPagerAdapter;
import com.ch.leyu.adapter.HotGridAdapter;
import com.ch.leyu.adapter.NewsListAdapter;
import com.ch.leyu.adapter.RecommendGridAdapter;
import com.ch.leyu.application.CLYApplication;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.HSResponse;
import com.ch.leyu.responseparse.Property;
import com.ch.leyu.utils.CommonUtil;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.view.AutoScrollViewPager;
import com.ch.leyu.widget.view.CircleLoopPageIndicator;
import com.ch.leyu.widget.view.CustomScrollView;
import com.ch.leyu.widget.view.LYGridView;

import org.apache.http.Header;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

/***
 * 首页--炉石传说
 *
 * @author L
 */
public class HSFragment extends BaseFragment implements OnClickListener, OnItemClickListener {

    private CustomScrollView mCustomScrollView ;

    /** 新闻 */
    private ListView mNewsListView;

    /** 大图小编推荐 */
    private LYGridView mBigImg;

    /** 四张小图小编推荐 */
    private LYGridView mRecommendGrid;

    /** Hot */
    private LYGridView mHotGrid;

    private AutoScrollViewPager mAtuoScrollViewPager;

    private CircleLoopPageIndicator mCircleLoopPageIndicator;

    /** 赛事专区 */
    private Button mMatch;

    /** 视频库 */
    private Button mVideos;

    /** 高玩攻略 */
    private Button mRaiders;

    private String mId ="";

    private String mTitle = "";


    @Override
    public void onClick(View v) {
        Intent intent = null ;
        switch (v.getId()) {
            case R.id.hs_bt_match:
                intent = new Intent(getActivity(), EventActivity.class);
                intent.putExtra(Constant.ID, mId);
                intent.putExtra(Constant.TITLE, mTitle);
                startActivity(intent);
                break;
            case R.id.hs_bt_videos:
                intent = new Intent(getActivity(), VideosActivity.class);
                startActivity(intent);

                break;
            case R.id.hs_bt_raiders:
                ((CLYApplication)getActivity().getApplication()).setmFlag(false);
                CommonUtil.switchToFragment(getActivity(), R.id.fragment_content, new NewsFragment(), "");
                ((MainActivity)getActivity()).setFoucs(true);
                break;

            default:
                break;
        }
    }

    @Override
    protected void findViewById() {
        mNewsListView = (ListView) findViewById(R.id.hs_listview_news);
        mBigImg = (LYGridView) findViewById(R.id.hs_big_img);
        mRecommendGrid = (LYGridView) findViewById(R.id.hs_gridview_recommend);
        mHotGrid = (LYGridView) findViewById(R.id.hs_gridview_hot);
        mMatch = (Button) findViewById(R.id.hs_bt_match);
        mVideos = (Button) findViewById(R.id.hs_bt_videos);
        mRaiders = (Button) findViewById(R.id.hs_bt_raiders);
        mAtuoScrollViewPager = (AutoScrollViewPager) findViewById(R.id.hs_auto_scroll_viewpager);
        mCircleLoopPageIndicator = (CircleLoopPageIndicator) findViewById(R.id.hs_cirle_pageindicator);
        mCustomScrollView = (CustomScrollView) findViewById(R.id.hs_custom_scrollview);

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_hs);

    }

    @Override
    protected void processLogic() {
        mNewsListView.setEnabled(false);
        requestData();

    }

    private void requestData() {
        JHttpClient.get(getActivity(), Constant.URL + Constant.HS_URL, null, HSResponse.class,
                new DataCallback<HSResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, HSResponse data) {
                        if(data!=null){
                            mAtuoScrollViewPager.startAutoScroll(2000);
                            mAtuoScrollViewPager.setInterval(4000);
                            mCustomScrollView.setAutoScrollViewPager(mAtuoScrollViewPager);
                            mId = data.getSaishi().get(0).getId();
                            mTitle = data.getSaishi().get(0).getTitle();
                            HeadofAllFragmentPagerAdapter adapter = new HeadofAllFragmentPagerAdapter(getActivity(), data.getFocus());
                            mAtuoScrollViewPager.setAdapter(adapter);
                            mAtuoScrollViewPager.setCurrentItem(data.getFocus().size() * 10000);
                            mCircleLoopPageIndicator.setPageCount(data.getFocus().size());
                            mCircleLoopPageIndicator.setViewPager(mAtuoScrollViewPager);
                            mNewsListView.setAdapter(new NewsListAdapter(data.getNews(), getActivity()));
                            mRecommendGrid.setAdapter(new RecommendGridAdapter(data.getRecommend(),getActivity()));
                            mHotGrid.setAdapter(new HotGridAdapter(data.getHot(), getActivity()));
                            mBigImg.setAdapter(new BigImgGridAdapter(data.getBigRecommend(), getActivity()));

                        }

                    }

                    @Override
                    public void onStart() {
                        mHttpErrorView.setVisibility(View.GONE);
                        mHttpLoadingView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        mHttpLoadingView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,
                            Exception exception) {
                        mHttpErrorView.setVisibility(View.VISIBLE);
                    }
                });
    }


    @Override
    protected void setListener() {
        mMatch.setOnClickListener(this);
        mVideos.setOnClickListener(this);
        mRaiders.setOnClickListener(this);
        mRecommendGrid.setOnItemClickListener(this);
        mHotGrid.setOnItemClickListener(this);
        mBigImg.setOnItemClickListener(this);
    }

    @Override
    protected void getExtraParams() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Property item = (Property) parent.getAdapter().getItem(position);
        if(item!=null){
            Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
            intent.putExtra(Constant.CID , item.getId());
            startActivity(intent);
        }

    }

    @Override
    protected void reload() {
        requestData();
    }

}
