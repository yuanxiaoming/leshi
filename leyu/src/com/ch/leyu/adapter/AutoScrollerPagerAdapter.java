package com.ch.leyu.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.ch.leyu.responseparse.Property;
import com.ch.leyu.utils.ImageLoaderUtil;
import com.ch.leyu.widget.view.RecyclingPagerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AutoScrollerPagerAdapter extends RecyclingPagerAdapter {
	
	public static final String TAG = "AutoScrollerPagerAdapter";
	private ArrayList<Property> mFocus ;
	private Context mContext ;

	public AutoScrollerPagerAdapter(Context context, ArrayList<Property> focus) {
		mFocus = focus ;
		mContext = context ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup container) {
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
		
		ImageLoader.getInstance().displayImage(mFocus.get(position % mFocus.size()).getImageSrc(), holder.image, ImageLoaderUtil.getImageOptions());
		
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
