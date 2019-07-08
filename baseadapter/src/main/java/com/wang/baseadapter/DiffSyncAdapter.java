package com.wang.baseadapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.baseadapter.listener.DefaultItemCallback;
import com.wang.baseadapter.model.ItemArray;
import com.wang.baseadapter.model.ItemData;

import java.util.List;

/**
 * Author: wangxiaojie6
 * Date: 2018/7/19
 */
abstract public class DiffSyncAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements IItemArrayAdapter {

    private final DiffUtil.ItemCallback<ItemData> mCallback;
    protected ItemArray<ItemData> mItemArray;

    public DiffSyncAdapter() {
        this(null);
    }

    public DiffSyncAdapter(@Nullable DiffUtil.ItemCallback<ItemData> callback) {
        mCallback = callback == null ? new DefaultItemCallback() : callback;
        mItemArray = new ItemArray<>();
    }

    @Override
    public int getItemCount() {
        return mItemArray.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItemArray.get(position).dataType;
    }

    /**
     * Get the items / data source of this adapter
     *
     * @return The items / data source
     */
    @Override
    public ItemArray<ItemData> getItems() {
        return mItemArray;
    }

    public void submitList(final ItemArray<ItemData> newList) {
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

        final List<ItemData> oldList = mItemArray;
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
