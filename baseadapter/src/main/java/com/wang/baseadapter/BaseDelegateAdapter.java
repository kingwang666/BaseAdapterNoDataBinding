package com.wang.baseadapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.ViewGroup;

import com.wang.baseadapter.delegate.AdapterDelegatesManager;

import java.lang.ref.WeakReference;


public abstract class BaseDelegateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IItemArrayAdapter {

    /**
     * 加载更多type
     */
    public static final int TYPE_LOADING = Integer.MAX_VALUE - 1;
    /**
     * footer type
     */
    public static final int TYPE_FOOTER = Integer.MAX_VALUE - 2;
    /**
     * 空viewHolder type
     */
    public static final int TYPE_EMPTY = Integer.MAX_VALUE - 3;
    /**
     * header type
     */
    public static final int TYPE_HEADER = Integer.MAX_VALUE - 4;

    protected final AdapterDelegatesManager delegatesManager;

    public BaseDelegateAdapter(@Nullable AdapterDelegatesManager delegatesManager) {
        this.delegatesManager = delegatesManager == null ? new AdapterDelegatesManager() : delegatesManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        delegatesManager.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder) {
        return delegatesManager.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        delegatesManager.onViewDetachedFromWindow(holder);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        int type = holder.getItemViewType();
        if (type >= TYPE_HEADER) {
            setFullSpan(holder);
        }
        delegatesManager.onViewAttachedToWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
            GridLayoutManager.SpanSizeLookup spanSizeLookup = manager.getSpanSizeLookup();
            if (spanSizeLookup instanceof GridLayoutManager.DefaultSpanSizeLookup) {
                manager.setSpanSizeLookup(new DelegateSpanSizeLookup(this, manager.getSpanCount()));
            } else if (spanSizeLookup instanceof DelegateSpanSizeLookup){
                ((DelegateSpanSizeLookup) spanSizeLookup).setAdapter(this);
                ((DelegateSpanSizeLookup) spanSizeLookup).setDefaultSize(manager.getSpanCount());
            }
        }
    }

    /**
     * 设置StaggeredGridLayoutManager的中当前holder视图宽度为整个recyclerView
     *
     * @param holder
     */
    protected void setFullSpan(RecyclerView.ViewHolder holder) {
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }

    public void resetAnimPosition() {
        delegatesManager.resetAnimPosition();
    }

    public static class DelegateSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

        private WeakReference<RecyclerView.Adapter> mAdapter;
        private int mDefaultSize;

        public DelegateSpanSizeLookup() {
            mDefaultSize = 1;
        }

        public DelegateSpanSizeLookup(RecyclerView.Adapter adapter, int defaultSize) {
            mAdapter = new WeakReference<>(adapter);
            mDefaultSize = defaultSize;
        }

        public void setAdapter(RecyclerView.Adapter adapter) {
            mAdapter.clear();
            mAdapter = new WeakReference<>(adapter);
        }

        public void setDefaultSize(int defaultSize) {
            mDefaultSize = defaultSize;
        }

        @Override
        public int getSpanSize(int position) {
            if (mAdapter == null){
                return 1;
            }
            RecyclerView.Adapter adapter = mAdapter.get();
            if (adapter != null) {
                int type = adapter.getItemViewType(position);
                if (type >= TYPE_HEADER) {
                    return mDefaultSize; //宽度为整个recycler的宽度
                }
            }
            return 1;
        }
    }
}
