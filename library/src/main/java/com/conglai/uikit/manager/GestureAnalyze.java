package com.conglai.uikit.manager;

import android.support.annotation.NonNull;
import android.view.MotionEvent;

/**
 * Created by chenwei on 15/2/28.
 */
public class GestureAnalyze {

    OnGestureListener gestureListener;
    float[] y, x;
    int index = 0;
    float ban = 0.5f;

    public GestureAnalyze(@NonNull OnGestureListener gestureListener) {
        this.gestureListener = gestureListener;
        y = new float[2];
        x = new float[2];
    }


    public void onTouchEvent(MotionEvent event) {
        if (gestureListener == null || event.getPointerCount() <= 0) return;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                x[0] = x[1] = event.getX(0);
                y[0] = y[1] = event.getY(0);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.INVALID_POINTER_ID:
                x[0] = x[1] = event.getX(0);
                y[0] = y[1] = event.getY(0);
                return;
        }
        index = (index + 1) % 2;
        x[index] = event.getX(0);
        y[index] = event.getY(0);

        int last = (index + 1) % 2;
        if (gestureListener != null && Math.abs(x[last] - x[index]) * ban < Math.abs(y[last] - y[index])) {
            gestureListener.onScroll(x[last] - x[index], y[last] - y[index]);
        }
    }

    public void setBan(float b) {
        ban = b;
    }

    public static interface OnGestureListener {
        public boolean onScroll(float distanceX, float distanceY);
    }
}
