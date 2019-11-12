package com.wang.baseadapter.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created on 2019/7/29.
 * Author: bigwang
 * Description:
 */
public class MarginGridDecoration extends RecyclerView.ItemDecoration {

    private int mMargin;

    private boolean mIncludeEdge;

    public MarginGridDecoration(Context context, int margin) {
        this(dp2px(context, margin), false);
    }

    public MarginGridDecoration(Context context, int margin, boolean includeEdge) {
        this(dp2px(context, margin), includeEdge);
    }

    public MarginGridDecoration(@Px int margin, boolean includeEdge) {
        mMargin = margin;
        mIncludeEdge = includeEdge;
    }

    public void setMargin(int margin) {
        mMargin = margin;
    }

    public void setIncludeEdge(boolean includeEdge) {
        mIncludeEdge = includeEdge;
    }

    private static int dp2px(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (mMargin <= 0) {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }
        int position;
        int index;
        int spanCount;
        int spanSize;
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            position = params.getViewLayoutPosition();
            index = params.getSpanIndex();
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            spanSize = params.getSpanSize();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            position = params.getViewLayoutPosition();
            index = params.getSpanIndex();
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            spanSize = params.isFullSpan() ? spanCount : 1;
        } else {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }
        if (mIncludeEdge) {
            outRect.left = mMargin - index * mMargin * spanSize / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (index + 1) * mMargin * spanSize / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount / spanSize) { // top edge
                outRect.top = mMargin;
            }
            outRect.bottom = mMargin; // item bottom
        } else {
            outRect.left = index * mMargin * spanSize / spanCount;
            outRect.right = mMargin - (index + 1) * mMargin * spanSize / spanCount;
            if (position >= spanCount / spanSize) {
                outRect.top = mMargin; // item top
            }
        }

    }
}
