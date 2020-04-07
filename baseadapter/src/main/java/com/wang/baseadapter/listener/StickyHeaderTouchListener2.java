package com.wang.baseadapter.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.baseadapter.itemdecoration.StickyHeaderDecoration;

/**
 * Created by wang
 * on 2016/11/8
 */
public class StickyHeaderTouchListener2 implements RecyclerView.OnItemTouchListener {

    private final StickyHeaderDecoration mDecor;

    public StickyHeaderTouchListener2(StickyHeaderDecoration decor) {
        mDecor = decor;
    }


    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent e) {
        int position = mDecor.findHeaderPositionUnder((int) e.getX(), (int) e.getY());
        if (position != -1) {
            View sticky = mDecor.getStickyView();
            sticky.onTouchEvent(e);
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent e) {
//        int position = mDecor.findHeaderPositionUnder((int) e.getX(), (int) e.getY());
//        if (position != -1) {
//
//        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        View sticky = mDecor.getStickyView();
        if (sticky instanceof ViewGroup){
            ((ViewGroup) sticky).requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }
}
