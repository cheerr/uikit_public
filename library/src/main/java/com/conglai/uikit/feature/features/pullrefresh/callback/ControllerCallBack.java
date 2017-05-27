package com.conglai.uikit.feature.features.pullrefresh.callback;

import android.view.View;

/**
 * Created by chenwei on 15/1/15.
 * <p/>
 * RefreshController控制的刷新动作监听器
 */
public interface ControllerCallBack {
    //停止滑动
    public void onStopScroll();

    //回滚
    public void onResetLayout();

    //下拉超过下拉阈值，松手刷新
    public void onUpRefresh();

    //上拉超过上拉阈值或者PULL_AUTO模式下到达底部时，松手刷新
    public void onDownRefresh();

    //下拉未达到下拉阈值时松手
    public void onUpBack();

    //上拉未达到上拉阈值时松手
    public void onDownBack();

    //刷新头出现后监控下拉的距离
    public void onUpMove(View view, int disY, float percent);

    //刷新尾出现后监控上拉的距离
    public void onDownMove(View view, int disY, float percent);

    //stand模式下下拉
    public void onUpPull(View view, float disY);

}
