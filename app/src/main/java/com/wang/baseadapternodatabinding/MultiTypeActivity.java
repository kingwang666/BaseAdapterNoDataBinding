package com.wang.baseadapternodatabinding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.wang.baseadapter.delegate.LoadingDelegate;
import com.wang.baseadapter.model.ItemData;
import com.wang.baseadapter.model.ItemArray;
import com.wang.baseadapternodatabinding.adapter.MultiTypeAdapter;
import com.wang.baseadapternodatabinding.model.Cat;
import com.wang.baseadapternodatabinding.model.Dog;


import java.util.LinkedList;
import java.util.List;

public class MultiTypeActivity extends AppCompatActivity implements LoadingDelegate.OnRequestMoreListener {

    private RecyclerView recyclerView;
    private ItemArray itemArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        initArray();
        recyclerView.setAdapter(new MultiTypeAdapter(itemArray, this));
    }

    private void initArray() {
        itemArray = new ItemArray();
        for (int i = 0; i < 20; i++) {

            if (i % 3 == 0) {
                itemArray.add(new ItemData(MultiTypeAdapter.TYPE_DOG, new Dog(i + "")));
            } else {
                itemArray.add(new ItemData(MultiTypeAdapter.TYPE_CAT, new Cat(i + "")));
            }
        }
//        itemArray.add(new ItemData<>(MultiTypeAdapter.TYPE_EMPTY, null));
        itemArray.add(new ItemData(MultiTypeAdapter.TYPE_LOADING, null));
    }

    int count = 1;

    @Override
    public void onRequestMore() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Cat> cats = new LinkedList<>();
                for (int i = 0; i < 3; i++) {
                    cats.add(new Cat("you are my love " + i));
                }
                ((MultiTypeAdapter) recyclerView.getAdapter()).notifyAfterLoadMore(MultiTypeAdapter.TYPE_CAT, cats, count < 3);
                count++;
            }
        }, 2000);
    }

}
