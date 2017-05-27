package com.conglai.uikit.feature.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.conglai.common.Debug;
import com.conglai.uikit.feature.features.pullrefresh.builders.HeaderFooterBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenwei on 15/9/30.
 */
public class BaseScrollView extends ScrollView implements HeaderFooterBuilder {

    private final String debug = "BaseScrollView";
    //为scrollView的子View再套一层view
    private LinearLayout container, headerContainer, footerContainer;
    //scrollView的子View
    private LinearLayout rootView;
    private List<View> headerList, footerList;

    public BaseScrollView(Context context) {
        this(context, null);
    }

    public BaseScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initSelf();
    }

    public BaseScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initSelf();
    }

    private void initSelf() {
        container = new LinearLayout(getContext());
        container.setOrientation(LinearLayout.VERTICAL);
        headerContainer = new LinearLayout(getContext());
        headerContainer.setOrientation(LinearLayout.VERTICAL);
        footerContainer = new LinearLayout(getContext());
        footerContainer.setOrientation(LinearLayout.VERTICAL);
        headerList = new ArrayList<>();
        footerList = new ArrayList<>();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        this.initRootView();
    }

    public LinearLayout getRootView() {
        return rootView;
    }

    //为child封装一层LinearLayout
    private void initRootView() {
        rootView = new LinearLayout(getContext());
        rootView.setOrientation(LinearLayout.VERTICAL);
        if (getChildCount() > 0) {
            View childAtO = getChildAt(0);
            super.removeAllViews();
            addContentView(childAtO);
        }
        container.addView(headerContainer);
        container.addView(rootView);
        container.addView(footerContainer);
        super.addView(container);
    }

    @Override
    public void addHeaderView(View view) {
        if (!headerList.contains(view)) {
            headerContainer.addView(view);
            headerList.add(view);
        } else {
            Debug.print(debug, "headerView 已存在！！！不能重复添加");
        }
    }

    @Override
    public void addFooterView(View view) {
        if (!footerList.contains(view)) {
            footerContainer.addView(view);
            footerList.add(view);
        } else {
            Debug.print(debug, "footerView 已存在！！！不能重复添加");
        }
    }

    public void addContentView(View view) {
        rootView.addView(view);
    }

    public void removeContentView(View view) {
        rootView.removeView(view);
    }

    @Override
    public boolean removeHeaderView(View view) {
        if (headerList.contains(view)) {
            headerContainer.removeView(view);
            headerList.remove(view);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFooterView(View view) {
        if (footerList.contains(view)) {
            footerContainer.removeView(view);
            footerList.remove(view);
            return true;
        }
        return false;
    }

    @Override
    public View getFirstHeader() {
        return headerList.size() == 0 ? null : headerList.get(0);
    }

    @Override
    public View getLastFooter() {
        return footerList.size() == 0 ? null : footerList.get(footerList.size() - 1);
    }

    public int getHeaderViewsCount() {
        return headerList.size();
    }

    public int getFooterViewsCount() {
        return footerList.size();
    }

    @Override
    public boolean arrivedTop() {
        return getScrollY() <= 0;
    }

    @Override
    public boolean arrivedBottom() {
        return getChildCount() > 0 && (getScrollY() <= getChildAt(0).getBottom() + getPaddingBottom() - getHeight());
    }

    public void superDispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
    }

    public void superOnInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
    }

    public void superOnTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
    }
}
