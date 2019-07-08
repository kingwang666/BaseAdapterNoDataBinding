package com.wang.baseadapter.delegate;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wang.baseadapter.R;
import com.wang.baseadapter.model.EmptyData;
import com.wang.baseadapter.model.ItemArray;


/**
 * Created on 2016/6/13.
 * Author: wang
 */
public class EmptyDelegate extends AdapterDelegate<EmptyDelegate.EmptyViewHolder> {

    @DrawableRes
    private int mResId = -1;

    private CharSequence mDesc;
    @StringRes
    private int mDescRes = -1;

    protected Context mContext;

    public EmptyDelegate(@DrawableRes int resId, CharSequence desc) {
        this.mResId = resId;
        this.mDesc = desc;
    }

    public EmptyDelegate(@DrawableRes int resId, @StringRes int desc) {
        this.mResId = resId;
        this.mDescRes = desc;
    }

    public EmptyDelegate(@DrawableRes int resId) {
        this(resId, null);
    }

    public EmptyDelegate(CharSequence desc) {
        this(-1, desc);
    }

    public EmptyDelegate() {
        this(-1, null);
    }

    @Override
    public EmptyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_empty, parent, false);
        return new EmptyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemArray itemArray, @NonNull EmptyViewHolder vh, int position) {
        if (mDescRes != -1) {
            mDesc = mContext.getResources().getString(mDescRes);
        }
        if (addData(vh, mResId, mDesc)) {
            EmptyData data = (EmptyData) itemArray.get(position);
            if (data != null) {
                addData(vh, data.mResId, data.mDesc);
            }
        }
    }

    private boolean addData(EmptyViewHolder vh, int resId, CharSequence desc) {
        boolean next = true;
        if (resId == -1) {
            vh.mEmptyImg.setVisibility(View.GONE);
        } else {
            vh.mEmptyImg.setVisibility(View.VISIBLE);
            vh.mEmptyImg.setImageResource(resId);
            next = false;
        }
        if (TextUtils.isEmpty(desc)) {
            vh.mEmptyTV.setVisibility(View.GONE);
        } else {
            vh.mEmptyTV.setVisibility(View.VISIBLE);
            vh.mEmptyTV.setText(desc);
            next = false;
        }
        return next;
    }

    protected class EmptyViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView mEmptyImg;
        AppCompatTextView mEmptyTV;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            mEmptyImg = (AppCompatImageView) itemView.findViewById(R.id.empty_img);
            mEmptyTV = (AppCompatTextView) itemView.findViewById(R.id.empty_tv);
        }
    }
}
