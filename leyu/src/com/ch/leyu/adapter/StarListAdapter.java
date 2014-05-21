
package com.ch.leyu.adapter;

import com.ch.leyu.R;
import com.ch.leyu.responseparse.Info;
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

public class StarListAdapter extends ArrayAdapter<Info> {

    public StarListAdapter(Context context, List<Info> objects) {
        super(context, 0, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.star_commentary_item,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.cover = (ImageView) convertView
                    .findViewById(R.id.fragment_c_item_imageView1);
            viewHolder.name = (TextView) convertView.findViewById(R.id.fragment_c_item_textView1);
            viewHolder.intro = (TextView) convertView.findViewById(R.id.fragment_c_item_textView2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Info item = getItem(position);
        if (item != null) {
            viewHolder.name.setText(item.getNickname());
            viewHolder.intro.setText(item.getDetail());

            ImageLoader.getInstance().displayImage(item.getThumb(), viewHolder.cover,
                    ImageLoaderUtil.getImageLoaderOptions());
        }

        return convertView;
    }

    class ViewHolder {

        ImageView cover;

        TextView name, intro;

    }
}
