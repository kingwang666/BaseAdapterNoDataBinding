package com.wang.baseadapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.baseadapter.delegate.AdapterDelegatesManager;
import com.wang.baseadapter.delegate.LoadingDelegate;
import com.wang.baseadapter.model.ItemArray;
import com.wang.baseadapter.model.TypeData;

import java.util.List;


public abstract class BaseRecyclerViewAdapter extends BaseDelegateAdapter {
    
    protected ItemArray itemArray;

    public BaseRecyclerViewAdapter(ItemArray itemArray) {
        this(null, itemArray);
    }

    public BaseRecyclerViewAdapter(@Nullable AdapterDelegatesManager delegatesManager, ItemArray itemArray) {
        super(delegatesManager);
        this.itemArray = itemArray;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        delegatesManager.onBindViewHolder(itemArray, holder, position, payloads);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(itemArray, holder, position);
    }


    @Override
    public int getItemViewType(int position) {
        return itemArray.get(position).getDataType();
    }

    @Override
    public int getItemCount() {
        return itemArray.size();
    }
    
    /**
     * Get the items / data source of this adapter
     *
     * @return The items / data source
     */
    @Override
    public ItemArray getItems() {
        return itemArray;
    }

    /**
     * 载入数据
     * @param mItemArray
     */
    public void setItems(ItemArray mItemArray) {
        this.itemArray = mItemArray;
        notifyDataSetChanged();
    }

    /**
     * 当{@link LoadingDelegate}的mOpenMore 是true时，没错自动加载更多需调用此方法
     * @param datas 需插入的数据
     * @param isHaveMore 是否还有数据
     */
    public void notifyAfterLoadMore(List<? extends TypeData> datas, boolean isHaveMore) {
        if (itemArray.findFirstTypePosition(TYPE_LOADING) != -1 && itemArray.findFirstTypePosition(TYPE_FOOTER) != -1){
            itemArray.addAll(itemArray.size() - 2, datas);
        }
        else if (itemArray.findFirstTypePosition(TYPE_LOADING) != -1 || itemArray.findFirstTypePosition(TYPE_FOOTER) != -1){
            itemArray.addAll(itemArray.size() - 1, datas);
        }
        else {
            itemArray.addAll(datas);
        }
        notifyAfterLoadMore(isHaveMore);
    }

    /**
     * 当{@link LoadingDelegate}的mOpenMore 是true时，没错自动加载更多需调用此方法
     * @param isHaveMore 是否还有数据
     */
    public void notifyAfterLoadMore(boolean isHaveMore){
        try {
            ((LoadingDelegate) delegatesManager.getDelegateForViewType(TYPE_LOADING)).notifyAfterLoadMore(isHaveMore);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            notifyDataSetChanged();
        }
    }

}
