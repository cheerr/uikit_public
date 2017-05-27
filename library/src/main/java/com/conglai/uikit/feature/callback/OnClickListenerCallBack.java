package com.conglai.uikit.feature.callback;

import android.view.View;

/**
 * Created by chenwei on 15/6/26.
 */
public interface OnClickListenerCallBack {
    public void beforeSetOnClickListener(View.OnClickListener listener);
    public void afterSetOnClickListener(View.OnClickListener listener);
}
