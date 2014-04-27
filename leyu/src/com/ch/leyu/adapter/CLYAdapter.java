package com.ch.leyu.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ch.leyu.R;
import com.ch.leyu.responseparse.LoginResponse;
import com.ch.leyu.responseparse.RegisterResponse;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CLYAdapter extends ArrayAdapter<LoginResponse> {

    public CLYAdapter(Context context, List<LoginResponse> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_login, parent, false);
            holder = new ViewHolder();
            holder.cover = (ImageView) convertView.findViewById(R.id.img_login_cover);
            holder.area = (TextView) convertView.findViewById(R.id.txt_login_area);
            holder.score = (TextView) convertView.findViewById(R.id.txt_login_score);
            holder.total = (TextView) convertView.findViewById(R.id.txt_login_totalnumber);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        LoginResponse item = getItem(position);
        if (item != null) {
            holder.area.setText(item.getArea());
            holder.score.setText(item.getScore());
            holder.total.setText(item.getCur_num());

            ImageLoader.getInstance().displayImage(item.getCover(), holder.cover, ImageLoaderUtil.getImageLoaderOptions());
        }
        return convertView;
    }

    private final class ViewHolder {
        public ImageView cover;
        public TextView total, score, area;
    }

}
