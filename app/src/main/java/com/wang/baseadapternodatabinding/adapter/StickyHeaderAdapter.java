package com.wang.baseadapternodatabinding.adapter;

import com.wang.baseadapter.BaseRecyclerViewAdapter;
import com.wang.baseadapter.delegate.AdapterDelegatesManager;
import com.wang.baseadapter.model.ItemArray;
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

    public StickyHeaderAdapter(ItemArray itemArray, OnRecyclerViewClickListener listener) {
        super(itemArray);
        delegatesManager.openLoadAnimation(AdapterDelegatesManager.SLIDE_IN_RIGHT);
        delegatesManager.addNoAnimType(TYPE_CHAPTER);
        delegatesManager.addDelegate(TYPE_CHAPTER, new ChapterDelegate(listener));
        delegatesManager.addDelegate(TYPE_SECTION, new SectionDelegate(listener));
        delegatesManager.addDelegate(TYPE_SPACE, new SpaceDelegate());
    }
}
