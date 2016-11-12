package com.wang.baseadapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

public class StickyHeaderDecoration extends RecyclerView.ItemDecoration{
    private int mHeaderPosition;
    private int mCurrentItemType;
    private int mStickyHeaderTop;

    private boolean mIsAdapterDataChanged;

    private Rect mClipBounds;
    private RecyclerView.Adapter mAdapter;
    private View mStickyView;
    private RecyclerView.ViewHolder mStickyViewHolder;

    private final SparseArray<PinnedHeaderCreator> mTypePinnedHeaderFactories = new SparseArray<>();
    private final RecyclerView.AdapterDataObserver mAdapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            mIsAdapterDataChanged = true;
        }
    };

    public StickyHeaderDecoration() {
        this.mHeaderPosition = -1;
        this.mCurrentItemType = -1;
    }

    public StickyHeaderDecoration(int viewType) {
        this.mHeaderPosition = -1;
        this.mCurrentItemType = -1;
        registerTypePinnedHeader(viewType);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        createPinnedHeader(parent);

        if (mStickyView != null) {
            int headerEndAt = mStickyView.getTop() + mStickyView.getHeight();
            View v = parent.findChildViewUnder(c.getWidth() / 2, headerEndAt + 1);

            if (isPinnedView(parent, v)) {
                mStickyHeaderTop = v.getTop() - mStickyView.getHeight();
            } else {
                mStickyHeaderTop = 0;
            }

            mClipBounds = c.getClipBounds();
            mClipBounds.top = mStickyHeaderTop + mStickyView.getHeight();
            c.clipRect(mClipBounds);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mStickyView != null) {
            c.save();

            mClipBounds.top = 0;
            c.clipRect(mClipBounds, Region.Op.UNION);
            c.translate(0, mStickyHeaderTop);
            mStickyView.draw(c);
            c.restore();
        }
    }

    /**
     * Gets the position of the header under the specified (x, y) coordinates.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return position of header, or -1 if not found
     */
    public int findHeaderPositionUnder(int x, int y) {
        if (mStickyView != null) {
            if (x >= 0 && x < mStickyView.getRight() && y >= 0 && y < mStickyView.getBottom() + mStickyHeaderTop) {
                return mHeaderPosition;
            }
        }
        return -1;
    }

    public void bindViewHolder() {
//        if (mAdapter != null && mStickyViewHolder != null){
//            mAdapter.onBindViewHolder(mStickyViewHolder, mHeaderPosition);
//        }
    }

    public int findCurrentHeaderViewType(){
        return mCurrentItemType;
    }

    private void createPinnedHeader(RecyclerView parent) {
        updatePinnedHeader(parent);

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null || layoutManager.getChildCount() <= 0) {
            return;
        }
        int firstVisiblePosition = ((RecyclerView.LayoutParams) layoutManager.getChildAt(0).getLayoutParams()).getViewAdapterPosition();
        int headerPosition = findPinnedHeaderPosition(parent, firstVisiblePosition);

        if (headerPosition >= 0 && mHeaderPosition != headerPosition) {
            mHeaderPosition = headerPosition;
            int viewType = mAdapter.getItemViewType(headerPosition);
            mCurrentItemType = viewType;
//            RecyclerView.ViewHolder pinnedViewHolder = mAdapter.createViewHolder(parent, viewType);
//            RecyclerView.ViewHolder stickyViewHolder = parent.findViewHolderForAdapterPosition(mHeaderPosition);
//            if (stickyViewHolder == null){
            mStickyViewHolder = mAdapter.createViewHolder(parent, viewType);
//            }
            mAdapter.bindViewHolder(mStickyViewHolder, headerPosition);
            mStickyView = mStickyViewHolder.itemView;
            // read layout parameters
            ViewGroup.LayoutParams layoutParams = mStickyView.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mStickyView.setLayoutParams(layoutParams);
            }

            int heightMode = View.MeasureSpec.getMode(layoutParams.height);
            int heightSize = View.MeasureSpec.getSize(layoutParams.height);

            if (heightMode == View.MeasureSpec.UNSPECIFIED) {
                heightMode = View.MeasureSpec.EXACTLY;
            }

            int maxHeight = parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom();
            if (heightSize > maxHeight) {
                heightSize = maxHeight;
            }

            // measure & layout
            int ws = View.MeasureSpec.makeMeasureSpec(parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.EXACTLY);
            int hs = View.MeasureSpec.makeMeasureSpec(heightSize, heightMode);
            mStickyView.measure(ws, hs);
            mStickyView.layout(0, 0, mStickyView.getMeasuredWidth(), mStickyView.getMeasuredHeight());
        }
    }

    private int findPinnedHeaderPosition(RecyclerView parent, int fromPosition) {
        if (fromPosition > mAdapter.getItemCount() || fromPosition < 0) {
            return -1;
        }

        for (int position = fromPosition; position >= 0; position--) {
            final int viewType = mAdapter.getItemViewType(position);
            if (isPinnedViewType(parent, position, viewType)) {
                return position;
            }
        }

        return -1;
    }

    private boolean isPinnedViewType(RecyclerView parent, int adapterPosition, int viewType) {
        PinnedHeaderCreator pinnedHeaderCreator =  mTypePinnedHeaderFactories.get(viewType);

        return pinnedHeaderCreator != null && pinnedHeaderCreator.create(parent, adapterPosition);
    }

    private boolean isPinnedView(RecyclerView parent, View v) {
        int position = parent.getChildAdapterPosition(v);
        if (position == RecyclerView.NO_POSITION) {
            return false;
        }

        return isPinnedViewType(parent, position, mAdapter.getItemViewType(position));
    }

    private void updatePinnedHeader(RecyclerView parent) {
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (mAdapter != adapter || mIsAdapterDataChanged) {
            resetPinnedHeader();
            if (mAdapter != null) {
                mAdapter.unregisterAdapterDataObserver(mAdapterDataObserver);
            }

            mAdapter = adapter;
            if (mAdapter != null) {
                mAdapter.registerAdapterDataObserver(mAdapterDataObserver);
            }
        }
    }

    private void resetPinnedHeader() {
        mHeaderPosition = -1;
        mCurrentItemType = -1;
        mStickyView = null;
    }

    public void registerTypePinnedHeader(int itemType, PinnedHeaderCreator pinnedHeaderCreator) {
        mTypePinnedHeaderFactories.put(itemType, pinnedHeaderCreator);
    }

    public void registerTypePinnedHeader(int itemType) {
        mTypePinnedHeaderFactories.put(itemType, new PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });
    }



    public interface PinnedHeaderCreator {
        boolean create(RecyclerView parent, int adapterPosition);
    }
}
