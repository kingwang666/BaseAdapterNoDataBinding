package com.wang.baseadapter.listener;

import androidx.annotation.NonNull;
import androidx.core.util.ObjectsCompat;
import androidx.recyclerview.widget.DiffUtil;

import com.wang.baseadapter.model.TypeData;

/**
 * Author: wangxiaojie6
 * Date: 2018/7/17
 */
public class DefaultItemCallback extends DiffUtil.ItemCallback<TypeData> {

    @Override
    public boolean areItemsTheSame(@NonNull TypeData oldItem, @NonNull TypeData newItem) {
        return oldItem.getDataType() == newItem.getDataType();
    }

    @Override
    public boolean areContentsTheSame(@NonNull TypeData oldItem, @NonNull TypeData newItem) {
        return ObjectsCompat.equals(oldItem, newItem);
    }
}
