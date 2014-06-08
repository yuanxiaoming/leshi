
package com.ch.leyu.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/***
 * 解决GridView与ScrollView冲突问题
 * 
 * @author Administrator
 */
public class LYGridView extends GridView {

    public LYGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LYGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LYGridView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
