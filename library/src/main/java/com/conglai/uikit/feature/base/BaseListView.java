package com.conglai.uikit.feature.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.conglai.common.Debug;
import com.conglai.uikit.feature.features.pullrefresh.builders.HeaderFooterBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenwei on 15/9/5.
 */
public class BaseListView extends ListView implements HeaderFooterBuilder {

    private final String debug = "BaseListView";
    private List<View> headerList, footerList;

    public BaseListView(Context context) {
        this(context, null);
    }

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSelf();
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSelf();
    }

    private void initSelf() {
        headerList = new ArrayList<>();
        footerList = new ArrayList<>();
    }

    @Override
    public void addHeaderView(View view) {
        if (!headerList.contains(view)) {
            super.addHeaderView(view);
            headerList.add(view);
        } else {
            Debug.print(debug, "headerView 已存在！！！不能重复添加");
        }
    }

    @Override
    public void addFooterView(View view) {
        if (!footerList.contains(view)) {
            super.addFooterView(view);
            footerList.add(view);
        } else {
            Debug.print(debug, "footerView 已存在！！！不能重复添加");
        }
    }

    @Override
    public boolean removeHeaderView(View view) {
        if (headerList.contains(view)) {
            headerList.remove(view);
            return super.removeHeaderView(view);
        } else {
            return false;
        }
    }

    @Override
    public boolean removeFooterView(View view) {
        if (footerList.contains(view)) {
            footerList.remove(view);
            return super.removeFooterView(view);
        } else {
            return false;
        }
    }


    @Override
    public View getFirstHeader() {
        return headerList.size() == 0 ? null : headerList.get(0);
    }

    @Override
    public View getLastFooter() {
        return footerList.size() == 0 ? null : footerList.get(footerList.size() - 1);
    }

    @Override
    public boolean arrivedTop() {
        return getFirstVisiblePosition() <= 0;
    }

    @Override
    public boolean arrivedBottom() {
        return getLastVisiblePosition() == getCount() - 1;
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
