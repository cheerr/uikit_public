package com.conglai.uikit.feature.judge;

import android.view.MotionEvent;

/**
 * Created by chenwei on 15/1/14.
 */
public interface DispatchTouchEventJudge {

    public boolean judgeDispatchTouchEvent(MotionEvent event);

    public boolean returnDispatchTouchEvent(MotionEvent event);
}
