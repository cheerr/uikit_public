package com.conglai.uikit.feature.abs.callback;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListAdapter;

import com.conglai.common.Debug;
import com.conglai.uikit.feature.abs.AbsFeatureBuilder;
import com.conglai.uikit.feature.abs.AbsViewFeature;
import com.conglai.uikit.feature.base.BaseListView;
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
import com.conglai.uikit.feature.callback.SetListAdapterCallBack;
import com.conglai.uikit.feature.callback.TouchEventCallBack;
import com.conglai.uikit.feature.interfaces.ListViewScrollCallBack;
import com.conglai.uikit.feature.interfaces.ScrollCallBack;

import java.util.ArrayList;

/**
 * Created by chenwei on 15/9/5.
 * Feature执行的监听层，监听每个方法执行的先后，用before和after两个方法回调，不对父类做拦截
 */
public class AbsCallBackListView extends BaseListView implements AbsFeatureBuilder<AbsViewFeature<BaseListView>> {

    public static final String debug = "AbsCallBackListView";
    private ArrayList<AbsViewFeature<BaseListView>> mFeatureList = new ArrayList<>();

    public ArrayList<AbsViewFeature<BaseListView>> getFeatureList() {
        if (mFeatureList == null)
            mFeatureList = new ArrayList<>();
        return mFeatureList;
    }

    public AbsCallBackListView(Context context) {
        super(context);
        initSelf();
    }

    public AbsCallBackListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSelf();
    }

    public AbsCallBackListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSelf();
    }

    @Override
    public void addFeature(@NonNull AbsViewFeature<BaseListView> feature) {
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
    public void removeFeature(@NonNull AbsViewFeature<BaseListView> feature) {
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

    /**
     * 初始化
     */
    private void initSelf() {
        super.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                onListViewScrollStateChanged(view, scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                onListViewScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
    }


    @Override
    public AbsViewFeature<BaseListView> getFeatureByType(@NonNull Class<?> clazz) {
        for (AbsViewFeature<BaseListView> feature : getFeatureList()) {
            if (feature.getClass() == clazz)
                return feature;
        }
        return null;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof SetListAdapterCallBack) {
                ((SetListAdapterCallBack) feature).beforeSetListAdapter(adapter);
            }
        }
        super.setAdapter(adapter);
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof SetListAdapterCallBack) {
                ((SetListAdapterCallBack) feature).afterSetListAdapter(adapter);
            }
        }
    }


    private AbsListView.OnScrollListener mOnScrollListener;

    @Override
    public void setOnScrollListener(AbsListView.OnScrollListener listener) {
        this.mOnScrollListener = listener;
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

    public void onListViewScrollStateChanged(AbsListView view, int scrollState) {

        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof ScrollCallBack) {
                ((ScrollCallBack) feature).onScrollStateChanged(view, scrollState != AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }
        }
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof ListViewScrollCallBack)
                ((ListViewScrollCallBack) feature).onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);

            if (feature instanceof ScrollCallBack) {
                ((ScrollCallBack) feature).onScroll(AbsCallBackListView.this, getScrollY(), getScrollX());
            }
        }

        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }
}
