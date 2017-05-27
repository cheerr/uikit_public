package com.conglai.uikit.feature.callback;

/**
 * Created by chenwei on 15/12/31.
 */
public interface OverScrollByCallBack {

    public void beforeOverScrollByCallBack(int deltaX, int deltaY, int scrollX,
                                           int scrollY, int scrollRangeX, int scrollRangeY,
                                           int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent);

    public void afterOverScrollByCallBack(int deltaX, int deltaY, int scrollX,
                                          int scrollY, int scrollRangeX, int scrollRangeY,
                                          int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent);

}
