package com.wang.baseadapternodatabinding.model;

import com.wang.baseadapter.model.ItemData;
import com.wang.baseadapternodatabinding.adapter.MultiTypeAdapter;

/**
 * Created on 2016/6/12.
 * Author: wang
 */
public class Cat extends ItemData {
    public String name;

    public Cat(String name) {
        super(MultiTypeAdapter.TYPE_CAT);
        this.name = name;
    }
}
