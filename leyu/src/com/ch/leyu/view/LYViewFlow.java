
package com.ch.leyu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 解决ViewFlow与ScrollView冲突
 * 
 * @author L
 */

public class LYViewFlow extends ViewFlow {

    public LYViewFlow(Context context) {
        super(context);
    }

    public LYViewFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean ret = super.dispatchTouchEvent(ev);
        if (ret) {
            requestDisallowInterceptTouchEvent(true);
        }
        return ret;

    }

}
