
package com.ch.leyu.adapter;

import com.ch.leyu.R;
import com.ch.leyu.responseparse.Property;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CLYAdapter extends ArrayAdapter<Property> {

    public CLYAdapter(Context context, List<Property> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_allnew_item,
                    parent, false);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.allnews_item_img);
            holder.title = (TextView) convertView.findViewById(R.id.allnews_item_title);
            holder.time = (TextView) convertView.findViewById(R.id.allnews_item_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Property item = getItem(position);
        if (item != null) {
            holder.title.setText(item.getTitle());
            holder.time.setText(item.getCreateTime());
            if(item.getImageSrc()==null||item.getImageSrc().equals("")){
                holder.img.setVisibility(View.GONE);
            }else {
                ImageLoader.getInstance().displayImage(item.getImageSrc(), holder.img , ImageLoaderUtil.getImageLoaderOptions());
            }
           
        }
        return convertView;
    }

    private final class ViewHolder {
        public ImageView img;

        public TextView title,  time;
    }

}
