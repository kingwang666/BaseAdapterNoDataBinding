package com.wang.baseadapter.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wang.baseadapter.R;
import com.wang.baseadapter.model.RecyclerViewItemArray;

/**
 * Created on 2016/6/13.
 * Author: wang
 * 默认加载更多代理
 */
public class LoadingDelegate implements AdapterDelegate<LoadingDelegate.NoMoreViewHolder> {

    private OnRequestMoreListener mRequestMoreListener;
    /**
     * 是否开启自动加载
     */
    private boolean mOpenMore = false;
    /**
     * 是否正在加载更多
     */
    private boolean isLoadingMore = false;
    /**
     * 是否还有数据
     */
    private boolean isHaveMore = false;

    public LoadingDelegate(){
        this(null, false);
    }

    public LoadingDelegate(OnRequestMoreListener listener, boolean openMore){
        mRequestMoreListener = listener;
        mOpenMore = openMore;
        isHaveMore = openMore;
    }

    @Override
    public NoMoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
        return new NoMoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemArray itemArray, NoMoreViewHolder vh, int position) {
        if (canLoadMore()){
            vh.mProgress.setVisibility(View.VISIBLE);
            vh.mTipTV.setText("正在加载更多...");
            isLoadingMore = true;
            mRequestMoreListener.onRequestMore();
        }
        else if (!isHaveMore){
            vh.mProgress.setVisibility(View.GONE);
            vh.mTipTV.setText("没有更多数据了！");
        }
    }

    /**
     * 加载更多成功后需调用此方法，告诉下次滑到底部是否还要加载更多
     * @param isHaveMore true需要
     */
    public void notifyAfterLoadMore(boolean isHaveMore){
        isLoadingMore = false;
        this.isHaveMore = isHaveMore;
    }

    private boolean canLoadMore(){
        return mRequestMoreListener != null && mOpenMore && !isLoadingMore && isHaveMore;
    }

    class NoMoreViewHolder extends RecyclerView.ViewHolder {

        ProgressBar mProgress;
        TextView mTipTV;

        public NoMoreViewHolder(View itemView) {
            super(itemView);
            mProgress = (ProgressBar) itemView.findViewById(R.id.loading_progress);
            mTipTV = (TextView) itemView.findViewById(R.id.loading_text);
        }
    }

    public interface OnRequestMoreListener{
        void onRequestMore();
    }
}
