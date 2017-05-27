package com.conglai.uikit.feature.callback;

import android.support.v7.widget.RecyclerView;

/**
 * Created by chenwei on 16/12/6.
 */

public interface SetRecyclerAdapterCallBack {

    public void beforeRecyclerSetAdapter(RecyclerView.Adapter adapter);

    public void afterRecyclerSetAdapter(RecyclerView.Adapter adapter);
}
