
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

public class HSNewsDetailListAdapter extends BaseAdapter {

    private ArrayList<Property> mArrayList;;

    private Context mContext;

    public HSNewsDetailListAdapter(Context context, ArrayList<Property> objects) {
        this.mArrayList = objects;
        this.mContext = context;
    }

    public void chargeArrayList(ArrayList<Property> arrayList) {
        this.mArrayList = arrayList;
        notifyDataSetChanged();
    }

    public void addArrayList(ArrayList<Property> arrayList) {
        if (mArrayList != null) {
            this.mArrayList.addAll(arrayList);
            notifyDataSetChanged();
        } else {
            chargeArrayList(arrayList);
        }
    }

    @Override
    public int getCount() {
        if (mArrayList != null) {
            return mArrayList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mArrayList != null) {
            return mArrayList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_hsnews_detail_item,parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.hsnews_detail_item_title);
            holder.time = (TextView) convertView.findViewById(R.id.hsnews_detail_item_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Property item = (Property) getItem(position);
        if (item != null) {
            holder.title.setText(item.getTitle());
            holder.time.setText(item.getCreateTime());

        }
        return convertView;
    }

    private final class ViewHolder {

        public TextView title, time;
    }

}
