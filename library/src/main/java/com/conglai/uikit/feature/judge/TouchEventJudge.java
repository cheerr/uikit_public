package com.conglai.uikit.feature.judge;

import android.view.MotionEvent;

/**
 * Created by chenwei on 15/1/14.
 */
public interface TouchEventJudge {

    public boolean judgeOnTouchEvent(MotionEvent event);

    public boolean returnOnTouchEvent(MotionEvent event);
}
