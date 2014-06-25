
package com.ch.leyu.adapter;

import com.ch.leyu.R;
import com.ch.leyu.responseparse.Info;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/***
 * 明星解说适配器
 * 
 * @author Administrator
 */
public class StarListAdapter extends BaseAdapter{
    
    private ArrayList<Info> mArrayList;

    private Context mContext;

    public StarListAdapter(Context context, ArrayList<Info> objects) {
        this.mArrayList = objects;
        this.mContext = context;
    }

    public void chargeArrayList(ArrayList<Info> arrayList) {
        this.mArrayList = arrayList;
        notifyDataSetChanged();
    }

    public void addArrayList(ArrayList<Info> arrayList) {
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.star_commentary_item,parent, false);
            viewHolder = new ViewHolder();
            viewHolder.cover = (ImageView) convertView.findViewById(R.id.fragment_c_item_imageView1);
            viewHolder.name = (TextView) convertView.findViewById(R.id.fragment_c_item_textView1);
            viewHolder.intro = (TextView) convertView.findViewById(R.id.fragment_c_item_textView2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Info item = (Info) getItem(position);
        if (item != null) {
            viewHolder.name.setText(mArrayList.get(position).getNickname());
            viewHolder.intro.setText(item.getDetail());
            ImageLoader.getInstance().displayImage(item.getThumb(), viewHolder.cover,ImageLoaderUtil.getImageLoaderOptions());
        }

        return convertView;
    }

    class ViewHolder {

        ImageView cover;

        TextView name, intro;

    }
}
