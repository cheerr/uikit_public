package com.conglai.uikit.feature.features.pullrefresh.feature;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.conglai.uikit.feature.abs.AbsViewFeature;
import com.conglai.uikit.feature.features.pullrefresh.callback.OnTouchGestureListener;
import com.conglai.uikit.feature.judge.DispatchTouchEventJudge;

/**
 * Created by chenwei on 16/1/13.
 */
public class TouchGestureFeature<T extends View> extends AbsViewFeature<T> implements DispatchTouchEventJudge {

    private float mDownX, mDownY;
    private long mDownTime;
    private boolean isHaveDown;
    private boolean first;
    private OnTouchGestureListener mOnTouchGestureListener;

    public TouchGestureFeature(Context context) {
        super(context);
    }

    public void setOnTouchGestureListener(OnTouchGestureListener listener) {
        this.mOnTouchGestureListener = listener;
    }

    @Override
    public boolean judgeDispatchTouchEvent(MotionEvent ev) {
        boolean touch = true;
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                mDownTime = System.currentTimeMillis();
                first = true;
                isHaveDown = true;
                if (mOnTouchGestureListener != null) {
                    touch = mOnTouchGestureListener.onDown(mDownX, mDownY, mDownTime);
                }
                break;
            case MotionEvent.ACTION_UP:
                isHaveDown = false;
                if (mOnTouchGestureListener != null) {
                    if (!mOnTouchGestureListener.onUp(ev.getX(), ev.getY(), System.currentTimeMillis())) {
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isHaveDown && mOnTouchGestureListener != null && !mOnTouchGestureListener.intercept()) {
                    touch = mOnTouchGestureListener.onTouch(ev.getX() - mDownX, ev.getY() - mDownY, System.currentTimeMillis() - mDownTime);
                    if (!touch && first) {
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                        first = false;
                        touch = true;//多执行一次
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                isHaveDown = false;
                if (mOnTouchGestureListener != null) {
                    mOnTouchGestureListener.onCancel(ev.getX(), ev.getY(), System.currentTimeMillis());
                }
                break;
        }
        return !touch;
    }

    @Override
    public boolean returnDispatchTouchEvent(MotionEvent event) {
        return true;
    }
}
