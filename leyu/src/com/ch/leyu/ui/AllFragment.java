
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.AllViewFlowAdapter;
import com.ch.leyu.adapter.CLYAdapter;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.RegisterResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.view.CircleFlowIndicator;
import com.ch.leyu.view.ViewFlow;
import com.ch.leyu.widget.xlistview.XListView;
import com.ch.leyu.widget.xlistview.XListView.IXListViewListener;

import org.apache.http.Header;

import android.view.LayoutInflater;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 新闻资讯---全部Fragment
 *
 * @author liu
 */
public class AllFragment extends BaseFragment {

    private ViewFlow mViewFlow;

    private XListView mXListView;

    private CircleFlowIndicator mIndicator;

    private CLYAdapter mAdapter;

    private SimpleDateFormat mSimpleDateFormat;

    //ListView 要加入的头部View
    private View mListViewHeaderView ;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_all);
        mListViewHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.viewflow, null);
    }

    @Override
    protected void findViewById() {
        mViewFlow = (ViewFlow) mListViewHeaderView.findViewById(R.id.all_viewflow);
        mIndicator = (CircleFlowIndicator) mListViewHeaderView.findViewById(R.id.all_viewflowindic);
        mXListView = (XListView) findViewById(R.id.all_listview_cly);

        mViewFlow.setAdapter(new AllViewFlowAdapter(getActivity()));
        mViewFlow.setmSideBuffer(3); // 实际图片张数， 我的ImageAdapter实际图片张数为3
        mViewFlow.setFlowIndicator(mIndicator);
        mViewFlow.setTimeSpan(3000);
        mViewFlow.setSelection(3 * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放

        mXListView.addHeaderView(mListViewHeaderView);
        mXListView.setPullRefreshEnable(true);
        mXListView.setPullLoadEnable(true);
        mXListView.setXListViewListener(mIXListViewListenerImp);
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        mXListView.setRefreshTime(mSimpleDateFormat.format(new Date()));

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
    	 

    }

	private void loadData() {
		JHttpClient.get(getActivity(), Constant.A_URL, null, RegisterResponse.class,
                new DataCallback<RegisterResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, RegisterResponse data) {
                        mAdapter = new CLYAdapter(getActivity(), data.getList());
                        mXListView.setAdapter(mAdapter);
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

    private XListView.IXListViewListener mIXListViewListenerImp = new IXListViewListener() {
        // 下拉刷新
        @Override
        public void onRefresh() {

        }

        // 上拉加载
        @Override
        public void onLoadMore() {

        }
    };


    public void setUserVisibleHint(boolean isVisibleToUser) {
    	if(isVisibleToUser){
    		loadData();
    	}
    };
}
