package com.wang.baseadapter.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wang.baseadapter.model.RecyclerViewItemArray;



public interface AdapterDelegate<VH extends RecyclerView.ViewHolder> {


    /**
     * Creates the  {@link VH} for the given data source item
     *
     * @param parent The ViewGroup parent of the given datasource
     * @param viewType the datasource type
     * @return The new instantiated {@link VH}
     */
    VH onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * Called to bind the {@link VH} to the item of the datas source set
     *
     * @param itemArray the recycler items
     * @param position The position in the datasource
     * @param vh   The {@link VH} to bind
     */
    void onBindViewHolder(RecyclerViewItemArray itemArray, VH vh, int position);
}
