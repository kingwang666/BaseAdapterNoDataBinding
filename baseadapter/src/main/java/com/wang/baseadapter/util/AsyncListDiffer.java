package com.wang.baseadapter.util;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.baseadapter.model.ItemArray;
import com.wang.baseadapter.model.ItemData;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Author: wangxiaojie6
 * Date: 2018/7/16
 */
public class AsyncListDiffer {

    private final ListUpdateCallback mUpdateCallback;
    private final AsyncDifferConfig<ItemData> mConfig;
    private final ReadWriteLock mLock;
    @NonNull
    private ItemArray<ItemData> mList = new ItemArray<>();

    // Max generation of currently scheduled runnable
    private int mMaxScheduledGeneration;

    /**
     * Convenience for
     * {@code AsyncListDiffer(new AdapterListUpdateCallback(adapter),
     * new AsyncDifferConfig.Builder().setDiffCallback(diffCallback).build());}
     *
     * @param adapter      Adapter to dispatch position updates to.
     * @param diffCallback ItemCallback that compares items to dispatch appropriate animations when
     * @see DiffUtil.DiffResult#dispatchUpdatesTo(RecyclerView.Adapter)
     */
    public AsyncListDiffer(@NonNull RecyclerView.Adapter adapter,
                           @NonNull DiffUtil.ItemCallback<ItemData> diffCallback,
                           @NonNull ReadWriteLock lock) {
        mUpdateCallback = new AdapterListUpdateCallback(adapter);
        mConfig = new AsyncDifferConfig.Builder<>(diffCallback).build();
        mLock = lock;
    }

    /**
     * Create a AsyncListDiffer with the provided config, and ListUpdateCallback to dispatch
     * updates to.
     *
     * @param listUpdateCallback Callback to dispatch updates to.
     * @param config             Config to define background work Executor, and DiffUtil.ItemCallback for
     *                           computing List diffs.
     * @see DiffUtil.DiffResult#dispatchUpdatesTo(RecyclerView.Adapter)
     */
    @SuppressWarnings("WeakerAccess")
    public AsyncListDiffer(@NonNull ListUpdateCallback listUpdateCallback,
                           @NonNull AsyncDifferConfig<ItemData> config,
                           @NonNull ReadWriteLock lock) {
        mUpdateCallback = listUpdateCallback;
        mConfig = config;
        mLock = lock;
    }


    /**
     * Get the current List - any diffing to present this list has already been computed and
     * dispatched via the ListUpdateCallback.
     * <p>
     * If a <code>null</code> List, or no List has been submitted, an empty list will be returned.
     * <p>
     * The returned list may not be mutated - mutations to content must be done through
     * {@link #submitList(ItemArray)}.
     *
     * @return current List.
     */
    @NonNull
    public ItemArray<ItemData> getCurrentList() {
        return mList;
    }

    /**
     * Pass a new List to the AdapterHelper. Adapter updates will be computed on a background
     * thread.
     * <p>
     * If a List is already present, a diff will be computed asynchronously on a background thread.
     * When the diff is computed, it will be applied (dispatched to the {@link ListUpdateCallback}),
     * and the new List will be swapped in.
     *
     * @param newList The new List.
     */
    @SuppressWarnings("WeakerAccess")
    @SuppressLint("RestrictedApi")
    public void submitList(final ItemArray<ItemData> newList) {
        if (newList == mList) {
            // nothing to do
            return;
        }

        // incrementing generation means any currently-running diffs are discarded when they finish
        final int runGeneration = ++mMaxScheduledGeneration;

        // fast simple remove all
        if (newList == null) {
            //noinspection ConstantConditions
            int countRemoved = mList.size();
            mList.clear();
            // notify last, after list is updated
            mUpdateCallback.onRemoved(0, countRemoved);
            return;
        }

        // fast simple first insert
        if (mList.size() == 0) {
            mList = newList;
            // notify last, after list is updated
            mUpdateCallback.onInserted(0, newList.size());
            return;
        }

        final List<ItemData> oldList = mList;
        mConfig.getBackgroundThreadExecutor().execute(new Runnable() {

            @Override
            public void run() {
                mLock.readLock().lock();
                try {
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
                            return mConfig.getDiffCallback().areItemsTheSame(
                                    oldList.get(oldItemPosition), newList.get(newItemPosition));
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            return mConfig.getDiffCallback().areContentsTheSame(
                                    oldList.get(oldItemPosition), newList.get(newItemPosition));
                        }

                        @Nullable
                        @Override
                        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                            return mConfig.getDiffCallback().getChangePayload(
                                    oldList.get(oldItemPosition), newList.get(newItemPosition));
                        }
                    });
                    mConfig.getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (mMaxScheduledGeneration == runGeneration) {
                                latchList(newList, result);
                            }
                        }
                    });
                } finally {
                    mLock.readLock().unlock();
                }
            }
        });
    }

    private void latchList(@NonNull ItemArray<ItemData> newList, @NonNull DiffUtil.DiffResult diffResult) {
        mList = newList;
        diffResult.dispatchUpdatesTo(mUpdateCallback);
    }
}
