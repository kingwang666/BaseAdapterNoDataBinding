package com.wang.baseadapter.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wang.baseadapter.model.RecyclerViewItemArray;



public interface AdapterDelegate {


    /**
     * Creates the  {@link RecyclerView.ViewHolder} for the given data source item
     *
     * @param parent The ViewGroup parent of the given datasource
     * @param viewType the datasource type
     * @return The new instantiated {@link RecyclerView.ViewHolder}
     */
    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * Called to bind the {@link RecyclerView.ViewHolder} to the item of the datas source set
     *
     * @param itemArray the recycler items
     * @param position The position in the datasource
     * @param holder   The {@link RecyclerView.ViewHolder} to bind
     */
    void onBindViewHolder(RecyclerViewItemArray itemArray, RecyclerView.ViewHolder holder, int position);
}
