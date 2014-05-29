/**
 * @Title: LatesSerachAdapter.java
 * @Package com.ch.leyu.adapter
 * Copyright: Copyright (c) 2013
 * Company: 广州灿和信息科技有限公司
 * @author xiaoming.yuan
 * @date 2014-5-21 下午7:17:55
 * @version V1.0
 */

package com.ch.leyu.adapter;

import com.ch.leyu.R;
import com.ch.leyu.provider.LatestSearch;
import com.ch.leyu.ui.AppContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @ClassName: LatesSerachAdapter
 * @author xiaoming.yuan
 * @date 2014-5-21 下午7:17:55
 */

public class LatestSearchAdapter extends BaseAdapter {

    private ArrayList<LatestSearch> mLatestSearchsArrayList;

    private Context mContext;

    public LatestSearchAdapter(ArrayList<LatestSearch> latestSearchsArrayList) {
        this.mLatestSearchsArrayList = latestSearchsArrayList;
        this.mContext = AppContext.getInstance();

    }

    public void chargeArrayList(ArrayList<LatestSearch> latestSearchsArrayList) {
        this.mLatestSearchsArrayList = latestSearchsArrayList;
        notifyDataSetChanged();
    }

    public ArrayList<LatestSearch> getArrayList() {
        return this.mLatestSearchsArrayList;
    }

    public void addArrayList(ArrayList<LatestSearch> latestSearchsArrayList) {
        if (latestSearchsArrayList != null) {
            this.mLatestSearchsArrayList.addAll(latestSearchsArrayList);
            notifyDataSetChanged();
        } else {
            chargeArrayList(latestSearchsArrayList);
        }
    }

    /**
     * Title: getCount Description:
     * 
     * @return
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        if (mLatestSearchsArrayList == null || mLatestSearchsArrayList.size() == 0) {
            return 0;
        }
        return mLatestSearchsArrayList.size();
    }

    /**
     * Title: getItem Description:
     * 
     * @param position
     * @return
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        if (mLatestSearchsArrayList == null || mLatestSearchsArrayList.size() == 0) {
            return null;
        }
        return mLatestSearchsArrayList.get(position).getKeyword();

    }

    /**
     * Title: getItemId Description:
     * 
     * @param position
     * @return
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Title: getView Description:
     * 
     * @param position
     * @param convertView
     * @param parent
     * @return
     * @see android.widget.Adapter#getView(int, android.view.View,
     *      android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.search_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.search_item_tv_result);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mLatestSearchsArrayList.get(position).getKeyword());

        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
