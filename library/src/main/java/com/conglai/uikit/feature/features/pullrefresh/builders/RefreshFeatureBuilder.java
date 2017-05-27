package com.conglai.uikit.feature.features.pullrefresh.builders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.Scroller;

import com.conglai.uikit.feature.abs.AbsFeature;
import com.conglai.uikit.feature.abs.AbsViewFeature;
import com.conglai.uikit.feature.callback.AddFeatureCallBack;
import com.conglai.uikit.feature.callback.ComputeScrollCallBack;
import com.conglai.uikit.feature.callback.DispatchTouchEventCallBack;
import com.conglai.uikit.feature.callback.SetListAdapterCallBack;
import com.conglai.uikit.feature.callback.SetRecyclerAdapterCallBack;
import com.conglai.uikit.feature.callback.TouchEventCallBack;
import com.conglai.uikit.feature.features.pullrefresh.RefreshController;
import com.conglai.uikit.feature.interfaces.ScrollCallBack;
import com.conglai.uikit.feature.judge.InterceptTouchEventJudge;

/**
 * Created by chenwei on 15/1/15.
 * <p/>
 * 简单封装了一下内含RefreshController的FixedListView的Feature，统一控制RefreshController必须实现的几个方法
 */
public abstract class RefreshFeatureBuilder<T extends HeaderFooterBuilder> extends AbsViewFeature<T> implements ComputeScrollCallBack,
        TouchEventCallBack, ScrollCallBack, AddFeatureCallBack, SetListAdapterCallBack, SetRecyclerAdapterCallBack, DispatchTouchEventCallBack, InterceptTouchEventJudge {

    private String debug = "RefreshFeatureBuilder";
    private RefreshController mRefreshController;

    @Override
    public void beforeOnTouchEvent(MotionEvent event) {
        mRefreshController.beforeOnTouchEvent(event);
    }

    @Override
    public void afterOnTouchEvent(MotionEvent event, boolean superValue) {
        mRefreshController.afterOnTouchEvent(event, superValue);
    }

    @Override
    public void beforeDispatchTouchEvent(MotionEvent event) {
        mRefreshController.beforeDispatchTouchEvent(event);
    }

    @Override
    public void afterDispatchTouchEvent(MotionEvent event, boolean superValue) {
        mRefreshController.afterDispatchTouchEvent(event, superValue);
    }

    public RefreshFeatureBuilder(Context context) {
        super(context);
        mRefreshController = new RefreshController(this, new Scroller(context));
        onCreateRefreshController(mRefreshController);
    }

    public RefreshController getRefreshController() {
        return mRefreshController;
    }

    @Override
    public void constructor(Context context, AttributeSet attrs, int defStyle) {

    }

    @Override
    public void setHost(T host) {
        super.setHost(host);
        mRefreshController.setControllerView((View) host);
    }

    protected abstract void onCreateRefreshController(RefreshController refreshController);

    @Override
    public void beforeComputeScroll() {
        mRefreshController.beforeComputeScroll();
    }

    @Override
    public void afterComputeScroll() {
    }

    @Override
    public void onScrollStateChanged(View view, boolean isScrolling) {
        mRefreshController.onScrollStateChanged(view, isScrolling);
    }

    @Override
    public void onScroll(View view, int scrollX, int scrollY) {
    }

    @Override
    public void beforeAddFeature(AbsFeature feature) {

    }

    @Override
    public void afterAddFeature(AbsFeature feature) {
        mRefreshController.loadController();
    }

    @Override
    public void beforeSetListAdapter(ListAdapter adapter) {
        mRefreshController.loadController();
    }

    @Override
    public void afterSetListAdapter(ListAdapter adapter) {
        mRefreshController.updateMode();
    }

    @Override
    public void beforeRecyclerSetAdapter(RecyclerView.Adapter adapter) {
        mRefreshController.loadController();
    }

    @Override
    public void afterRecyclerSetAdapter(RecyclerView.Adapter adapter) {
        mRefreshController.updateMode();
    }


    @Override
    public boolean judgeInterceptTouchEvent(MotionEvent event) {
        return mRefreshController.judgeInterceptTouchEvent(event);
    }

    @Override
    public boolean returnInterceptTouchEvent(MotionEvent event) {
        return mRefreshController.returnInterceptTouchEvent(event);
    }

}
