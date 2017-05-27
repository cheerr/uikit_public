package com.conglai.uikit.feature.callback;

import android.view.View;

/**
 * Created by chenwei on 15/9/5.
 */
public interface OnLongClickListenerCallBack {
    public void beforeSetOnLongClickListener(View.OnLongClickListener listener);
    public void afterSetOnLongClickListener(View.OnLongClickListener listener);
}

