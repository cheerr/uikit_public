package com.conglai.uikit.feature.judge;

import android.graphics.Rect;

/**
 * Created by chenwei on 15/2/10.
 */
public interface OnFocusChangedJudge {
    public boolean judgeFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect);
}
