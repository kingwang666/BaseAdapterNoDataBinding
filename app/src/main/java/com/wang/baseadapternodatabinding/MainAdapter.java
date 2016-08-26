package com.wang.baseadapternodatabinding;


import com.wang.baseadapter.BaseRecyclerViewAdapter;
import com.wang.baseadapter.delegate.LoadingDelegate;
import com.wang.baseadapter.model.RecyclerViewItemArray;

/**
 * Created on 2016/6/12.
 * Author: wang
 */
public class MainAdapter extends BaseRecyclerViewAdapter {

    public static final int TYPE_CAT = 1;
    public static final int TYPE_DOG = 2;



    public MainAdapter(RecyclerViewItemArray itemArray, LoadingDelegate.OnRequestMoreListener listener) {
        super(itemArray);
        delegatesManager.addDelegate(TYPE_CAT, new CatDelegate());
        delegatesManager.addDelegate(TYPE_DOG, new DogDelegate());
        delegatesManager.addDelegate(TYPE_LOADING, new LoadingDelegate(listener, true));
        delegatesManager.addDelegate(TYPE_EMPTY, new EmptyDelegate(R.mipmap.ic_launcher));
    }
}
