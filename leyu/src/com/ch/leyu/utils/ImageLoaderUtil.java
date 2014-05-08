package com.ch.leyu.utils;

import com.ch.leyu.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.graphics.Bitmap;

public class ImageLoaderUtil {

	public static DisplayImageOptions getImageLoaderOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.abc_ab_bottom_solid_dark_holo).showImageForEmptyUri(R.drawable.abc_ab_bottom_solid_dark_holo)
				.showImageOnFail(R.drawable.abc_ab_bottom_solid_dark_holo).cacheInMemory(true).cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(5)).bitmapConfig(Bitmap.Config.RGB_565).build();

		return options;
	}

	public static DisplayImageOptions getImageOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.abc_ab_bottom_solid_dark_holo).showImageForEmptyUri(R.drawable.abc_ab_bottom_solid_dark_holo)
				.showImageOnFail(R.drawable.abc_ab_bottom_solid_dark_holo).cacheInMemory(true).cacheOnDisc(true).displayer(new FadeInBitmapDisplayer(300)).bitmapConfig(Bitmap.Config.ARGB_8888)
				.build();
		return options;
	}
}
