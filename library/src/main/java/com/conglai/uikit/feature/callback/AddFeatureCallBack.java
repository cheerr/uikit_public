package com.conglai.uikit.feature.callback;


import com.conglai.uikit.feature.abs.AbsFeature;

/**
 * Created by chenwei on 15/1/17.
 */
public interface AddFeatureCallBack {
    public void beforeAddFeature(AbsFeature feature);

    public void afterAddFeature(AbsFeature feature);
}
