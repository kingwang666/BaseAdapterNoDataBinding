package com.wang.baseadapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.baseadapter.listener.DefaultItemCallback;
import com.wang.baseadapter.model.ItemArray;
import com.wang.baseadapter.model.TypeData;

import java.util.List;

/**
 * Author: wangxiaojie6
 * Date: 2018/7/19
 */
abstract public class DiffSyncAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements IItemArrayAdapter {

    private final DiffUtil.ItemCallback<TypeData> mCallback;
    protected ItemArray mItemArray;

    public DiffSyncAdapter() {
        this(null);
    }

    public DiffSyncAdapter(@Nullable DiffUtil.ItemCallback<TypeData> callback) {
        mCallback = callback == null ? new DefaultItemCallback() : callback;
        mItemArray = new ItemArray();
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

        final List<TypeData> oldList = mItemArray;
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
