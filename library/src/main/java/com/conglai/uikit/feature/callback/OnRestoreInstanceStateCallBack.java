package com.conglai.uikit.feature.callback;

import android.os.Parcelable;

/**
 * Created by chenwei on 15/3/3.
 */
public interface OnRestoreInstanceStateCallBack {
    public void afterRestoreInstanceState(Parcelable state);
    public void beforeRestoreInstanceState(Parcelable state);
}
