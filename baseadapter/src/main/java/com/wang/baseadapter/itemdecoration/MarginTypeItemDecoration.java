package com.wang.baseadapter.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 2019/6/26.
 * Author: bigwang
 * Description:
 */
public class MarginTypeItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private int mMargin;

    /**
     * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    private int mOrientation;

    private int mType;


    public MarginTypeItemDecoration(Context context, int type, int margin) {
        this(type, dp2px(context, margin), VERTICAL);
    }

    public MarginTypeItemDecoration(Context context, int type, int margin, int orientation) {
        this(type, dp2px(context, margin), orientation);
    }


    public MarginTypeItemDecoration(int type, @Px int margin, int orientation) {
        mMargin = margin;
        mOrientation = orientation;
        mType = type;
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
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);
        int position = holder != null ? holder.getAdapterPosition() : RecyclerView.NO_POSITION;
        int type = holder != null ? holder.getItemViewType() : RecyclerView.NO_POSITION;
        if (mMargin == 0 || position == 0 || position == RecyclerView.NO_POSITION || mType != type) {
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
