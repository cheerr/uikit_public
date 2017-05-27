package com.conglai.uikit.feature.judge;

import android.view.MotionEvent;

/**
 * Created by chenwei on 15/1/14.
 */
public interface InterceptTouchEventJudge {
    public boolean judgeInterceptTouchEvent(MotionEvent event);

    public boolean returnInterceptTouchEvent(MotionEvent event);
}
