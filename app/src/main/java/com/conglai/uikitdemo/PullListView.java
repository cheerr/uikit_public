package com.conglai.uikitdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Scroller;

import com.conglai.common.Debug;

import java.util.List;

/**
 * Created by chenwei on 15/12/31.
 */
public class PullListView extends ListView {


    private View header;
    private int originHeight = 0;

    private Scroller mScroller;

    public PullListView(Context context) {
        super(context);
        init();
    }

    public PullListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }


    public void attachHeader(View view) {
        this.header = view;
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        // 控制ImageView的高度不断增加
        boolean isCollapse = resizeOverScrollBy(deltaY);

        // return true:下拉到边界的某个地方的时候不再往下拉
        return isCollapse ? true : super.overScrollBy(deltaX, deltaY, scrollX,
                scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent);
    }

    private void toScrollByY(int fromY, int toY, int duration) {
        mScroller.startScroll(0, fromY, 0, toY - fromY, duration);
        postInvalidate();
    }

    private void toScrollByY(int fromY, int toY) {
        if (fromY == toY) return;
        toScrollByY(fromY, toY, 300 + 2 * Math.abs(fromY - toY));
    }

    float lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (header != null && originHeight > 0) {
                    if (event.getY() < lastY) {
                        if (header.getHeight() > originHeight) {
                            event.setAction(MotionEvent.ACTION_POINTER_DOWN);
                        }
                        resizeOverScrollBy(lastY - event.getY());
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (header != null && originHeight > 0) {
                    toScrollByY(header.getHeight(), originHeight);
                }
                break;
        }
        lastY = event.getY();
        return super.onTouchEvent(event);
    }


    private boolean resizeOverScrollBy(float deltaY) {
        Log.d("resizeOverScrollBy", "deltaY:" + deltaY);
        // 下拉过渡，不断增加ImageView的高度，deltay是负数，增加高度就是减去
        header.getLayoutParams().height = header.getHeight()
                - (int) deltaY;
        // View重新调整宽高
        if (originHeight == 0) {
            originHeight = header.getHeight();
        }
        header.requestLayout(); // onMeasure-->onLayout

        return false;
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset() && header != null) {
            if (mScroller.getCurrY() >= originHeight) {
                header.getLayoutParams().height = mScroller.getCurrY();
                header.requestLayout(); // onMeasure-->onLayout
                postInvalidate();
            }
        }
        super.computeScroll();
    }

}
