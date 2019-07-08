package com.wang.baseadapter.itemdecoration;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 2019/6/26.
 * Author: bigwang
 * Description:
 */
public class DividerItemDecoration extends androidx.recyclerview.widget.DividerItemDecoration {

    public DividerItemDecoration(Context context, @DrawableRes int drawableId, int orientation) {
        this(context, orientation);
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable != null) {
            setDrawable(drawable);
        }
    }


    public DividerItemDecoration(Context context, Drawable drawable, int orientation) {
        this(context, orientation);
        setDrawable(drawable);
    }

    /**
     * Creates a divider {@link RecyclerView.ItemDecoration} that can be used with a
     * {@link LinearLayoutManager}.
     *
     * @param context     Current context, it will be used to access resources.
     * @param orientation Divider orientation. Should be {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    public DividerItemDecoration(Context context, int orientation) {
        super(context, orientation);
    }
}
