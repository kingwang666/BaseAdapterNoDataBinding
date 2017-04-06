package com.wang.baseadapternodatabinding.adapter.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wang.baseadapter.delegate.AdapterDelegate;
import com.wang.baseadapter.model.RecyclerViewItemArray;
import com.wang.baseadapternodatabinding.R;
import com.wang.baseadapternodatabinding.interfaces.OnRecyclerViewClickListener;
import com.wang.baseadapternodatabinding.model.Chapter;

/**
 * Created by wang
 * on 2016/11/11
 */

public class ChapterDelegate extends AdapterDelegate<ChapterDelegate.ChapterViewHolder> {

    private OnRecyclerViewClickListener mListener;

    public ChapterDelegate(OnRecyclerViewClickListener listener) {
        mListener = listener;
    }

    @Override
    public ChapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
        return new ChapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemArray itemArray, ChapterViewHolder vh, int position) {
        Chapter chapter = (Chapter) itemArray.get(position).getData();
        vh.nameTV.setText(chapter.name + " " + (chapter.isOpen ? "open" : "close"));
    }

    class ChapterViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV;

        public ChapterViewHolder(View itemView) {
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
