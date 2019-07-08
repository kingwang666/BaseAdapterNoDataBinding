package com.wang.baseadapter.listener;

import androidx.recyclerview.widget.DiffUtil;

import com.wang.baseadapter.model.ItemData;

/**
 * Author: wangxiaojie6
 * Date: 2018/7/17
 */
public class DefaultItemCallback extends DiffUtil.ItemCallback<ItemData> {

    @Override
    public boolean areItemsTheSame(ItemData oldItem, ItemData newItem) {
        return oldItem.dataType == newItem.dataType;
    }

    @Override
    public boolean areContentsTheSame(ItemData oldItem, ItemData newItem) {
        return oldItem.equals(newItem);
    }
}
