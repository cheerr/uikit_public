package com.conglai.uikit.feature.features.pullrefresh.builders;

import android.view.View;

import com.conglai.uikit.feature.features.pullrefresh.interfaces.ViewBorderJudge;

/**
 * Created by chenwei on 15/1/14.
 * 需要实现下拉上拉的View需要实现这个接口，由RefreshController统一实现刷新的过程
 */
public interface HeaderFooterBuilder extends ViewBorderJudge {
    public void addHeaderView(View view);

    public void addFooterView(View view);

    public boolean removeHeaderView(View view);

    public boolean removeFooterView(View view);

    public int getHeaderViewsCount();

    public int getFooterViewsCount();

    public View getFirstHeader();

    public View getLastFooter();
}
