package com.wang.baseadapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.baseadapter.delegate.AdapterDelegatesManager;
import com.wang.baseadapter.listener.DefaultItemCallback;
import com.wang.baseadapter.model.ItemArray;
import com.wang.baseadapter.model.TypeData;

import java.util.List;

/**
 * Author: wangxiaojie6
 * Date: 2018/7/16
 */
public abstract class BaseSyncAdapter extends BaseDelegateAdapter {

    private final DiffUtil.ItemCallback<TypeData> mCallback;
    protected ItemArray mItemArray;

    public BaseSyncAdapter() {
        this(null, null);
    }

    public BaseSyncAdapter(@Nullable DiffUtil.ItemCallback<TypeData> callback) {
        this(null, callback);
    }

    public BaseSyncAdapter(@Nullable AdapterDelegatesManager delegatesManager, @Nullable DiffUtil.ItemCallback<TypeData> callback) {
        super(delegatesManager);
        mCallback = callback == null ? new DefaultItemCallback() : callback;
        mItemArray = new ItemArray();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(mItemArray, holder, position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        delegatesManager.onBindViewHolder(mItemArray, holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mItemArray.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItemArray.get(position).getDataType();
    }

    /**
     * Get the items / data source of this adapter
     *
     * @return The items / data source
     */
    @Override
    public ItemArray getItems() {
        return mItemArray;
    }

    public void submitList(final ItemArray newList) {
        if (newList == mItemArray) {
            // nothing to do
            return;
        }

        // fast simple remove all
        if (newList == null) {
            //noinspection ConstantConditions
            int countRemoved = mItemArray.size();
            mItemArray.clear();
            // notify last, after list is updated
            notifyItemRangeRemoved(0, countRemoved);
            return;
        }

        if (newList.size() == 0) {
            //noinspection ConstantConditions
            int countRemoved = mItemArray.size();
            mItemArray = newList;
            // notify last, after list is updated
            notifyItemRangeRemoved(0, countRemoved);
            return;
        }

        // fast simple first insert
        if (mItemArray.size() == 0) {
            mItemArray = newList;
            // notify last, after list is updated
            notifyItemRangeInserted(0, newList.size());
            return;
        }

        final List<? extends TypeData> oldList = mItemArray;
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldList.size();
            }

            @Override
            public int getNewListSize() {
                return newList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return mCallback.areItemsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return mCallback.areContentsTheSame(oldList.get(oldItemPosition), newList.get(newItemPosition));
            }

            @Nullable
            @Override
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                return mCallback.getChangePayload(oldList.get(oldItemPosition), newList.get(newItemPosition));
            }
        });
        mItemArray = newList;
        result.dispatchUpdatesTo(this);
    }
}
