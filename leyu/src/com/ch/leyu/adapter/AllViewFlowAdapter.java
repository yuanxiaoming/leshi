
package com.ch.leyu.adapter;

import com.ch.leyu.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AllViewFlowAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mInflater;

    private static final int[] ids = {
            R.drawable.biz_plugin_weather_beijin, R.drawable.biz_plugin_weather_guangzhou,
            R.drawable.biz_plugin_weather_shanghai
    };

    public AllViewFlowAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE; // 返回很大的值使得getView中的position不断增大来实现循环
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
        ((ImageView) convertView.findViewById(R.id.imgView)).setImageResource(ids[position
                % ids.length]);

        return convertView;
    }

}
