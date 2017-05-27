package com.conglai.uikit.feature.abs.judge;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.conglai.uikit.feature.abs.AbsViewFeature;
import com.conglai.uikit.feature.abs.callback.AbsCallBackListView;
import com.conglai.uikit.feature.judge.DispatchTouchEventJudge;
import com.conglai.uikit.feature.judge.InterceptTouchEventJudge;
import com.conglai.uikit.feature.judge.IsFastScollEnabledJudge;
import com.conglai.uikit.feature.judge.SetFastScrollEnabledJudge;
import com.conglai.uikit.feature.judge.TouchEventJudge;

/**
 * Created by chenwei on 15/9/5.
 * Feature的判定层，优先于监听层，如果判定为拦截，不会执行父类方法，并通过返回函数返回
 */
public class AbsJudgeListView extends AbsCallBackListView {

    public static final String debug = "AbsJudgeListView";


    public AbsJudgeListView(Context context) {
        super(context);
    }

    public AbsJudgeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsJudgeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    @Override
    public boolean isFastScrollEnabled() {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof IsFastScollEnabledJudge) {
                if (((IsFastScollEnabledJudge) feature).judgeIsFastScollEnabled()) {
                    return ((IsFastScollEnabledJudge) feature).returnIsFastScollEnabled();
                }
            }
        }
        return super.isFastScrollEnabled();
    }

    @Override
    public void setFastScrollEnabled(boolean enabled) {
        for (AbsViewFeature feature : getFeatureList()) {
            if (feature instanceof SetFastScrollEnabledJudge) {
                if (((SetFastScrollEnabledJudge) feature).judgeSetFastScrollEnabled(enabled)) {
                    return;
                }
            }
        }
        super.setFastScrollEnabled(enabled);
    }
}
