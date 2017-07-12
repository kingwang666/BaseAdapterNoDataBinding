package com.wang.baseadapternodatabinding.adapter;


import android.support.v7.widget.RecyclerView;

import com.wang.baseadapter.BaseRecyclerViewAdapter;
import com.wang.baseadapter.RecyclerSwipeAdapter;
import com.wang.baseadapter.delegate.LoadingDelegate;
import com.wang.baseadapter.model.ItemArray;
import com.wang.baseadapternodatabinding.adapter.delegate.CatDelegate;
import com.wang.baseadapternodatabinding.adapter.delegate.DogDelegate;
import com.wang.baseadapternodatabinding.adapter.delegate.EmptyDelegate;
import com.wang.baseadapternodatabinding.R;

/**
 * Created on 2016/6/12.
 * Author: wang
 */
public class MultiTypeAdapter extends RecyclerSwipeAdapter {

    public static final int TYPE_CAT = 1;
    public static final int TYPE_DOG = 2;



    public MultiTypeAdapter(ItemArray itemArray, LoadingDelegate.OnRequestMoreListener listener) {
        super(itemArray);
        delegatesManager.addDelegate(TYPE_CAT, new CatDelegate());
        delegatesManager.addDelegate(TYPE_DOG, new DogDelegate());
        delegatesManager.addDelegate(TYPE_LOADING, new LoadingDelegate(listener, true));
        delegatesManager.addDelegate(TYPE_EMPTY, new EmptyDelegate(R.mipmap.ic_launcher));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}
