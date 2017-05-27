package com.conglai.uikit.feature.callback;

/**
 * Created by chenwei on 15/3/2.
 */
public interface OnSizeChangeCallBack {
    public void beforeOnSizeChanged(int w, int h, int oldw, int oldh);

    public void afterOnSizeChanged(int w, int h, int oldw, int oldh);
}
