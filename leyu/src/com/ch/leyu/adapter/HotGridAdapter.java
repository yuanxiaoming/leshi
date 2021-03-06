
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
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/***
 * 炉石传说首页--小编推荐列表适配器
 * 
 * @author Administrator
 */
public class HotGridAdapter extends BaseAdapter {

    private ArrayList<Property> mNewsList;

    private Context mContext;

    public HotGridAdapter(ArrayList<Property> mNewsList, Context mContext) {
        this.mContext = mContext;
        this.mNewsList = mNewsList;
    }

    @Override
    public int getCount() {
        if (mNewsList != null) {
            return mNewsList.size();
        }
        return 0;

    }

    @Override
    public Object getItem(int position) {
        return mNewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.hot_grid_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.hot_img);
            holder.title = (TextView) convertView.findViewById(R.id.hot_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.img.setLayoutParams(new RelativeLayout.LayoutParams(CommonUtil.getWidthMetrics(mContext) / 2, (int) (CommonUtil.getWidthMetrics(mContext) / 3.5)));
        holder.img.setScaleType(ScaleType.FIT_XY);

        ImageLoader.getInstance().displayImage(mNewsList.get(position).getImageSrc(), holder.img,
                ImageLoaderUtil.getImageLoaderOptions());
        holder.title.setText(mNewsList.get(position).getTitle());

        return convertView;
    }

    class ViewHolder {
        ImageView img;

        TextView title;
    }

}
