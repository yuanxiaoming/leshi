package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.ListAsGridBaseAdapter.GridItemClickListener;
import com.ch.leyu.adapter.ListChangeGridAdapter;
import com.ch.leyu.http.httplibrary.RequestParams;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.StarDetailResponse;
import com.ch.leyu.responseparse.VideoListResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.xlistview.XListView;

import org.apache.http.Header;

import android.content.Intent;
import android.view.View;

/**
 * 明星视频--最新上传
 *
 * @author L
 */
public class NewVideoFragment extends BaseFragment implements
		GridItemClickListener {

	private XListView mXListView;

	private String uid;

	private ListChangeGridAdapter mAdapter;

	private VideoListResponse mResponse;

	@Override
	protected void getExtraParams() {
		uid = ((StarDetailActivity) getActivity()).getUid();
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.fragment_newvideo);
	}

	@Override
	protected void findViewById() {
		mXListView = (XListView) findViewById(R.id.starviedo_fragment_xlistview);
		mAdapter = new ListChangeGridAdapter(null, getActivity());
	}

	@Override
	protected void setListener() {
		mAdapter.setOnGridClickListener(this);
	}

	@Override
	protected void processLogic() {
		mAdapter.setNumColumns(2);
		mXListView.setAdapter(mAdapter);
		RequestParams params = new RequestParams();
		params.put(Constant.UID, uid);
		JHttpClient.get(getActivity(), Constant.URL + Constant.STAR_DETAIL,
				params, StarDetailResponse.class,
				new DataCallback<StarDetailResponse>() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							StarDetailResponse data) {
						if (data != null) {
							mResponse = data.getVideoList();
							mAdapter.addArrayList(data.getVideoList().getData());
						}

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
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Exception exception) {

					}
				});
	}

	@Override
	public void onGridItemClicked(View v, int position, long itemId) {
		if (mResponse != null) {
			String uId = mResponse.getData().get(position).getId();
			Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
			intent.putExtra(Constant.CID, uId);
			startActivity(intent);
		}

	}

}
