package com.wang.baseadapter;


import android.animation.AnimatorSet;
import android.support.annotation.IntDef;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.wang.baseadapter.animation.AlphaInAnimation;
import com.wang.baseadapter.animation.BaseAnimation;
import com.wang.baseadapter.animation.ScaleInAnimation;
import com.wang.baseadapter.animation.SlideInBottomAnimation;
import com.wang.baseadapter.animation.SlideInLeftAnimation;
import com.wang.baseadapter.animation.SlideInRightAnimation;
import com.wang.baseadapter.delegate.AdapterDelegatesManager;
import com.wang.baseadapter.delegate.LoadingDelegate;
import com.wang.baseadapter.model.RecyclerViewItemArray;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;


public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    protected AdapterDelegatesManager delegatesManager;

    protected RecyclerViewItemArray itemArray;


    public BaseRecyclerViewAdapter(RecyclerViewItemArray itemArray) {
        this(itemArray, new AdapterDelegatesManager());
    }

    public BaseRecyclerViewAdapter(RecyclerViewItemArray itemArray, AdapterDelegatesManager delegatesManager) {
        if (delegatesManager == null) {
            throw new NullPointerException("AdapterDelegatesManager is null");
        }
        this.itemArray = itemArray == null ? new RecyclerViewItemArray() : itemArray;
        this.delegatesManager = delegatesManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(itemArray, holder, position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        delegatesManager.onBindViewHolder(itemArray, holder, position, payloads);
    }

    @Override
    public int getItemViewType(int position) {
        return itemArray.get(position).getDataType();
    }

    @Override
    public int getItemCount() {
        return itemArray.size();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        delegatesManager.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        return delegatesManager.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        delegatesManager.onViewDetachedFromWindow(holder);
    }

    /**
     * Get the items / data source of this adapter
     *
     * @return The items / data source
     */
    public RecyclerViewItemArray getItems() {
        return itemArray;
    }

    /**
     * 载入数据
     * @param itemArray
     */
    public void setItems(RecyclerViewItemArray itemArray) {
        this.itemArray = itemArray;
        notifyDataSetChanged();
    }

    /**
     * 当{@link LoadingDelegate}的mOpenMore 是true时，没错自动加载更多需调用此方法
     * @param type 数据type
     * @param datas 需插入的数据
     * @param isHaveMore 是否还有数据
     * @param <E>
     */
    public <E> void notifyAfterLoadMore(int type, List<E> datas, boolean isHaveMore) {
        if (itemArray.findFirstTypePosition(TYPE_LOADING) != -1 && itemArray.findFirstTypePosition(TYPE_FOOTER) != -1){
            itemArray.addAllAtPosition(itemArray.size() - 2, type, datas);
        }
        else if (itemArray.findFirstTypePosition(TYPE_LOADING) != -1 || itemArray.findFirstTypePosition(TYPE_FOOTER) != -1){
            itemArray.addAllAtPosition(itemArray.size() - 1, type, datas);
        }
        else {
            itemArray.addAllAtLast(type, datas);
        }
        notifyAfterLoadMore(isHaveMore);
    }

    /**
     * 当{@link LoadingDelegate}的mOpenMore 是true时，没错自动加载更多需调用此方法
     * @param isHaveMore 是否还有数据
     */
    public void notifyAfterLoadMore(boolean isHaveMore){
        try {
            ((LoadingDelegate)delegatesManager.getDelegateForViewType(TYPE_LOADING)).notifyAfterLoadMore(isHaveMore);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            notifyDataSetChanged();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        int type = holder.getItemViewType();
        if (type >= TYPE_HEADER) {
            setFullSpan(holder);
        }
        delegatesManager.onViewAttachedToWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager){
            final GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
            if (manager.getSpanSizeLookup() instanceof GridLayoutManager.DefaultSpanSizeLookup){
                manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        int type = itemArray.get(position).getDataType();
                        if (type >= TYPE_HEADER){
                            return manager.getSpanCount(); //宽度为整个recycler的宽度
                        }
                        return 1;
                    }
                });
            }
        }
    }

    /**
     * 设置StaggeredGridLayoutManager的中当前holder视图宽度为整个recyclerView
     * @param holder
     */
    protected void setFullSpan(RecyclerView.ViewHolder holder) {
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }

    public void resetAnimPosition(){
        delegatesManager.resetAnimPosition();
    }

}
