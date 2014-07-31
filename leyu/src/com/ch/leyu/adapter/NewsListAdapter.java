
package com.ch.leyu.adapter;

import com.ch.leyu.R;
import com.ch.leyu.responseparse.Property;
import com.ch.leyu.ui.NewsDetailActivity;
import com.ch.leyu.utils.Constant;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsListAdapter extends BaseAdapter {

    private ArrayList<Property> mNewsList;

    private Context mContext;

    public NewsListAdapter(ArrayList<Property> mNewsList, Context mContext) {
        this.mContext = mContext;
        this.mNewsList = mNewsList;
    }

    @Override
    public int getCount() {
        if(mNewsList!=null){
            return mNewsList.size();
        }
        return 0;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.news_listview_item, null);
            holder.txt = (TextView) convertView.findViewById(R.id.news_lvitem_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txt.setText(mNewsList.get(position).getTitle());
        holder.txt.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(mContext, NewsDetailActivity.class);
                    intent.putExtra(Constant.CID , mNewsList.get(position).getId());
                    mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView txt;
    }

}
