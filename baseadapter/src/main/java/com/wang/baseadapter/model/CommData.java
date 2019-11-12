package com.wang.baseadapter.model;

/**
 * Created on 2019/7/9.
 * Author: bigwang
 * Description:
 */
public class CommData extends ItemData {


    public Object data;

    public CommData(int dataType, Object data) {
        super(dataType);
        this.data = data;
    }

    public CommData(int dataType) {
        super(dataType);
    }

}
