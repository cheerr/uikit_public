package com.conglai.uikit.feature.features.pullrefresh.feature;

import android.content.Context;

import com.conglai.uikit.feature.features.pullrefresh.RefreshController;
import com.conglai.uikit.feature.features.pullrefresh.builders.HeaderFooterBuilder;
import com.conglai.uikit.feature.features.pullrefresh.builders.PullModeBuilder;
import com.conglai.uikit.feature.features.pullrefresh.builders.RefreshFeatureBuilder;

/**
 * Created by chenwei on 15/9/29.
 */
public class CustomFeature<T extends HeaderFooterBuilder> extends RefreshFeatureBuilder<T> {

    public CustomFeature(Context context) {
        super(context);
    }

    @Override
    protected void onCreateRefreshController(RefreshController refreshController) {
        refreshController.setUpPullToRefreshEnable(false);
        refreshController.setDownPullToRefreshEnable(false);
        refreshController.setDownMode(PullModeBuilder.DownPullMode.PULL_SMOOTH);
    }
}
