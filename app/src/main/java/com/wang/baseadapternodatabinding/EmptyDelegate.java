package com.wang.baseadapternodatabinding;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wang.baseadapter.delegate.AdapterDelegate;
import com.wang.baseadapter.model.RecyclerViewItemArray;

/**
 * Created on 2016/6/13.
 * Author: wang
 */
public class EmptyDelegate implements AdapterDelegate {

    @DrawableRes
    private int resId;

    public EmptyDelegate(@DrawableRes int resId){
        this.resId = resId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EmptyViewHolder(parent, resId);
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemArray itemArray, RecyclerView.ViewHolder holder, int position) {

    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(ViewGroup parent, int image) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false));
            if(image != -1){
                ((ImageView) itemView.findViewById(R.id.image_view)).setImageResource(image);
            }

        }
    }
}
