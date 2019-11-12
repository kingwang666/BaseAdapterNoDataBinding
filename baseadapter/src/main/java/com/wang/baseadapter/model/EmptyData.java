package com.wang.baseadapter.model;

import androidx.annotation.DrawableRes;

import com.wang.baseadapter.BaseRecyclerViewAdapter;

/**
 * Created by wang
 * on 2016/12/28
 */

public class EmptyData implements TypeData {

    @DrawableRes
    public int mResId;

    public CharSequence mDesc;

    public EmptyData(int resId, CharSequence desc) {
        mResId = resId;
        mDesc = desc;
    }

    public EmptyData(int resId) {
        this(resId, null);
    }

    public EmptyData(CharSequence desc) {
        this(-1, desc);
    }

    public EmptyData() {
        this(-1, null);
    }

    @Override
    public int getDataType() {
        return BaseRecyclerViewAdapter.TYPE_EMPTY;
    }
}
