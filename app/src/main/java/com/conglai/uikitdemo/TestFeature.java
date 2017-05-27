package com.conglai.uikitdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.conglai.uikit.feature.features.pullrefresh.RefreshController;
import com.conglai.uikit.feature.features.pullrefresh.builders.HeaderFooterBuilder;
import com.conglai.uikit.feature.features.pullrefresh.builders.PullModeBuilder;
import com.conglai.uikit.feature.features.pullrefresh.builders.RefreshFeatureBuilder;
import com.conglai.uikit.feature.features.pullrefresh.callback.ControllerCallBack;
import com.conglai.common.Debug;
import com.conglai.uikit.util.UIViewUtil;

/**
 * Created by chenwei on 15/12/31.
 */
public class TestFeature<T extends HeaderFooterBuilder> extends RefreshFeatureBuilder<T> implements ControllerCallBack {

    private static final String debug = "TestFeature";


    public TestFeature(Context context) {
        super(context);
    }

    @Override
    protected void onCreateRefreshController(RefreshController refreshController) {
        refreshController.setUpMode(PullModeBuilder.UpPullMode.PULL_STAND);
    }


    @Override
    public void onStopScroll() {

    }

    @Override
    public void onResetLayout() {

    }

    @Override
    public void onUpRefresh() {

    }

    @Override
    public void onDownRefresh() {

    }

    @Override
    public void onUpBack() {

    }

    @Override
    public void onDownBack() {

    }

    @Override
    public void onUpMove(View view, int disY, float percent) {

    }

    @Override
    public void onDownMove(View view, int disY, float percent) {

    }


    private int originHeight = 0;
    private View child;


    @Override
    public void onUpPull(View view, float disY) {
        if (view != null && view instanceof ListView && ((ListView) view).getChildCount() > 0) {
            if (originHeight == 0 || child == null) {
                child = ((ListView) view).getChildAt(0);
                UIViewUtil.measureView(child);
                originHeight = child.getMeasuredHeight();
                Debug.print(debug, "originHeight " + originHeight);
            }
            if (originHeight > 0 && child != null) {
                ViewGroup.LayoutParams params = child.getLayoutParams();
                params.height = (int) (disY + originHeight);
                child.requestLayout();
            }
        }
    }
}
