package com.conglai.uikit.feature.callback;

import android.widget.ListAdapter;

/**
 * Created by chenwei on 15/9/5.
 */
public interface SetListAdapterCallBack {

    public void beforeSetListAdapter(ListAdapter adapter);
    public void afterSetListAdapter(ListAdapter adapter);

}
