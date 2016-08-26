package com.wang.baseadapternodatabinding;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wang.baseadapter.delegate.AdapterDelegate;
import com.wang.baseadapter.model.RecyclerViewItemArray;
import com.wang.baseadapternodatabinding.model.Cat;

/**
 * Created by jiudeng009 on 2016/6/12.
 */
public class CatDelegate implements AdapterDelegate {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cat, parent, false);
        return new CatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemArray itemArray, RecyclerView.ViewHolder holder, int position) {
        CatViewHolder vh = (CatViewHolder) holder;
        Cat cat = (Cat) itemArray.get(position).getData();
        vh.nameTV.setText("you are cat " + cat.name);
    }

    class CatViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV;

        public CatViewHolder(View itemView) {
            super(itemView);
            nameTV = (TextView) itemView.findViewById(R.id.name_tv);
        }
    }
}
