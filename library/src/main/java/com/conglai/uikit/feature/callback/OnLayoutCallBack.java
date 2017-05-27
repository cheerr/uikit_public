package com.conglai.uikit.feature.callback;

/**
 * Created by keke on 15-5-6.
 */
public interface OnLayoutCallBack {
    public void beforeOnLayout(boolean changed, int l, int t, int r, int b);

    public void afterOnLayout(boolean changed, int l, int t, int r, int b);
}
