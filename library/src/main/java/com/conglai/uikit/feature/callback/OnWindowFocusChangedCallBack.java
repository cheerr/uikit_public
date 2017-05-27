package com.conglai.uikit.feature.callback;

/**
 * Created by chenwei on 15/2/10.
 */
public interface OnWindowFocusChangedCallBack {
    public void beforeWindowFocusChanged(boolean hasWindowFocus);

    public void afterWindowFocusChanged(boolean hasWindowFocus);
}
