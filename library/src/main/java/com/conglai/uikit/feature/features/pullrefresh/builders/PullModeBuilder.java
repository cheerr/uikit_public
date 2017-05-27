package com.conglai.uikit.feature.features.pullrefresh.builders;

/**
 * Created by chenwei on 15/1/15.
 */
public interface PullModeBuilder {
    public static enum UpPullMode {
        PULL_SMOOTH, PULL_STAY_REFRESH,PULL_STAND;
    }

    public static enum DownPullMode {
        PULL_SMOOTH, PULL_AUTO,PULL_STAND;
    }

    public void setUpMode(UpPullMode mode);

    public UpPullMode getUpMode();

    public void setDownMode(DownPullMode mode);

    public DownPullMode getDownMode();
}
