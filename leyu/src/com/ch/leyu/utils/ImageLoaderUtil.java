package com.ch.leyu.utils;

import com.ch.leyu.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.graphics.Bitmap;

public class ImageLoaderUtil {

	public static DisplayImageOptions getImageLoaderOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.legames_default).showImageForEmptyUri(R.drawable.legames_default)
				.showImageOnFail(R.drawable.legames_default).cacheInMemory(true).cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(0)).bitmapConfig(Bitmap.Config.ARGB_8888).build();

		return options;
	}
	
	public static DisplayImageOptions getImageLoaderOptions(int roundDegree) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.legames_default).showImageForEmptyUri(R.drawable.legames_default)
				.showImageOnFail(R.drawable.legames_default).cacheInMemory(true).cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(roundDegree)).bitmapConfig(Bitmap.Config.ARGB_8888).build();

		return options;
	}

	public static DisplayImageOptions getImageOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.legames_default).showImageForEmptyUri(R.drawable.legames_default)
				.showImageOnFail(R.drawable.legames_default).cacheInMemory(true).cacheOnDisc(true).displayer(new FadeInBitmapDisplayer(300)).bitmapConfig(Bitmap.Config.ARGB_8888)
				.build();
		return options;
	}
}
