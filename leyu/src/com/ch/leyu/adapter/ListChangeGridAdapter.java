
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListChangeGridAdapter extends ListAsGridBaseAdapter {

    private ArrayList<Property> mArrayList;

    private LayoutInflater mInflater;

    private Context mContext;

    public ListChangeGridAdapter(Context context) {
        super(context);
    }

    public ListChangeGridAdapter(ArrayList<Property> arrayList, Context context) {
        super(context);
        this.mArrayList = arrayList;
        mInflater = LayoutInflater.from(context);
        mContext = context;
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
    public int getItemCount() {
        if (mArrayList != null) {
            return mArrayList.size();
        }
        return 0;
    }

    @Override
    protected View getItemView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.gridview_item, null);
            holder.imageView = (ImageView) view.findViewById(R.id.gd_img1);
            holder.textView = (TextView) view.findViewById(R.id.gd_tv1);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        
        holder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(CommonUtil.getWidthMetrics(mContext) / 2, CommonUtil.getWidthMetrics(mContext) / 3));
        holder.imageView.setScaleType(ScaleType.FIT_XY) ;

        ImageLoader.getInstance().displayImage(mArrayList.get(position).getImageSrc(),
                holder.imageView, ImageLoaderUtil.getImageLoaderOptions());
        holder.textView.setText(mArrayList.get(position).getTitle());
        return view;
    }

    class ViewHolder {
        ImageView imageView;

        TextView textView;
    }
}
