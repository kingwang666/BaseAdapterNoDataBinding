package com.wang.baseadapternodatabinding;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.widget.Toast;

import com.wang.baseadapter.listener.StickyHeaderTouchListener2;
import com.wang.baseadapter.util.GravitySnapHelper;
import com.wang.baseadapter.util.SnappingLinearLayoutManager;
import com.wang.baseadapter.itemdecoration.StickyHeaderDecoration;
import com.wang.baseadapter.listener.OnHeaderClickListener;
import com.wang.baseadapter.listener.StickyHeaderTouchListener;
import com.wang.baseadapter.model.ItemData;
import com.wang.baseadapter.model.ItemArray;
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
    private ItemArray mItemArray;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_header);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSideBarView = (WaveSideBarView) findViewById(R.id.side_view);
        initArray();
        mRecyclerView.setLayoutManager(new SnappingLinearLayoutManager(this));
        mRecyclerView.setAdapter(new StickyHeaderAdapter(mItemArray, this));
//        mRecyclerView.addOnScrollListener(new RecyclerViewListener());
        StickyHeaderDecoration decoration = new StickyHeaderDecoration(true, StickyHeaderAdapter.TYPE_CHAPTER);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.addOnItemTouchListener(new StickyHeaderTouchListener(this, decoration, this));
//        mRecyclerView.addOnItemTouchListener(new StickyHeaderTouchListener2(decoration));
//        mRecyclerView.setItemAnimator(new MyDefaultItemAnimator());
        DefaultItemAnimator animator = new DefaultItemAnimator() {
            @Override
            public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                return true;
            }
        };
        mRecyclerView.setItemAnimator(animator);
        new GravitySnapHelper(Gravity.TOP).attachToRecyclerView(mRecyclerView);
        mSideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int size = mItemArray.size();
                for (int i = 0; i < size; i++) {
                    ItemData data = (ItemData) mItemArray.get(i);
                    if (data.dataType == StickyHeaderAdapter.TYPE_CHAPTER) {
                        Chapter chapter = (Chapter) data;
                        if (chapter.name.startsWith(letter)) {
//                            LinearLayoutManager mLayoutManager =
//                                    (LinearLayoutManager) mRecyclerView.getLayoutManager();
//                            mLayoutManager.scrollToPositionWithOffset(i, 0);
                            mRecyclerView.smoothScrollToPosition(i);
                            return;
                        }
                    }
                }
            }
        });
    }



    private void initArray() {
        mItemArray = new ItemArray();
        for (int i = 1; i < 15; i++) {
            Chapter chapter = new Chapter("第" + i + "章", 9);
            mItemArray.add(chapter);
            for (int j = 1; j < 10; j++) {
                Section section = new Section(i + "-" + j);
                chapter.sections.add(section);
                mItemArray.add(section);
            }
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
                Section section = (Section) mItemArray.get(position);
                Toast.makeText(StickyHeaderActivity.this, section.name, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void onChapterClick(int position) {
        Chapter chapter = (Chapter) mItemArray.get(position);
        Toast.makeText(StickyHeaderActivity.this, chapter.name, Toast.LENGTH_SHORT).show();
        if (chapter.isOpen) {
            chapter.isOpen = false;
            mRecyclerView.getAdapter().notifyItemChanged(position);
            mItemArray.removeAllAtPosition(position + 1, chapter.sectionSize);
            mRecyclerView.getAdapter().notifyItemRangeRemoved(position + 1, chapter.sectionSize);
//            mRecyclerView.getAdapter().notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(position);
//            LinearLayoutManager mLayoutManager =
//                    (LinearLayoutManager) mRecyclerView.getLayoutManager();
//            mLayoutManager.scrollToPositionWithOffset(position, 0);
        } else {
            chapter.isOpen = true;
            mRecyclerView.getAdapter().notifyItemChanged(position);
            mItemArray.addAll(position + 1, chapter.sections);
            mRecyclerView.getAdapter().notifyItemRangeInserted(position + 1, chapter.sectionSize);
//            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
