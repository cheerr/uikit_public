package com.conglai.uikit.feature.abs.judge;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.conglai.uikit.feature.abs.AbsViewFeature;
import com.conglai.uikit.feature.abs.callback.AbsCallBackRecyclerView;
import com.conglai.uikit.feature.judge.DispatchTouchEventJudge;
import com.conglai.uikit.feature.judge.InterceptTouchEventJudge;
import com.conglai.uikit.feature.judge.TouchEventJudge;

/**
 * Created by chenwei on 16/12/7.
 */

public class AbsJudgeRecyclerView extends AbsCallBackRecyclerView {
    public AbsJudgeRecyclerView(Context context) {
        super(context);
    }

    public AbsJudgeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsJudgeRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof DispatchTouchEventJudge) {
                if (((DispatchTouchEventJudge) feature).judgeDispatchTouchEvent(ev)) {
                    return ((DispatchTouchEventJudge) feature).returnDispatchTouchEvent(ev);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof InterceptTouchEventJudge) {
                if (((InterceptTouchEventJudge) feature).judgeInterceptTouchEvent(ev)) {
                    return ((InterceptTouchEventJudge) feature).returnInterceptTouchEvent(ev);
                }
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof TouchEventJudge) {
                if (((TouchEventJudge) feature).judgeOnTouchEvent(ev)) {
                    return ((TouchEventJudge) feature).returnOnTouchEvent(ev);
                }
            }
        }
        return super.onTouchEvent(ev);
    }
}
