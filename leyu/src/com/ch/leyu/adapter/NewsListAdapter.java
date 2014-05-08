
package com.ch.leyu.adapter;

import com.ch.leyu.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsListAdapter extends BaseAdapter {

    private ArrayList<String> mNewsList;

    private Context mContext;

    public NewsListAdapter(ArrayList<String> mNewsList, Context mContext) {
        this.mContext = mContext;
        this.mNewsList = mNewsList;
    }

    @Override
    public int getCount() {
        return mNewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.news_listview_item, null);
            holder.txt = (TextView) convertView.findViewById(R.id.news_lvitem_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txt.setText(mNewsList.get(position));

        return convertView;
    }

    class ViewHolder {
        TextView txt;
    }

}
