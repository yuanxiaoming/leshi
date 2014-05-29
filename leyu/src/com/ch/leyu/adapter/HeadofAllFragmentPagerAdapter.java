package com.ch.leyu.adapter;

import com.ch.leyu.responseparse.Property;
import com.ch.leyu.ui.VideoPlayActivity;
import com.ch.leyu.utils.Constant;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.ch.leyu.view.RecyclingPagerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import java.util.ArrayList;

public class HeadofAllFragmentPagerAdapter extends RecyclingPagerAdapter {

	private Context mContext ;
	
	private ArrayList<Property> mList;

	public HeadofAllFragmentPagerAdapter(Context context, ArrayList<Property>arrayList) {
	   this.mList = arrayList ;
	   this.mContext = context ;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup container) {
		ViewHolder holder = null ;
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = holder.image = new ImageView(mContext);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.image.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		holder.image.setScaleType(ScaleType.FIT_XY);

		ImageLoader.getInstance().displayImage(mList.get(position % mList.size()).getImageSrc(), 
		        holder.image,ImageLoaderUtil.getImageLoaderOptions());
		
		holder.image.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VideoPlayActivity.class);
                intent.putExtra(Constant.UID,mList.get(position % mList.size()).getId());
                mContext.startActivity(intent);
            }
        });
		return convertView;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	private final class ViewHolder {
		public ImageView image ;
	}
}
