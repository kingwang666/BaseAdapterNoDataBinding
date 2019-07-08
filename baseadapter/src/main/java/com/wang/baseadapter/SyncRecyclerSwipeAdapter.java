package com.wang.baseadapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.baseadapter.delegate.AdapterDelegatesManager;
import com.wang.baseadapter.model.ItemData;
import com.wang.baseadapter.util.SwipeAdapterInterface;
import com.wang.baseadapter.util.SwipeItemMangerImpl;
import com.wang.baseadapter.util.SwipeItemMangerInterface;
import com.wang.baseadapter.widget.SwipeItemView;

import java.util.List;

/**
 * Author: wangxiaojie6
 * Date: 2018/7/17
 */
public abstract class SyncRecyclerSwipeAdapter extends BaseSyncAdapter implements SwipeItemMangerInterface, SwipeAdapterInterface {

    protected SwipeItemMangerImpl mItemManger = new SwipeItemMangerImpl(this);

    public SyncRecyclerSwipeAdapter(@Nullable DiffUtil.ItemCallback<ItemData> callback) {
        super(callback);
    }

    public SyncRecyclerSwipeAdapter(@Nullable AdapterDelegatesManager delegatesManager, @Nullable DiffUtil.ItemCallback<ItemData> callback) {
        super(delegatesManager, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (holder.itemView instanceof SwipeItemView){
            mItemManger.bind((SwipeItemView) holder.itemView, position);
        }
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.itemView instanceof SwipeItemView){
            mItemManger.bind((SwipeItemView) holder.itemView, position);
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void openItem(int position) {
        mItemManger.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        mItemManger.closeItem(position);
    }

    @Override
    public void closeAllExcept(SwipeItemView layout) {
        mItemManger.closeAllExcept(layout);
    }

    @Override
    public void closeAllItems() {
        mItemManger.closeAllItems();
    }

    @Override
    public List<Integer> getOpenItems() {
        return mItemManger.getOpenItems();
    }

    @Override
    public List<SwipeItemView> getOpenLayouts() {
        return mItemManger.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(SwipeItemView layout) {
        mItemManger.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return mItemManger.isOpen(position);
    }

    @Override
    public SwipeItemView.Mode getMode() {
        return mItemManger.getMode();
    }

    @Override
    public void setMode(SwipeItemView.Mode mode) {
        mItemManger.setMode(mode);
    }

}
