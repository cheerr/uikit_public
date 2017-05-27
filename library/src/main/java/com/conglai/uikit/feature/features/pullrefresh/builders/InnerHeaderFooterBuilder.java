package com.conglai.uikit.feature.features.pullrefresh.builders;

import android.view.View;

/**
 * Created by chenwei on 15/9/29.
 */
public interface InnerHeaderFooterBuilder {

    public void addInnerHeader(View view);

    public void addInnerFooter(View view);

    public void removeInnerHeader(View view);

    public void removeInnerFooter(View view);
}
