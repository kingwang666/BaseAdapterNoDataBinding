package com.wang.baseadapter.model;

/**
 * Created on 2019/8/5.
 * Author: bigwang
 * Description:
 */
public class ItemData implements TypeData {

    public transient int dataType;

    public ItemData(int dataType) {
        this.dataType = dataType;
    }

    @Override
    public int getDataType() {
        return dataType;
    }
}
