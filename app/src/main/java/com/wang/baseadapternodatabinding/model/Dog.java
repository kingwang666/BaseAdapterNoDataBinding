package com.wang.baseadapternodatabinding.model;

import com.wang.baseadapter.model.ItemData;
import com.wang.baseadapternodatabinding.adapter.MultiTypeAdapter;

/**
 * Created on 2016/6/12.
 * Author: wang
 */
public class Dog extends ItemData {

    public String name;

    public Dog(String name) {
        super(MultiTypeAdapter.TYPE_DOG);
        this.name = name;
    }
}
