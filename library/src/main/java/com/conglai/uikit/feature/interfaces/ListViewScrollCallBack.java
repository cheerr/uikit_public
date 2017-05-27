package com.conglai.uikit.feature.interfaces;

import android.widget.AbsListView;

/**
 * Created by chenwei on 15/3/3.
 */
public interface ListViewScrollCallBack {
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
}
