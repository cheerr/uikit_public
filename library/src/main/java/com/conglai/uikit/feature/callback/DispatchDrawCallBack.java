package com.conglai.uikit.feature.callback;

import android.graphics.Canvas;

/**
 * Created by chenwei on 15/3/3.
 */
public interface DispatchDrawCallBack {

    public void beforeDispatchDraw(Canvas canvas);

    public void afterDispatchDraw(Canvas canvas);
}
