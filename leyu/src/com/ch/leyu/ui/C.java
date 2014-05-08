
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.StarListAdapter;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.RegisterResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.xlistview.XListView;

import org.apache.http.Header;

import android.view.View;

public class C extends BaseFragment {

    private XListView mXListView;

    private StarListAdapter mAdapter;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void findViewById() {
        mXListView = (XListView) findViewById(R.id.listview_c_cly);


    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_c);

    }

    @Override
    protected void processLogic() {
        JHttpClient.get(getActivity(), Constant.A_URL, null, RegisterResponse.class,
                new DataCallback<RegisterResponse>() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, RegisterResponse data) {
                        mAdapter = new StarListAdapter(getActivity(), data.getList());
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

    @Override
    protected void setListener() {

    }

    @Override
    protected void getExtraParams() {

    }

}
