package com.conglai.uikit.feature.callback;

import android.graphics.Canvas;

/**
 * Created by chenwei on 15/3/2.
 */
public interface DrawCallBack {

    public void beforeDraw(Canvas canvas);

    public void afterDraw(Canvas canvas);
}
