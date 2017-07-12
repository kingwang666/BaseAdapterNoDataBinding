package com.wang.baseadapternodatabinding.adapter.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wang.baseadapter.delegate.AdapterDelegate;
import com.wang.baseadapter.model.ItemArray;
import com.wang.baseadapternodatabinding.R;
import com.wang.baseadapternodatabinding.interfaces.OnRecyclerViewClickListener;
import com.wang.baseadapternodatabinding.model.Section;

/**
 * Created by wang
 * on 2016/11/11
 */

public class SectionDelegate extends AdapterDelegate<SectionDelegate.SectionViewHolder> {

    private OnRecyclerViewClickListener mListener;

    public SectionDelegate(OnRecyclerViewClickListener listener) {
        mListener = listener;
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false);
        return new SectionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemArray itemArray, SectionViewHolder vh, int position) {
        Section section = (Section) itemArray.get(position).getData();
        vh.nameTV.setText(section.name);
    }

    class SectionViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV;

        public SectionViewHolder(View itemView) {
            super(itemView);
            nameTV = (TextView) itemView.findViewById(R.id.name_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(getItemViewType(), getAdapterPosition(), null);
                }
            });
        }
    }
}
