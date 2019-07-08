package com.wang.baseadapter.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Px;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 2019/6/26.
 * Author: bigwang
 * Description:
 */
public class MarginItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private int mMargin;

    /**
     * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    private int mOrientation;


    public MarginItemDecoration(Context context, int margin) {
        this(dp2px(context, margin), VERTICAL);
    }

    public MarginItemDecoration(Context context, int margin, int orientation) {
        this(dp2px(context, margin), orientation);
    }


    public MarginItemDecoration(@Px int margin, int orientation) {
        mMargin = margin;
        mOrientation = orientation;
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * {@link RecyclerView.LayoutManager} changes orientation.
     *
     * @param orientation {@link #HORIZONTAL} or {@link #VERTICAL}
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }


    public void setMargin(int margin) {
        mMargin = margin;
    }

    private static int dp2px(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (mMargin == 0 || parent.getChildAdapterPosition(view) == 0) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        if (mOrientation == VERTICAL) {
            outRect.set(0, mMargin, 0, 0);
        } else {
            outRect.set(mMargin, 0, 0, 0);
        }
    }


}
