package com.wang.baseadapternodatabinding.adapter.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wang.baseadapter.delegate.AdapterDelegate;
import com.wang.baseadapter.model.RecyclerViewItemArray;
import com.wang.baseadapternodatabinding.R;

/**
 * Created by wang
 * on 2016/11/11
 */

public class SpaceDelegate implements AdapterDelegate {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_space, parent, false);
        return new RecyclerView.ViewHolder(itemView) {};
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemArray itemArray, RecyclerView.ViewHolder viewHolder, int position) {

    }
}
