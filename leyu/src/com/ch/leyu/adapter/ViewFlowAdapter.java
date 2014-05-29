
package com.ch.leyu.adapter;

import com.ch.leyu.R;
import com.ch.leyu.responseparse.Property;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class ViewFlowAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mInflater;

    private ArrayList<Property> arrayList;

    public ViewFlowAdapter(Context context, ArrayList<Property> arrayList) {
        mContext = context;
        this.arrayList = arrayList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return Integer.MAX_VALUE; // 返回很大的值使得getView中的position不断增大来实现循环
        // return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.all_viewflow_item, null);
        }
        ImageLoader.getInstance().displayImage(
                arrayList.get(position % arrayList.size()).getImageSrc(),
                (ImageView) convertView.findViewById(R.id.imgView),
                ImageLoaderUtil.getImageLoaderOptions());

        return convertView;
    }

}
