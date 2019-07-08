package com.wang.baseadapter.util;

import android.content.Context;
import android.graphics.PointF;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

/**
 * Created by wang
 * on 2017/4/6
 */

public class SnappingLinearLayoutManager extends LinearLayoutManager {
    /**
     * scroll position
     */
    private int mSnap;

    public SnappingLinearLayoutManager(Context context) {
        this(context, LinearSmoothScroller.SNAP_TO_START);
    }

    public SnappingLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        this(context, LinearSmoothScroller.SNAP_TO_START, orientation, reverseLayout);
    }

    public SnappingLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, LinearSmoothScroller.SNAP_TO_START, attrs, defStyleAttr, defStyleRes);
    }

    public SnappingLinearLayoutManager(Context context, int snap) {
        super(context);
        mSnap = snap;
    }

    public SnappingLinearLayoutManager(Context context, int snap, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        mSnap = snap;
    }

    public SnappingLinearLayoutManager(Context context, int snap, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mSnap = snap;
    }


    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {

        RecyclerView.SmoothScroller smoothScroller = new SnappedSmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }


    public int getSnap() {
        return mSnap;
    }

    public void setSnap(int snap) {
        mSnap = snap;
    }

    private class SnappedSmoothScroller extends LinearSmoothScroller {


        public SnappedSmoothScroller(Context context) {
            super(context);

        }

        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return SnappingLinearLayoutManager.this
                    .computeScrollVectorForPosition(targetPosition);
        }

        @Override
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return 150f/ displayMetrics.densityDpi;
        }

        @Override
        protected int calculateTimeForScrolling(int dx) {
            return Math.min(80, super.calculateTimeForScrolling(dx));
        }

        @Override
        protected int getVerticalSnapPreference() {
            return mSnap;
        }

        @Override
        protected int getHorizontalSnapPreference() {
            return mSnap;
        }

    }
}
