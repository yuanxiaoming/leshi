
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.StarListAdapter;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.StarResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.xlistview.XListView;

import org.apache.http.Header;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/***
 * 首页--明星解说
 * 
 * @author L
 */
public class StarGirefFragment extends BaseFragment {

    private XListView mXListView;

    private StarListAdapter mAdapter;

    private String uid;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_stargiref);

    }

    @Override
    protected void findViewById() {
        mXListView = (XListView) findViewById(R.id.star_xistview);

    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processLogic() {
        JHttpClient.get(getActivity(), Constant.URL + Constant.STAR_URL, null, StarResponse.class,
                new DataCallback<StarResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, final StarResponse data) {
                        mAdapter = new StarListAdapter(getActivity(), data.getUserInfo());
                        mXListView.setAdapter(mAdapter);
                        mXListView.setOnItemClickListener(new OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                                uid = data.getUserInfo().get(position).getUid();
                                Intent intent = new Intent(getActivity(), StarDetailActivity.class);
                                intent.putExtra(Constant.UID, uid);
                                startActivity(intent);
                            }

                        });
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

}
