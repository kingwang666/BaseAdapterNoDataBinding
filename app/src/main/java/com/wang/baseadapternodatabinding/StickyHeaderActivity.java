package com.wang.baseadapternodatabinding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wang.baseadapter.StickyHeaderDecoration;
import com.wang.baseadapter.listener.OnHeaderClickListener;
import com.wang.baseadapter.listener.StickyHeaderTouchListener;
import com.wang.baseadapter.model.ItemData;
import com.wang.baseadapter.model.RecyclerViewItemArray;
import com.wang.baseadapter.widget.WaveSideBarView;
import com.wang.baseadapternodatabinding.adapter.StickyHeaderAdapter;
import com.wang.baseadapternodatabinding.interfaces.OnRecyclerViewClickListener;
import com.wang.baseadapternodatabinding.model.Chapter;
import com.wang.baseadapternodatabinding.model.Section;

/**
 * Created by wang
 * on 2016/11/11
 */

public class StickyHeaderActivity extends AppCompatActivity implements OnRecyclerViewClickListener, OnHeaderClickListener {

    private RecyclerView mRecyclerView;
    private WaveSideBarView mSideBarView;
    private RecyclerViewItemArray mItemArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_header);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSideBarView = (WaveSideBarView) findViewById(R.id.side_view);
        initArray();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new StickyHeaderAdapter(mItemArray, this));
        StickyHeaderDecoration decoration = new StickyHeaderDecoration(StickyHeaderAdapter.TYPE_CHAPTER);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.addOnItemTouchListener(new StickyHeaderTouchListener(this, decoration, this));
    }

    private void initArray() {
        mItemArray = new RecyclerViewItemArray();
        mItemArray.add(new ItemData<>(StickyHeaderAdapter.TYPE_SECTION, new Section("test")));
        for (int i = 1; i < 15; i++) {
            Chapter chapter = new Chapter("第" + i + "章", 9);
            for (int j = 1; j < 10; j++) {
                chapter.sections.add(new Section(i + "-" + j));
            }
            mItemArray.add(new ItemData<>(StickyHeaderAdapter.TYPE_CHAPTER, chapter));
        }
    }

    @Override
    public void onHeader(int viewType, int position) {
        onChapterClick(position);
    }

    @Override
    public void onClick(int viewType, int position, Object data) {
        switch (viewType) {
            case StickyHeaderAdapter.TYPE_CHAPTER:
                onChapterClick(position);
                break;
            case StickyHeaderAdapter.TYPE_SECTION:
                break;
        }
    }

    private void onChapterClick(int position) {
        Chapter chapter = (Chapter) mItemArray.get(position).getData();
        if (chapter.isOpen) {
            mItemArray.removeAllAtPosition(position + 1, chapter.sectionSize);
            chapter.isOpen = false;
            mRecyclerView.getAdapter().notifyItemChanged(position);
            mRecyclerView.getAdapter().notifyItemRangeRemoved(position + 1, chapter.sectionSize);
        } else {
            mItemArray.addAllAtPosition(position + 1, StickyHeaderAdapter.TYPE_SECTION, chapter.sections);
            chapter.isOpen = true;
            mRecyclerView.getAdapter().notifyItemChanged(position);
            mRecyclerView.getAdapter().notifyItemRangeInserted(position + 1, chapter.sectionSize);
        }
    }
}
