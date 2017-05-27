package com.conglai.uikit.feature.callback;


import com.conglai.uikit.feature.abs.AbsFeature;

/**
 * Created by chenwei on 15/7/9.
 */
public interface RemoveFeatureCallBack {
    public void beforeRemoveFeature(AbsFeature feature);

    public void afterRemoveFeature(AbsFeature feature);
}
