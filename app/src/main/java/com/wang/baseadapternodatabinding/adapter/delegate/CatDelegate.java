package com.wang.baseadapternodatabinding.adapter.delegate;


import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.wang.baseadapter.delegate.AdapterDelegate;
import com.wang.baseadapter.model.ItemArray;
import com.wang.baseadapter.model.ItemData;
import com.wang.baseadapternodatabinding.R;
import com.wang.baseadapternodatabinding.model.Cat;

/**
 * Created by wang
 * on 2016/6/12
 */
public class CatDelegate extends AdapterDelegate<CatDelegate.CatViewHolder> {


    @Override
    public CatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cat, parent, false);
        return new CatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemArray<ItemData> itemArray, CatViewHolder vh, int position) {
        Cat cat = (Cat) itemArray.get(position);
        vh.nameTV.setText("you are cat " + cat.name);
    }

    class CatViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV;

        public CatViewHolder(final View itemView) {
            super(itemView);
            nameTV = (TextView) itemView.findViewById(R.id.name_tv);
            itemView.findViewById(R.id.swipe_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "click " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
