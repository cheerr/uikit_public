package com.conglai.uikit.feature.abs;

/**
 * Created by chenwei on 15/1/14.
 */
public interface AbsFeature<T> {

    public void setHost(final T host);

    public T getHost();

    public int priority();
}
