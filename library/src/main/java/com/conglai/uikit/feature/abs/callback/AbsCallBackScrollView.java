package com.conglai.uikit.feature.abs.callback;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.conglai.common.Debug;
import com.conglai.uikit.feature.abs.AbsFeatureBuilder;
import com.conglai.uikit.feature.abs.AbsViewFeature;
import com.conglai.uikit.feature.base.BaseScrollView;
import com.conglai.uikit.feature.callback.AddFeatureCallBack;
import com.conglai.uikit.feature.callback.ComputeScrollCallBack;
import com.conglai.uikit.feature.callback.DispatchDrawCallBack;
import com.conglai.uikit.feature.callback.DispatchTouchEventCallBack;
import com.conglai.uikit.feature.callback.DrawCallBack;
import com.conglai.uikit.feature.callback.InterceptTouchEventCallBack;
import com.conglai.uikit.feature.callback.OnDrawCallBack;
import com.conglai.uikit.feature.callback.OnFocusChangedCallBack;
import com.conglai.uikit.feature.callback.OnLayoutCallBack;
import com.conglai.uikit.feature.callback.OnRestoreInstanceStateCallBack;
import com.conglai.uikit.feature.callback.OnSizeChangeCallBack;
import com.conglai.uikit.feature.callback.OnWindowFocusChangedCallBack;
import com.conglai.uikit.feature.callback.OverScrollByCallBack;
import com.conglai.uikit.feature.callback.RemoveFeatureCallBack;
import com.conglai.uikit.feature.callback.TouchEventCallBack;
import com.conglai.uikit.feature.interfaces.ScrollCallBack;

import java.util.ArrayList;

/**
 * Created by chenwei on 15/9/30.
 */
public class AbsCallBackScrollView extends BaseScrollView implements AbsFeatureBuilder<AbsViewFeature<BaseScrollView>> {

    public static final String debug = "AbsCallBackScrollView";
    protected ArrayList<AbsViewFeature<BaseScrollView>> mFeatureList = new ArrayList<>();

    public ArrayList<AbsViewFeature<BaseScrollView>> getFeatureList() {
        if (mFeatureList == null)
            mFeatureList = new ArrayList<>();
        return mFeatureList;
    }

    public AbsCallBackScrollView(Context context) {
        super(context);
    }

    public AbsCallBackScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsCallBackScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addFeature(@NonNull AbsViewFeature<BaseScrollView> feature) {
        if (!getFeatureList().contains(feature)) {
            if (feature instanceof AddFeatureCallBack) {
                ((AddFeatureCallBack) feature).beforeAddFeature(feature);
            }
            mFeatureList.add(feature);
            feature.setHost(this);
            AbsViewFeature.sortAbsViewFeatureList(mFeatureList);

            if (feature instanceof AddFeatureCallBack) {
                ((AddFeatureCallBack) feature).afterAddFeature(feature);
            }
        } else {
            Debug.print(debug, "添加失败，" + feature.getClass().getSimpleName() + " 已存在！！！");
        }
    }

    @Override
    public void removeFeature(@NonNull AbsViewFeature<BaseScrollView> feature) {
        if (getFeatureList().contains(feature)) {
            if (feature instanceof RemoveFeatureCallBack) {
                ((RemoveFeatureCallBack) feature).beforeRemoveFeature(feature);
            }
            feature.setHost(null);//与View解绑
            mFeatureList.remove(feature);
            if (feature instanceof RemoveFeatureCallBack) {
                ((RemoveFeatureCallBack) feature).afterRemoveFeature(feature);
            }
        } else {
            Debug.print(debug, "删除失败，" + feature.getClass().getSimpleName() + " 不存在！！！");
        }
    }

    @Override
    public AbsViewFeature<BaseScrollView> getFeatureByType(@NonNull Class<?> clazz) {
        for (AbsViewFeature<BaseScrollView> feature : getFeatureList()) {
            if (feature.getClass() == clazz)
                return feature;
        }
        return null;
    }


    private int currentScroll;
    /**
     * 监控ScrollView滑动状态
     */
    private Runnable checkState = new Runnable() {
        @Override
        public void run() {
            int newScroll = getScrollY();
            if (currentScroll == newScroll) {
                for (AbsViewFeature<BaseScrollView> feature : getFeatureList()) {
                    if (feature instanceof ScrollCallBack) {
                        ((ScrollCallBack) feature).onScrollStateChanged(AbsCallBackScrollView.this, false);
                    }
                }
            } else {
                currentScroll = getScrollY();
                postDelayed(this, 150);
            }
        }
    };

