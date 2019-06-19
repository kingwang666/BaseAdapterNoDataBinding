package com.wang.baseadapternodatabinding.adapter.delegate;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wang.baseadapter.delegate.AdapterDelegate;
import com.wang.baseadapter.model.ItemArray;
import com.wang.baseadapternodatabinding.R;
import com.wang.baseadapternodatabinding.model.Dog;

/**
 * Created by wang
 * on 2016/6/12
 */
public class DogDelegate extends AdapterDelegate<DogDelegate.DogViewHolder> {

    @Override
    public DogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dog, parent, false);
        return new DogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemArray itemArray, DogViewHolder vh, int position) {
        Dog dog = (Dog) itemArray.get(position).getData();
        vh.nameTV.setText("you are dog " + dog.name);
    }

    class DogViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV;

        public DogViewHolder(View itemView) {
            super(itemView);
            nameTV = (TextView) itemView.findViewById(R.id.name_tv);
        }
    }
}
