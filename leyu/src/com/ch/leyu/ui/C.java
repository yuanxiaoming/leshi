
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.CLYAdapter;
import com.ch.leyu.adapter.StarListAdapter;
import com.ch.leyu.http.work.DataCallback;
import com.ch.leyu.http.work.JHttpClient;
import com.ch.leyu.responseparse.RegisterResponse;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.widget.xlistview.XListView;

import org.apache.http.Header;

import android.view.View;
import android.widget.ProgressBar;

public class C extends BaseFragment {

    private XListView mXListView;

    private ProgressBar mBar;

    private StarListAdapter mAdapter;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void findViewById() {
        mXListView = (XListView) findViewById(R.id.listview_c_cly);
        mBar = (ProgressBar) findViewById(R.id.progressBar_c);
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
                        mBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        mBar.setVisibility(View.GONE);
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