    @Override
    protected void onScrollChanged(int w, int h, int oldw, int oldh) {
        super.onScrollChanged(w, h, oldw, oldh);
        for (AbsViewFeature<BaseScrollView> feature : getFeatureList()) {
            if (feature instanceof ScrollCallBack) {
                ((ScrollCallBack) feature).onScroll(this, getScrollX(), getScrollY());
                ((ScrollCallBack) feature).onScrollStateChanged(this, true);
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof OnDrawCallBack) {
                ((OnDrawCallBack) feature).beforeOnDraw(canvas);
            }
        }
        super.onDraw(canvas);
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof OnDrawCallBack) {
                ((OnDrawCallBack) feature).afterOnDraw(canvas);
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof DispatchDrawCallBack) {
                ((DispatchDrawCallBack) feature).beforeDispatchDraw(canvas);
            }
        }
        super.dispatchDraw(canvas);
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof DispatchDrawCallBack) {
                ((DispatchDrawCallBack) feature).afterDispatchDraw(canvas);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof DrawCallBack) {
                ((DrawCallBack) feature).beforeDraw(canvas);
            }
        }
        super.draw(canvas);
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof DrawCallBack) {
                ((DrawCallBack) feature).afterDraw(canvas);
            }
        }
    }

    @Override
    public void computeScroll() {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof ComputeScrollCallBack) {
                ((ComputeScrollCallBack) feature).beforeComputeScroll();
            }
        }
        super.computeScroll();
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof ComputeScrollCallBack) {
                ((ComputeScrollCallBack) feature).afterComputeScroll();
            }
        }
    }

    //监控TouchEvent事件分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof DispatchTouchEventCallBack) {
                ((DispatchTouchEventCallBack) feature).beforeDispatchTouchEvent(ev);
            }
        }
        boolean dispatch = super.dispatchTouchEvent(ev);

        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof DispatchTouchEventCallBack) {
                ((DispatchTouchEventCallBack) feature).afterDispatchTouchEvent(ev, dispatch);
            }
        }
        return dispatch;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof InterceptTouchEventCallBack) {
                ((InterceptTouchEventCallBack) feature).beforeInterceptTouchEvent(ev);
            }
        }
        boolean dispatch = super.onInterceptTouchEvent(ev);
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof InterceptTouchEventCallBack) {
                ((InterceptTouchEventCallBack) feature).afterInterceptTouchEvent(ev, dispatch);
            }
        }
        return dispatch;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof TouchEventCallBack) {
                ((TouchEventCallBack) feature).beforeOnTouchEvent(ev);
            }
        }
        boolean dispatch = super.onTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            removeCallbacks(checkState);
            currentScroll = getScrollY();
            postDelayed(checkState, 150);
        }
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof TouchEventCallBack) {
                ((TouchEventCallBack) feature).afterOnTouchEvent(ev, dispatch);
            }
        }
        return dispatch;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof OnFocusChangedCallBack) {
                ((OnFocusChangedCallBack) feature).beforeFocusChanged(focused, direction, previouslyFocusedRect);
            }
        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof OnFocusChangedCallBack) {
                ((OnFocusChangedCallBack) feature).afterFocusChanged(focused, direction, previouslyFocusedRect);
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof OnWindowFocusChangedCallBack) {
                ((OnWindowFocusChangedCallBack) feature).beforeWindowFocusChanged(hasWindowFocus);
            }
        }
        super.onWindowFocusChanged(hasWindowFocus);
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof OnWindowFocusChangedCallBack) {
                ((OnWindowFocusChangedCallBack) feature).afterWindowFocusChanged(hasWindowFocus);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof OnSizeChangeCallBack) {
                ((OnSizeChangeCallBack) feature).beforeOnSizeChanged(w, h, oldw, oldh);
            }
        }
        super.onSizeChanged(w, h, oldw, oldh);
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof OnSizeChangeCallBack) {
                ((OnSizeChangeCallBack) feature).afterOnSizeChanged(w, h, oldw, oldh);
            }
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof OnRestoreInstanceStateCallBack) {
                ((OnRestoreInstanceStateCallBack) feature).beforeRestoreInstanceState(state);
            }
        }
        super.onRestoreInstanceState(state);
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof OnRestoreInstanceStateCallBack) {
                ((OnRestoreInstanceStateCallBack) feature).afterRestoreInstanceState(state);
            }
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof OnLayoutCallBack) {
                ((OnLayoutCallBack) feature).beforeOnLayout(changed, l, t, r, b);
            }
        }
        super.onLayout(changed, l, t, r, b);
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof OnLayoutCallBack) {
                ((OnLayoutCallBack) feature).afterOnLayout(changed, l, t, r, b);
            }
        }
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof OverScrollByCallBack) {
                ((OverScrollByCallBack) feature).beforeOverScrollByCallBack(deltaX, deltaY
                        , scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
            }
        }
        boolean result = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof OverScrollByCallBack) {
                ((OverScrollByCallBack) feature).afterOverScrollByCallBack(deltaX, deltaY
                        , scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
            }
        }
        return result;
    }
}
