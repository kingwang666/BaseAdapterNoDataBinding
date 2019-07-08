package com.wang.baseadapternodatabinding.model;

import com.wang.baseadapter.model.ItemData;
import com.wang.baseadapternodatabinding.adapter.StickyHeaderAdapter;

/**
 * Created by wang
 * on 2016/11/11
 */

public class Section extends ItemData {

    public String name;

    public Section(String name) {
        super(StickyHeaderAdapter.TYPE_SECTION);
        this.name = name;
    }
}
