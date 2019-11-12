package com.wang.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 2019/9/6
 * Author: bigwang
 * Description:
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutId) {
        this(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public Context getContext() {
        return itemView.getContext();
    }


}
