
package com.ch.leyu.adapter;

import com.ch.leyu.R;
import com.ch.leyu.responseparse.HistroyResponse;
import com.ch.leyu.utils.CommonUtil;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

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

public class HistroyStickyHeaderAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private Context mContext;

    private ArrayList<HistroyResponse> mHistroyList;

    public static final long DAY_TIME = 1000 * 60 * 60 * 24;

    public HistroyStickyHeaderAdapter(Context context, ArrayList<HistroyResponse> histroyList) {
        this.mContext = context;
        this.mHistroyList = histroyList;
    }

    @Override
    public int getCount() {
        if (mHistroyList != null) {
            return mHistroyList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mHistroyList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.recommend_listview_item,
                    null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.recommend_img_pic);
            holder.textView = (TextView) convertView.findViewById(R.id.recommend_tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(CommonUtil
                .getWidthMetrics(mContext) / 4, CommonUtil.getWidthMetrics(mContext) / 5));
        holder.imageView.setScaleType(ScaleType.FIT_XY);

        ImageLoader.getInstance().displayImage(mHistroyList.get(position).getPath(),
                holder.imageView, ImageLoaderUtil.getImageLoaderOptions());
        holder.textView.setText(mHistroyList.get(position).getTitle());

        return convertView;
    }

    class ViewHolder {
        ImageView imageView;

        TextView textView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.alterant_head, null);
            holder.textView = (TextView) convertView.findViewById(R.id.alterant_head_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Long nowTime = System.currentTimeMillis();
        Long pastTime = Long.valueOf(mHistroyList.get(position).getTimeStamp());
        String content = "";
        if (nowTime - pastTime < DAY_TIME * 1) {
            content = "一天内";
        } else if (nowTime - pastTime < DAY_TIME * 3) {
            content = "三天内";
        } else if (nowTime - pastTime < DAY_TIME * 7) {
            content = "一周内";
        } else if (nowTime - pastTime > DAY_TIME * 7) {
            content = "更早";
        }
        holder.textView.setText(content);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        Long nowTime = System.currentTimeMillis();
        Long pastTime = Long.valueOf(mHistroyList.get(position).getTimeStamp());
        Long headerid = null;
        if (nowTime - pastTime < DAY_TIME * 1) {
            headerid = (long) 1;
        } else if (nowTime - pastTime < DAY_TIME * 3) {
            headerid = (long) 2;
        } else if (nowTime - pastTime < DAY_TIME * 7) {
            headerid = (long) 3;
        } else if (nowTime - pastTime > DAY_TIME * 7) {
            headerid = (long) 4;
        }

        return headerid;
    }

}
