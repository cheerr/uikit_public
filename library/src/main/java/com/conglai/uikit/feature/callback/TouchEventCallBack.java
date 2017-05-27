package com.conglai.uikit.feature.callback;

import android.view.MotionEvent;

/**
 * Created by chenwei on 15/1/14.
 */
public interface TouchEventCallBack {

    public void beforeOnTouchEvent(MotionEvent event);

    public void afterOnTouchEvent(MotionEvent event,boolean superValue);
}
