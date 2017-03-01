package com.wang.baseadapter.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wang.baseadapter.model.RecyclerViewItemArray;

import java.util.List;


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
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     *
     * @param vh The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    void onBindViewHolder(RecyclerViewItemArray itemArray, VH vh, int position);
}
