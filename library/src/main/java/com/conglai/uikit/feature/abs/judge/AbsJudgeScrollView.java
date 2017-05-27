package com.conglai.uikit.feature.abs.judge;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.conglai.uikit.feature.abs.AbsViewFeature;
import com.conglai.uikit.feature.abs.callback.AbsCallBackScrollView;
import com.conglai.uikit.feature.judge.DispatchTouchEventJudge;
import com.conglai.uikit.feature.judge.InterceptTouchEventJudge;
import com.conglai.uikit.feature.judge.TouchEventJudge;

/**
 * Created by chenwei on 15/9/30.
 */
public class AbsJudgeScrollView extends AbsCallBackScrollView {

    public static final String debug = "AbsJudgeScrollView";


    public AbsJudgeScrollView(Context context) {
        super(context);
    }

    public AbsJudgeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsJudgeScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (AbsViewFeature feature : mFeatureList) {
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
        for (AbsViewFeature feature : mFeatureList) {
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
        for (AbsViewFeature feature : mFeatureList) {
            if (feature instanceof TouchEventJudge) {
                if (((TouchEventJudge) feature).judgeOnTouchEvent(ev)) {
                    return ((TouchEventJudge) feature).returnOnTouchEvent(ev);
                }
            }
        }
        return super.onTouchEvent(ev);
    }
}
