
package com.ch.leyu.ui;

import com.ch.leyu.R;
import com.ch.leyu.adapter.HistroyStickyHeaderAdapter;
import com.ch.leyu.provider.HistoryManager;
import com.ch.leyu.responseparse.HistroyResponse;
import com.ch.leyu.utils.Constant;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class HistoryRecordFragment extends BaseFragment {

    private StickyListHeadersListView mStickyListHeadersListView;
    
    private HistroyStickyHeaderAdapter mStickyHeaderAdapter ;
    
    private ArrayList<HistroyResponse> mList ;

    @Override
    protected void getExtraParams() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.fragment_history_record);
    }

    @Override
    protected void loadfindViewById() {
        mStickyListHeadersListView = (StickyListHeadersListView) findViewById(R.id.stickylistheaderslistview);
       
     
    }
    
    @Override
    public void onResume() {
        super.onResume();
        mList = HistoryManager.findLatestSearchAll();
        if(mList!=null){
            mStickyHeaderAdapter = new HistroyStickyHeaderAdapter(getActivity(),mList);
            mStickyListHeadersListView.setAdapter(mStickyHeaderAdapter);
        }
    }

    @Override
    protected void setListener() {
        mStickyListHeadersListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 HistroyResponse item = (HistroyResponse) parent.getAdapter().getItem(position);
                 if(item!=null){
                     Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                     intent.putExtra(Constant.CID, item.getId());
                     startActivity(intent);
                 }
            }
        });
    }

    @Override
    protected void processLogic() {
       
    }

    @Override
    protected void reload() {

    }

}
