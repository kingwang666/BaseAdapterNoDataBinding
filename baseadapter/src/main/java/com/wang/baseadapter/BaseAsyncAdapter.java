package com.wang.baseadapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.baseadapter.delegate.AdapterDelegatesManager;
import com.wang.baseadapter.listener.DefaultItemCallback;
import com.wang.baseadapter.model.ItemArray;
import com.wang.baseadapter.model.TypeData;
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

    public BaseAsyncAdapter(@Nullable DiffUtil.ItemCallback<TypeData> callback) {
        this(null, callback);
    }

    public BaseAsyncAdapter(@Nullable AdapterDelegatesManager delegatesManager, @Nullable DiffUtil.ItemCallback<TypeData> callback) {
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
        List<TypeData> current = mDiffer.getCurrentList();
        return current.get(position).getDataType();
    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

    /**
     * 载入数据
     *
     */
    public void submitList(ItemArray itemArray) {
        mDiffer.submitList(itemArray);
    }

    @Override
    public ItemArray getItems() {
        return mDiffer.getCurrentList();
    }

    public void addItem(int position, TypeData data){
        mLock.writeLock().lock();
        try {
            ItemArray array = mDiffer.getCurrentList();
            array.add(position, data);
            notifyItemInserted(position);
        }finally {
            mLock.writeLock().unlock();
        }
    }

    public void addItems(int position, List<? extends TypeData> data){
        mLock.writeLock().lock();
        try {
            ItemArray array = mDiffer.getCurrentList();
            array.addAll(position, data);
            notifyItemRangeInserted(position, data.size());
        }finally {
            mLock.writeLock().unlock();
        }
    }

}
