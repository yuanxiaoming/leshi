package com.ch.leyu.widget.xlistview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class XXListView extends XListView {
    public static final String TAG = "XXListView";

    public XXListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public XXListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XXListView(Context context) {
        super(context);
    }

    private ViewPager mTitleView;

    public void setViewPager(ViewPager viewPager) {
        this.mTitleView = viewPager;
    }

    //    private float mPreY;
    //
    //    @Override
    //    public boolean dispatchTouchEvent(MotionEvent ev) {
    //
    //        switch (ev.getActionMasked()) {
    //        case MotionEvent.ACTION_DOWN:
    //            mPreY = ev.getRawY();
    //            System.out.println("mPreY = " + mPreY);
    //            break;
    //
    //        case MotionEvent.ACTION_MOVE:
    //            float curY = ev.getRawY();
    //            System.out.println("curY = " + curY);
    //            float deltaY = curY - mPreY;
    //            System.out.println("deltaY = " + deltaY);
    //            // XListView滑动的时候 禁止掉界面布局的移动
    //            EventBus.getDefault().post(new XListViewTouchEventBus(deltaY, this));
    //            mPreY = ev.getRawY();
    //            System.out.println("mPreY = " + mPreY);
    //            break;
    //        default:
    //            break;
    //        }
    //        // 这里返回false 没作用？
    //        return CommonUtil.getAppliction(getContext()).isScroll() ? super.dispatchTouchEvent(ev) : true;
    //    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (mTitleView != null) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTitleView.getLayoutParams();
                    int newTopMargin = (int) (params.topMargin + deltaY);
                    if (deltaY > 0) {
                        System.out.println(deltaY);
                        // 表示向下拉 并且当前可见的List item 为1 ListView的header为0
                        // 当前可见的Item 为1 或者为 0 才让其下滑
                        if (getFirstVisiblePosition() == 1 || getFirstVisiblePosition() == 0) {
                            if (newTopMargin > 0)
                                newTopMargin = 0;
                            if (newTopMargin != 0) {
                                params.topMargin = newTopMargin;
                                mTitleView.setLayoutParams(params);
                                return true;
                            }
                        }
                    }
                    // deltaY <= 0 表示向上拉
                    else if (deltaY < 0) {
                        if (newTopMargin <= -params.height) {
                            newTopMargin = -params.height;
                        }
                        params.topMargin = newTopMargin;
                        mTitleView.setLayoutParams(params);
                        if (params.topMargin != -params.height) {
                            // 处理topMargin的边界
                            return true;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        return super.onTouchEvent(ev);

    }
}
