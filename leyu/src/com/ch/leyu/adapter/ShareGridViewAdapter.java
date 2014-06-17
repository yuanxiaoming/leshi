
package com.ch.leyu.adapter;

import com.ch.leyu.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ShareGridViewAdapter extends BaseAdapter {

    private int[] mImgs = null ;

    private LayoutInflater mInflater;
    
    public ShareGridViewAdapter(Context context)
    {
    	mImgs = new int[]{R.drawable.legames_sina, R.drawable.legames_tx, R.drawable.legames_qq,
                R.drawable.legames_baidu, R.drawable.legames_renren, R.drawable.legames_weixin,
                R.drawable.legames_xiaoyou, R.drawable.legames_douhan };
    	mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mImgs.length;
    }

    @Override
    public Object getItem(int position) {
        return mImgs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null ;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.play_share_item, parent,false);
            holder.img = (ImageView) convertView.findViewById(R.id.shar_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.img.setBackgroundResource(mImgs[position]);
        return convertView;
    }

    class ViewHolder {
        ImageView img;
    }

}
