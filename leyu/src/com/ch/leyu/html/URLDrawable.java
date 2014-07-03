package com.ch.leyu.html;

import com.ch.leyu.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;

public class URLDrawable extends BitmapDrawable{

	protected Drawable drawable;

	public URLDrawable(Context context) {
		this.setBounds(getDefaultImageBounds(context));
		drawable = context.getResources().getDrawable(R.drawable.abc_ab_bottom_solid_dark_holo);
		drawable.setBounds(getDefaultImageBounds(context));
	}

	@Override
	public void draw(Canvas canvas) {
		Log.d("test", "this=" + this.getBounds());
		if (drawable != null) {
			Log.d("test", "draw=" + drawable.getBounds());
			drawable.draw(canvas);
		}
	}

	public static Rect getDefaultImageBounds(Context context) {
		Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = (int) (width * 9 / 16);
		Rect bounds = new Rect(0, 0, width/18, height/15);
		return bounds;
	}

}
