package com.conglai.uikit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;

import com.conglai.uikit.feature.abs.judge.AbsJudgeListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenwei on 15/9/29.
 */
public class FeatureListView extends AbsJudgeListView {


    List<OnScrollListener> mOnScrollListenerList;

    public FeatureListView(Context context) {
        this(context, null);
    }

    public FeatureListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FeatureListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onListViewScrollStateChanged(AbsListView view, int scrollState) {
        super.onListViewScrollStateChanged(view, scrollState);
        if (mOnScrollListenerList != null) {
            for (int i = 0; i < mOnScrollListenerList.size(); i++) {
                mOnScrollListenerList.get(i).onScrollStateChanged(view, scrollState);
            }
        }
    }

    public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        super.onListViewScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        if (mOnScrollListenerList != null) {
            for (int i = 0; i < mOnScrollListenerList.size(); i++) {
                mOnScrollListenerList.get(i).onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        }
    }

    public void addOnScrollListener(OnScrollListener listener) {
        if (mOnScrollListenerList != null) {
            mOnScrollListenerList.add(listener);
        } else {
            mOnScrollListenerList = new ArrayList<>();
            mOnScrollListenerList.add(listener);
        }
    }
}
