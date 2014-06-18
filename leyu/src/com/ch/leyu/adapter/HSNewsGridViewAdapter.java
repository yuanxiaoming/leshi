
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/***
 * 新闻资讯--炉石传说适配器
 * 
 * @author L
 */
public class HSNewsGridViewAdapter extends BaseAdapter {

    private ArrayList<Property> arrayList;

    private Context mContext;

    public HSNewsGridViewAdapter(ArrayList<Property> arrayList, Context context) {
        this.arrayList = arrayList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (arrayList != null) {
            return arrayList.size();
        }

        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (arrayList != null) {
            return arrayList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.hsnews_gridview_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.hsnews_gd_img1);
            holder.textView = (TextView) convertView.findViewById(R.id.hsnews_gd_tv1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(CommonUtil
                .getWidthMetrics(mContext) / 2, CommonUtil.getWidthMetrics(mContext) / 5));
        ImageLoader.getInstance().displayImage(arrayList.get(position).getImageSrc(),
                holder.imageView, ImageLoaderUtil.getImageLoaderOptions());
        holder.textView.setText(arrayList.get(position).getTitle());
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;

        TextView textView;
    }

}
