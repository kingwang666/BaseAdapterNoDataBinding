package com.wang.baseadapter.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 2019/6/26.
 * Author: bigwang
 * Description:
 */
public class ColorTypeItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private int mDividerSize; //线的高度
    private Paint mPaint;           //画笔将自己弄出来偏移量的分割线画出颜色
    private int mMargin;

    /**
     * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    private int mOrientation;

    private final Rect mBounds = new Rect();

    private int mType;

    public ColorTypeItemDecoration(Context context, int type, @ColorRes int color) {
        this(type, ContextCompat.getColor(context, color), 1, 0, VERTICAL);
    }

    public ColorTypeItemDecoration(Context context, int type, @ColorRes int color, int orientation) {
        this(type, ContextCompat.getColor(context, color), 1, 0, orientation);
    }

    public ColorTypeItemDecoration(int type, @ColorInt int color) {
        this(type, color, 1, 0, VERTICAL);
    }

    public ColorTypeItemDecoration(int type, @ColorInt int color, int orientation) {
        this(type, color, 1, 0, orientation);
    }

    public ColorTypeItemDecoration(int type, @ColorInt int color, @Px int margin, int orientation) {
        this(type, color, 1, margin, orientation);
    }


    public ColorTypeItemDecoration(int type, @ColorInt int color, @Px int dividerSize, @Px int margin, int orientation) {
        mType = type;
        mDividerSize = dividerSize;
        mMargin = margin;
        mOrientation = orientation;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);          //抗锯齿
        mPaint.setColor(color);        //默认颜色
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

    public void setColor(@ColorInt int color) {
        mPaint.setColor(color);
    }

    public void setDividerSize(int dividerSize) {
        mDividerSize = dividerSize;
    }


    public void setMargin(int margin) {
        mMargin = margin;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (mDividerSize == -1 || parent.getChildAdapterPosition(view) == state.getItemCount()) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, mDividerSize);
        } else {
            outRect.set(0, 0, mDividerSize, 0);
        }
    }


    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getLayoutManager() == null || mDividerSize == -1) {
            return;
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent, state.getItemCount());
        } else {
            drawHorizontal(c, parent, state.getItemCount());
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent, int itemCount) {
        canvas.save();
        final int left;
        final int right;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(child);
            //第一个ItemView不需要绘制
            if (index == itemCount - 1) {
                continue;
            }
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            final int top = bottom - mDividerSize;
            canvas.drawRect(left + mMargin, top, right - mMargin, bottom, mPaint);
        }
        canvas.restore();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent, int itemCount) {
        canvas.save();
        final int top;
        final int bottom;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(child);
            //第一个ItemView不需要绘制
            if (index == itemCount - 1) {
                continue;
            }
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(child.getTranslationX());
            final int left = right - mDividerSize;
            canvas.drawRect(left, top + mMargin, right, bottom - mMargin, mPaint);
        }
        canvas.restore();
    }


}
