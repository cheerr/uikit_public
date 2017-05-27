package com.conglai.uikit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by chenwei on 15/11/1.
 */
public class FeatureButton extends Button {
    public FeatureButton(Context context) {
        super(context);
        init();
    }

    public FeatureButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FeatureButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
       // setTypeface(UITypeface.getTypeFace(getContext(), UITypeface.TypeFace.Lantinghei));
    }
}
