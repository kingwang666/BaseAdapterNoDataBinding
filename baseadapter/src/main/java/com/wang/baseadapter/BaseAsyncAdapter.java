package com.wang.baseadapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.baseadapter.delegate.AdapterDelegatesManager;
import com.wang.baseadapter.listener.DefaultItemCallback;
import com.wang.baseadapter.model.ItemArray;
import com.wang.baseadapter.model.ItemData;
import com.wang.baseadapter.util.AsyncListDiffer;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Author: wangxiaojie6
 * Date: 2018/7/16
 */
public abstract class BaseAsyncAdapter extends BaseDelegateAdapter {

    private final AsyncListDiffer mDiffer;

    protected final ReentrantReadWriteLock mLock;

    public BaseAsyncAdapter() {
        this(null, null);
    }

    public BaseAsyncAdapter(@Nullable DiffUtil.ItemCallback<ItemData> callback) {
        this(null, callback);
    }

    public BaseAsyncAdapter(@Nullable AdapterDelegatesManager delegatesManager, @Nullable DiffUtil.ItemCallback<ItemData> callback) {
        super(delegatesManager);
        mLock = new ReentrantReadWriteLock();
        mDiffer = new AsyncListDiffer(this, callback == null ? new DefaultItemCallback() : callback, mLock);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(mDiffer.getCurrentList(), holder, position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        delegatesManager.onBindViewHolder(mDiffer.getCurrentList(), holder, position, payloads);
    }

    @Override
    public int getItemViewType(int position) {
        List<ItemData> current = mDiffer.getCurrentList();
        return current.get(position).dataType;
    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

    /**
     * 载入数据
     *
     */
    public void submitList(ItemArray<ItemData> itemArray) {
        mDiffer.submitList(itemArray);
    }

    @Override
    public ItemArray<ItemData> getItems() {
        return mDiffer.getCurrentList();
    }

    public void addItem(int position, ItemData data){
        mLock.writeLock().lock();
        try {
            ItemArray<ItemData> array = mDiffer.getCurrentList();
            array.add(position, data);
            notifyItemInserted(position);
        }finally {
            mLock.writeLock().unlock();
        }
    }

    public void addItems(int position, List<ItemData> data){
        mLock.writeLock().lock();
        try {
            ItemArray<ItemData> array = mDiffer.getCurrentList();
            array.addAll(position, data);
            notifyItemRangeInserted(position, data.size());
        }finally {
            mLock.writeLock().unlock();
        }
    }

}
