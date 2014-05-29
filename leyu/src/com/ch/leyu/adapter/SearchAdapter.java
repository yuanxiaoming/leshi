
package com.ch.leyu.adapter;

import com.ch.leyu.R;
import com.ch.leyu.responseparse.Property;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {
    private Context mContext;

    private ArrayList<Property> mlist;

    public SearchAdapter(Context mContext, ArrayList<Property> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        if(mlist!=null){
            return mlist.size();
        }
        
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mlist != null) {
            return mlist.get(position);
        }
        return null;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.search_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.search_item_tv_result);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mlist.get(position).getTitle());

        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }

}
