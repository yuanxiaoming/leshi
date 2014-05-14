package com.ch.leyu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.ch.leyu.view.RecyclingPagerAdapter;

public class HeadofAllFragmentPagerAdapter extends RecyclingPagerAdapter {

	private int [] mFocus ;
	private Context mContext ;

	public HeadofAllFragmentPagerAdapter(Context context, int [] focus) {
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

		holder.image.setImageResource(mFocus[position % mFocus.length]);
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
