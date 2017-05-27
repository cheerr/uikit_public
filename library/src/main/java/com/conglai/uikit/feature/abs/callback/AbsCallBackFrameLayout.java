package com.conglai.uikit.feature.abs.callback;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.conglai.common.Debug;
import com.conglai.uikit.feature.abs.AbsFeatureBuilder;
import com.conglai.uikit.feature.abs.AbsViewFeature;
import com.conglai.uikit.feature.base.BaseFrameLayout;
import com.conglai.uikit.feature.callback.AddFeatureCallBack;
import com.conglai.uikit.feature.callback.ComputeScrollCallBack;
import com.conglai.uikit.feature.callback.DispatchTouchEventCallBack;
import com.conglai.uikit.feature.callback.InterceptTouchEventCallBack;
import com.conglai.uikit.feature.callback.RemoveFeatureCallBack;
import com.conglai.uikit.feature.callback.TouchEventCallBack;

import java.util.ArrayList;

/**
 * Created by chenwei on 16/1/13.
 */
public class AbsCallBackFrameLayout extends BaseFrameLayout implements AbsFeatureBuilder<AbsViewFeature<BaseFrameLayout>> {

    public static final String debug = "AbsCallBackFrameLayout";
    private ArrayList<AbsViewFeature<BaseFrameLayout>> mFeatureList = new ArrayList<>();

    public ArrayList<AbsViewFeature<BaseFrameLayout>> getFeatureList() {
        if (mFeatureList == null)
            mFeatureList = new ArrayList<>();
        return mFeatureList;
    }

    public AbsCallBackFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AbsCallBackFrameLayout(Context context) {
        super(context);
    }

    public AbsCallBackFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void addFeature(@NonNull AbsViewFeature<BaseFrameLayout> feature) {
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
    public void removeFeature(@NonNull AbsViewFeature<BaseFrameLayout> feature) {
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
    public AbsViewFeature<BaseFrameLayout> getFeatureByType(@NonNull Class<?> clazz) {
        for (AbsViewFeature<BaseFrameLayout> feature : getFeatureList()) {
            if (feature.getClass() == clazz)
                return feature;
        }
        return null;
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

}
