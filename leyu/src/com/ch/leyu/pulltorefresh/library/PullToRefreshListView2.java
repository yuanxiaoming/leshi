
package com.ch.leyu.pulltorefresh.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.ch.leyu.R;
import com.ch.leyu.pulltorefresh.library.internal.FlipLoadingLayout;
import com.ch.leyu.pulltorefresh.library.internal.LoadingLayout;

public class PullToRefreshListView2 extends PullToRefreshListView {

    private static final String TAG = "PullToRefreshListView2";

    private boolean isEnd = false;

    public PullToRefreshListView2(Context context) {
        super(context);
    }

    public PullToRefreshListView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshListView2(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshListView2(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }


    public void end(boolean isEnd) {
        this.isEnd = isEnd;
    }

    @Override
    protected void onRefreshing(boolean doScroll) {
        if (getCurrentMode() == Mode.PULL_FROM_END && isEnd) {
            super.onRefreshing(false);
            if (doScroll) {
                int selection = getRefreshableView().getCount() - 1;
                int scrollToY = getScrollY() - getFooterSize();

                disableLoadingLayoutVisibilityChanges();
                setHeaderScroll(scrollToY);
                getRefreshableView().setSelection(selection);
                smoothScrollTo(0);
                onRefreshComplete();
            }
        }
        else {
            super.onRefreshing(doScroll);
        }
    }

    protected void callRefreshListener() {
        if (getCurrentMode() == Mode.PULL_FROM_END && isEnd) {
            return;
        }
        super.callRefreshListener();
    }

    protected LoadingLayout createLoadingLayout(Context context, Mode mode, TypedArray attrs) {
        LoadingLayout layout = createLoadingLayout(context, mode, getPullToRefreshScrollDirection(), attrs);
        layout.setVisibility(View.INVISIBLE);
        return layout;
    }

    private LoadingLayout createLoadingLayout(Context context, final Mode mode, final Orientation scrollDirection, TypedArray attrs) {
        return new GLoadingLayout(context, mode, scrollDirection, attrs);
    }

    private class GLoadingLayout extends FlipLoadingLayout {
        private CharSequence mEndText;

        public GLoadingLayout(Context context, final Mode mode, final Orientation scrollDirection, TypedArray attrs) {
            super(context, mode, scrollDirection, attrs);
            mEndText = context.getText(R.string.pull_to_refresh_from_bottom_end_label);
        }

        @Override
        protected void pullToRefreshImpl() {
            if (mMode == Mode.PULL_FROM_END && isEnd) {
                mHeaderText.setText(mEndText);
                mHeaderImage.setVisibility(View.INVISIBLE);
                mSubHeaderText.setVisibility(View.INVISIBLE);
            }
            else {
                super.pullToRefreshImpl();
            }
        }

        @Override
        protected void releaseToRefreshImpl() {
            if (mMode == Mode.PULL_FROM_END && isEnd) {
                mHeaderText.setText(mEndText);
                mHeaderImage.setVisibility(View.INVISIBLE);
                mSubHeaderText.setVisibility(View.INVISIBLE);
            }
            else {
                super.releaseToRefreshImpl();
            }
        }

        @Override
        protected void refreshingImpl() {
            if (mMode == Mode.PULL_FROM_END && isEnd) {
                mHeaderText.setText(mEndText);
                mHeaderImage.clearAnimation();
                mHeaderImage.setVisibility(View.INVISIBLE);
                mHeaderProgress.setVisibility(View.INVISIBLE);
                mSubHeaderText.setVisibility(View.INVISIBLE);
            }
            else {
                super.refreshingImpl();
            }
        }

        @Override
        protected void resetImpl() {
            if (mMode == Mode.PULL_FROM_END && isEnd) {
                mHeaderText.setText(mEndText);
                mHeaderImage.clearAnimation();
                mHeaderImage.setVisibility(View.INVISIBLE);
                mHeaderProgress.setVisibility(View.INVISIBLE);
                mSubHeaderText.setVisibility(View.INVISIBLE);
            }
            else {
                super.resetImpl();
            }
        }
    }


}

