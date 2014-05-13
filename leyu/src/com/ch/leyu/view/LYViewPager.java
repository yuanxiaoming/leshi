
package com.ch.leyu.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * 解决viewpager中嵌套viewFlow的事件冲突。
 *
 * @author Administrator
 */
public class LYViewPager extends ViewPager {

    public LYViewPager(Context context) {
        super(context);
    }

    public LYViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v != this && v instanceof ViewPager) {
            return true;
        }

        return super.canScroll(v, checkV, dx, x, y);
    }

}
