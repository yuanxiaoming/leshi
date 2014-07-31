
package com.ch.leyu.adapter;

import com.ch.leyu.R;
import com.ch.leyu.responseparse.TagResponse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PopGridViewAdapter extends BaseAdapter {

    private ArrayList<TagResponse> mArrayList;

    private LayoutInflater mInflater;

    public PopGridViewAdapter(ArrayList<TagResponse> arrayList, Context context) {
        this.mArrayList = arrayList;
        mInflater = LayoutInflater.from(context);
    }

    public void chargeArrayList(ArrayList<TagResponse> arrayList) {
        this.mArrayList = arrayList;
        notifyDataSetChanged();
    }

    public void addArrayList(ArrayList<TagResponse> arrayList) {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.pop_item, null);
            holder.button = (TextView) convertView.findViewById(R.id.pop_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.button.setText(mArrayList.get(position).getTag());
        return convertView;
    }

    class ViewHolder {
       TextView button ;
    }

}
