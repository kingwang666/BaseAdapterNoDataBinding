package com.wang.baseadapternodatabinding.adapter;

import com.wang.baseadapter.BaseRecyclerViewAdapter;
import com.wang.baseadapter.model.RecyclerViewItemArray;
import com.wang.baseadapternodatabinding.adapter.delegate.ChapterDelegate;
import com.wang.baseadapternodatabinding.adapter.delegate.SectionDelegate;
import com.wang.baseadapternodatabinding.adapter.delegate.SpaceDelegate;
import com.wang.baseadapternodatabinding.interfaces.OnRecyclerViewClickListener;

/**
 * Created by wang
 * on 2016/11/11
 */

public class StickyHeaderAdapter extends BaseRecyclerViewAdapter {

    public static final int TYPE_CHAPTER = 1;
    public static final int TYPE_SECTION = 2;
    public static final int TYPE_SPACE = 3;

    public StickyHeaderAdapter(RecyclerViewItemArray itemArray, OnRecyclerViewClickListener listener) {
        super(itemArray);
        delegatesManager.addDelegate(TYPE_CHAPTER, new ChapterDelegate(listener));
        delegatesManager.addDelegate(TYPE_SECTION, new SectionDelegate(listener));
        delegatesManager.addDelegate(TYPE_SPACE, new SpaceDelegate());
    }
}
