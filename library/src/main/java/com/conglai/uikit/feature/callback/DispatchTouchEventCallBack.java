package com.conglai.uikit.feature.callback;

import android.view.MotionEvent;

/**
 * Created by chenwei on 15/1/14.
 */
public interface DispatchTouchEventCallBack {

    public void beforeDispatchTouchEvent(MotionEvent event);

    public void afterDispatchTouchEvent(MotionEvent event,boolean superValue);
}
