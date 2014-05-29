
package com.ch.leyu.adapter;

import com.ch.leyu.R;
import com.ch.leyu.responseparse.Property;
import com.ch.leyu.utils.CommonUtil;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    private ArrayList<Property> mArrayList;

    private LayoutInflater mInflater;
    
    private Context mContext ;

    public GridViewAdapter(ArrayList<Property> arrayList, Context context) {
        this.mArrayList = arrayList;
        mInflater = LayoutInflater.from(context);
        mContext = context ;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.gridview_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.gd_img1);
            holder.textView = (TextView) convertView.findViewById(R.id.gd_tv1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(CommonUtil.getWidthMetrics(mContext) / 2, CommonUtil.getWidthMetrics(mContext) / 3));
        holder.imageView.setScaleType(ScaleType.FIT_XY) ;

        ImageLoader.getInstance().displayImage(mArrayList.get(position).getImageSrc(),
                holder.imageView, ImageLoaderUtil.getImageLoaderOptions());
        holder.textView.setText(mArrayList.get(position).getTitle());
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;

        TextView textView;
    }

}
