
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
import android.widget.ImageView.ScaleType;

import java.util.ArrayList;

public class CLYAdapter extends BaseAdapter {

    private ArrayList<Property> mArrayList;;

    private Context mContext;

    public CLYAdapter(Context context, ArrayList<Property> objects) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_allnew_item,
                    parent, false);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.allnews_item_img);
            holder.title = (TextView) convertView.findViewById(R.id.allnews_item_title);
            holder.time = (TextView) convertView.findViewById(R.id.allnews_item_time); 
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        Property item = (Property) getItem(position);
        if (item != null) {
            holder.title.setText(item.getTitle());
            holder.time.setText(item.getCreateTime());
            holder.img.setLayoutParams(new RelativeLayout.LayoutParams(CommonUtil.getWidthMetrics(mContext) / 4, CommonUtil.getWidthMetrics(mContext) / 5));
            holder.img.setScaleType(ScaleType.FIT_XY) ;
            if (item.getImageSrc() == null || item.getImageSrc().equals("")) {
                holder.img.setVisibility(View.GONE);

            }else {
            ImageLoader.getInstance().displayImage(item.getImageSrc(), holder.img , ImageLoaderUtil.getImageLoaderOptions());
                
               
            }

        }
        
      
        return convertView;
    }

    private final class ViewHolder {
        public ImageView img;

        public TextView title, time;
    }

}
