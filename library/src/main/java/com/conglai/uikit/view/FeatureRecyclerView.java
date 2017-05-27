package com.conglai.uikit.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.conglai.uikit.feature.abs.judge.AbsJudgeRecyclerView;

/**
 * Created by chenwei on 16/4/6.
 */
public class FeatureRecyclerView extends AbsJudgeRecyclerView {
    public FeatureRecyclerView(Context context) {
        super(context);
    }

    public FeatureRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FeatureRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
