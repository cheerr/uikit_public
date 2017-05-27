package com.conglai.uikit.feature.abs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by chenwei on 15/1/14.
 */
public class AbsViewFeature<T> implements AbsFeature<T> {

    private T host;
    private Context context;
    private int priority = 0;

    public AbsViewFeature(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    /**
     * 设置这个Feature的宿主
     *
     * @param host
     */
    @Override
    public void setHost(final T host) {
        this.host = host;
    }

    @Override
    public T getHost() {
        return host;
    }

    @Override
    public int priority() {
        return priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * 布局XML里通过标签设置Feature时，constructor会在
     * view的构造函数之后执行,
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public void constructor(Context context, AttributeSet attrs, int defStyle) {

    }


    public static <T extends View> void sortAbsViewFeatureList(ArrayList<AbsViewFeature<T>> features) {
        if (features != null && features.size() > 0) {
            //降序
            Collections.sort(features, new Comparator<AbsViewFeature>() {
                @Override
                public int compare(AbsViewFeature lhs, AbsViewFeature rhs) {
                    return rhs.priority() - lhs.priority();
                }
            });
        }
    }
}
